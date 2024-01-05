package com.rummytitans.playcashrummyonline.cardgame.utils.services

// InternetCheckService.kt
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class InternetCheckService : Service() {
    private var isServiceRunning = false
    private val connectivityCheckIntervalMillis:Long = 10000 // 10 seconds
    private val handler = Handler()

    private val runAble  = object :Runnable {
        override fun run() {
            val connected = checkInternetConnectivity()
            val intent = Intent(INTENT_CONNECTIVITY_CHANGE)
            intent.putExtra(EXTRA_CONNECTIVITY_STATUS,connected)
            sendBroadcast(intent)
            handler.postDelayed(this,connectivityCheckIntervalMillis)
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Check for internet connectivity here
        if(!isServiceRunning){
            isServiceRunning = true
            handler.postDelayed(runAble,connectivityCheckIntervalMillis)
        }
        return START_STICKY
    }

    private fun checkInternetConnectivity(): Boolean {
        var isConnected = true/* Check internet connectivity */
        val url = URL("https://www.google.com") // Change this URL to a known server
        try {
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 2000 // 3 seconds timeout
            urlConnection.connect()
            isConnected = urlConnection.responseCode == 200
        } catch (e: IOException) {
            isConnected = false
        }
        return isConnected
    }

    companion object {
        private const val TAG = "InternetCheckService"
        const val INTENT_CONNECTIVITY_CHANGE = "your.package.name.CONNECTIVITY_CHANGE"
        const val EXTRA_CONNECTIVITY_STATUS = "connectivity_status"
    }
}
