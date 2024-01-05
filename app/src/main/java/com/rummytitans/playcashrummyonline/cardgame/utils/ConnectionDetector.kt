package com.rummytitans.playcashrummyonline.cardgame.utils

import com.rummytitans.playcashrummyonline.cardgame.MainApplication
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class ConnectionDetector(internal var context: Context) {

    val isConnected: Boolean
        get() {
            return isOnline()
        }

    private fun isOnline(): Boolean {
        val app = (context as? MainApplication)
        if (app?.isAppBackgroud == true) {
            return true
        }
        if(app?.isFromBackground==true){
            Thread.sleep(500)
        }
        app?.isFromBackground  =false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        ||networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                            true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnectedOrConnecting
        }
    }

    fun checkConnectionUsingHost():Boolean{
        val url = URL("https://www.google.com") // Change this URL to a known server
        return try {
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 2000 // 3 seconds timeout
            urlConnection.connect()
            urlConnection.responseCode == 200
        } catch (e: IOException) {
            false
        }
    }
}
