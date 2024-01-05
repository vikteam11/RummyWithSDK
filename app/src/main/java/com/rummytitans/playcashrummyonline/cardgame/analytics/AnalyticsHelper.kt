package com.rummytitans.playcashrummyonline.cardgame.analytics

import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.utils.AnalyticsConstants
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.clevertap.android.sdk.CleverTapAPI

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.onesignal.OneSignal
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

class AnalyticsHelper @Inject constructor(var context: Context, var gson: Gson) {

    val mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)
    var firestore = FirebaseFirestore.getInstance()
    var clervertap: CleverTapAPI? = CleverTapAPI.getDefaultInstance(context)
    var clervertapHelper = clervertap?.let { CleverTapHelper.getInstance(context, it) }
    fun setUserLocationToCT(lat:Double ,long:Double){
        kotlin.runCatching {
            val location = Location(LocationManager.GPS_PROVIDER)
            location.latitude = lat
            location.longitude = long
            clervertap?.location = location
        }
    }
    fun setUserProperty(property: String, value: String) {
        try {
            mFirebaseAnalytics.setUserProperty(property, value)
            OneSignal.sendTag(property, value)
            // pushPropertiesOnFirestore(property, value)
            val map = HashMap<String, Any>()
            map[property] = value
            setJsonUserPropertyClevertap(map,true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setJsonUserPropertyClevertap(map: HashMap<String, Any>,update:Boolean=false) {
        try {
            if(map.contains(AnalyticsKey.Properties.FullName)){
                changeKeyFromMap(map,AnalyticsKey.Properties.FullName,AnalyticsKey.Properties.Name)
            }
            if(map.contains(AnalyticsKey.Properties.Mobile)){
                changeKeyFromMap(map,AnalyticsKey.Properties.Mobile,AnalyticsKey.Properties.Phone)
            }
            if(map.contains(AnalyticsKey.Properties.UserID)){
                map[AnalyticsKey.Properties.Identity] = map[AnalyticsKey.Properties.UserID]?:""
            }
            if(map.contains(AnalyticsKey.Properties.Gender)){
                map[AnalyticsKey.Properties.Gender] = when(map[AnalyticsKey.Properties.Gender]?:""){
                    "Male" -> "M"
                    "Female" -> "F"
                    else -> "O"
                }
            }

            if(update){
                clervertap?.pushProfile(map)
            }else{
                clervertap?.onUserLogin(map)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setJsonUserProperty(json: JSONObject,update:Boolean=false) {
        try {
            val map = gson.fromJson<HashMap<String, Any>>(json.toString(), HashMap::class.java)
            for ((key, value) in map) {
                val valMap=json.get(key)
                map[key]=valMap
                mFirebaseAnalytics.setUserProperty(key, value.toString())
                // pushPropertiesOnFirestore(key, value.toString())
            }
            setJsonUserPropertyClevertap(map,update)
            OneSignal.sendTags(json)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun changeKeyFromMap(map: HashMap<String, Any>,oldKey:String,newKey:String){
        map[newKey] = map[oldKey]?:""
        map.remove(oldKey)
        if(map.containsKey(AnalyticsKey.Properties.Phone)) {
            var phoneNo = "+91" + map[AnalyticsKey.Properties.Phone] ?: ""
            map[AnalyticsKey.Properties.Phone] = phoneNo
        }
    }


    fun setUserID(userID: String?) {
        mFirebaseAnalytics.setUserId(userID)
        OneSignal.setExternalUserId(userID?:"")
        fireLoginEvent(userID)
    }

    fun setUserDataToTools(loginResponse: LoginResponse?){
        kotlin.runCatching {
            loginResponse?.apply {
                OneSignal.setEmail(Email)
                OneSignal.setSMSNumber(Mobile)
            }
        }
    }

    fun updateEndPointValue(loginResponse: LoginResponse?) {
    }

    fun fireLoginEvent(userId: String?) {
        // smartech.login(userId)
    }

    fun fireLogoutEvent() {
        OneSignal.removeExternalUserId(object :OneSignal.OSExternalUserIdUpdateCompletionHandler {
            @Override
            fun onComplete(results: JSONObject) {
                Log.d("fireLogoutEvent","onSuccess ")

                // The results will contain push and email success statuses
                // OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "Remove external user id done with results: " + results.toString());
                // Push can be expected in almost every situation with a success status, but     // as a pre-caution its good to verify it exists
                if (results.has("push") && results.getJSONObject("push").has("success")) {
                    val isPushSuccess = results.getJSONObject ("push").getBoolean("success");
                    OneSignal.onesignalLog(
                        OneSignal.LOG_LEVEL.VERBOSE,
                        "Remove external user id for push status: " + isPushSuccess
                    );
                    // }
                    // Verify the email is set or check that the results have an email success status
                    if (results.has("email") && results.getJSONObject("email").has("success")) {
                        val isEmailSuccess = results.getJSONObject ("email").getBoolean("success");
                        OneSignal.onesignalLog(
                            OneSignal.LOG_LEVEL.VERBOSE,
                            "Remove external user id for email status: " + isEmailSuccess
                        );
                    }
                }
            }

            override fun onSuccess(p0: JSONObject?) {
                Log.d("fireLogoutEvent","onSuccess ")
            }

            override fun onFailure(p0: OneSignal.ExternalIdError?) {
                Log.d("fireLogoutEvent","onFailure ")
            }
        })
        //  smartech.logoutAndClearUserIdentity(true)
    }

    fun addTrigger(key:String,value: String) {
        OneSignal.addTrigger(key,value)
        //  smartech.logoutAndClearUserIdentity(true)
    }

    fun sendEventToFireBase(eventName: String, eventData: Bundle) {
        eventData.putString(AnalyticsKey.Keys.Platform,if (BuildConfig.isPlayStoreApk==1) "AndroidPlayStore" else "Android")
        mFirebaseAnalytics.logEvent(eventName, eventData)
    }

    fun fireAttributesEvent(eventName: String?, userId: String?) {
        val eventValue: HashMap<String, Any> = HashMap()
        eventValue[AnalyticsConstants.USER_ID] = userId ?: ""
        eventValue[AnalyticsKey.Keys.Platform]=if (BuildConfig.isPlayStoreApk==1) "AndroidPlayStore" else "Android"
        AppsFlyerLib.getInstance().logEvent(context, eventName, eventValue)
    }

    fun fireEvent(key: String, bundle: Bundle? = Bundle()) {
        if (bundle == null) return
        fireAppxorEvent(key, bundle)
        val cleverTechMap=HashMap<String,Any>()
        cleverTechMap[AnalyticsKey.Keys.Platform.lowercase()] =  "Android"
        for (keyName in bundle.keySet()) {
            val value=bundle[keyName]
            cleverTechMap[keyName.lowercase()] = value.toString()
        }
        clervertap?.pushEvent(key, cleverTechMap)
    }

    private fun fireAppxorEvent(eventName: String, eventData: Bundle) {
        try {
            eventData.putString(AnalyticsKey.Keys.Platform.lowercase(),if (BuildConfig.isPlayStoreApk==1) "AndroidPlayStore" else "Android")
            eventData.putInt(AnalyticsKey.Keys.AppId,8)
            mFirebaseAnalytics.logEvent(eventName, eventData)
            val map= JSONObject()
            for(key in eventData.keySet()){
                map.put(key.lowercase(), eventData.get(key)?:"")
            }
            OneSignal.sendTags(map)
            // pushEventOnFirestore(eventName,map)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*fun pushEventOnFirestore(eventName: String, json: JSONObject) {
        val doc = firestore.collection("androidEvents").document()
        val map: HashMap<String, Any> = HashMap()
        map["EventName"] = eventName
        map["Parameter"] = json.toString()
        map["timestamp"] = Date()
        doc.set(map).addOnCompleteListener { task ->
            if (task.isSuccessful) Log.wtf("Firestore Event Push -->", eventName)
        }
    }

    fun pushPropertiesOnFirestore(name: String, value: String) {
        val doc = firestore.collection("androidProperties").document()
        val map: HashMap<String, Any> = HashMap()
        map["PropertyName"] = name
        map["PropertyValue"] = value
        map["timestamp"] = Date()
        doc.set(map).addOnCompleteListener { task ->
            if (task.isSuccessful) Log.wtf("Firestore Properties Push -->", name)
        }
    }*/

}