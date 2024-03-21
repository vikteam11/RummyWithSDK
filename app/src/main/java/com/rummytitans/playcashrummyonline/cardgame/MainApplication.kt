package com.rummytitans.playcashrummyonline.cardgame

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.appsflyer.deeplink.DeepLinkResult
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.onesignal.OneSignal
import com.rummytitans.playcashrummyonline.cardgame.analytics.AbstractApplicationLifeCycleHelper
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@HiltAndroidApp
class MainApplication : Application(), LifecycleEventObserver,
    CTPushNotificationListener, CTPushAmpListener {
    var isNotificationApiUpdateRequire = false
    var showGameAnimation = false
    private var applicationLifeCycleHelper: AbstractApplicationLifeCycleHelper? = null
    private val AF_DEV_KEY = "RSVXVunkaMb3wCuLjhdRXE"
    var isAppBackgroud = false
    var isFromBackground = false
    override fun onCreate() {
        ActivityLifecycleCallback.register(this)
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG);
        manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        sAnalytics = GoogleAnalytics.getInstance(this)
        sAnalytics!!.enableAutoActivityReports(this)
        analyticsHelper = AnalyticsHelper(this, Gson())
        sAnalytics!!.newTracker(R.xml.global_tracker).enableAutoActivityTracking(true)
        //initAppsFlyer();
        initOneSignal()
        initClevertap()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        applicationLifeCycleHelper = object : AbstractApplicationLifeCycleHelper() {
            override fun onActivityPaused(activity: Activity) {
                super.onActivityPaused(activity)
            }

            override fun applicationEnteredForeground() {
                //  analyticsHelper.getPinpointHelper().startSession();
            }

            override fun applicationEnteredBackground() {
                // analyticsHelper.getPinpointHelper().stopSession();
            }

            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                super.onActivityCreated(activity, bundle)
                forgroundActivityRef = WeakReference<Activity??>(activity)
            }

            override fun onActivityResumed(activity: Activity) {
                super.onActivityResumed(activity)
                forgroundActivityRef = WeakReference<Activity?>(activity)
            }
        }
        registerActivityLifecycleCallbacks(applicationLifeCycleHelper)
        createNotificationChannel()
    }

    private fun initClevertap() {
        CoroutineScope(Dispatchers.Default).launch {
            val clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(
                applicationContext
            )
            clevertapDefaultInstance!!.enableDeviceNetworkInfoReporting(true)
            CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG)
            clevertapDefaultInstance.ctPushNotificationListener = this@MainApplication
            clevertapDefaultInstance.ctPushAmpListener = this@MainApplication
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                analyticsHelper!!.clervertapHelper!!.createNotificationChannels()
            }
            clevertapDefaultInstance.getCleverTapID { cleverTapID -> // Callback on main thread
                initAppsFlyer(cleverTapID)
            }
        }
    }

    private fun initOneSignal() {
        CoroutineScope(Dispatchers.Default).launch {
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
            OneSignal.initWithContext(this@MainApplication)
            OneSignal.setAppId("265de366-b8ec-4cac-93f0-2b17c532ce98")
            OneSignal.promptForPushNotifications()
        }
    }

    fun initAppsFlyer(cleverTapId: String?) {
        val conversionListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val isFirstLaunch = conversionData["is_first_launch"] as Boolean
                for (attrName in conversionData.keys) {
                    Log.d(
                        "LOG_TAG",
                        "onConversionDataSuccess: " + attrName + " = " + conversionData[attrName]
                    )
                    if (attrName.equals("campaignId", ignoreCase = true)) {
                        val pref = SharedPreferenceStorage(
                            applicationContext
                        )
                        pref.campaignId = conversionData[attrName].toString()
                    }
                    if (attrName.equals("refercode", ignoreCase = true)) {
                        val pref = SharedPreferenceStorage(
                            applicationContext
                        )
                        pref.referCode = conversionData[attrName].toString()
                    }
                    if (attrName.equals("deep_link_value", ignoreCase = true) && isFirstLaunch) {
                        val deeplinkValue = conversionData[attrName].toString()
                        handleAppsFlyerDeepLink(deeplinkValue)
                    }
                }
            }

            override fun onConversionDataFail(errorMessage: String) {
                Log.d("LOG_TAG", "error onConversionDataFail: $errorMessage")
            }

            override fun onAppOpenAttribution(attributionData: Map<String, String>) {
                for (attrName in attributionData.keys) {
                    Log.d(
                        "LOG_TAG",
                        "onAppOpenAttribution: " + attrName + " = " + attributionData[attrName]
                    )
                }
            }

            override fun onAttributionFailure(errorMessage: String) {
                Log.d("LOG_TAG", "error onAttributionFailure : $errorMessage")
            }
        }
        val appsFlyerLib = AppsFlyerLib.getInstance()
        appsFlyerLib.init(AF_DEV_KEY, conversionListener, this)
        appsFlyerLib.start(this, AF_DEV_KEY, object : AppsFlyerRequestListener {
            override fun onSuccess() {
                Log.d("AppflyerStatus", " onSuccess : ")
            }

            override fun onError(i: Int, s: String) {
                Log.d("AppflyerStatus", "onError   $i message $s")
            }
        })
        appsFlyerLib.setDebugLog(true)
        appsFlyerLib.setCustomerUserId(cleverTapId)
        appsFlyerLib.subscribeForDeepLink { deepLinkResult: DeepLinkResult ->
            val deeplinkValue = deepLinkResult.deepLink.deepLinkValue
            handleAppsFlyerDeepLink(deeplinkValue)
        }
    }

    private fun handleAppsFlyerDeepLink(deeplinkValue: String?) {
        // Toast.makeText(this,"AppsFlyer DeepLinks-->",Toast.LENGTH_LONG).show();
        Log.d("LOG_TAG", "hanadle $deeplinkValue")
        if (deeplinkValue != null) {
            if (deeplinkValue.contains("game")) {
                Log.d("AppsFlyer DeepLinks-->", deeplinkValue)
                openGameAppFlyer = true
                val pref = SharedPreferenceStorage(applicationContext)
                pref.appsFlyerDeepLink = deeplinkValue
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event.name == "ON_STOP") {
            isAppBackgroud = true
            isFromBackground = false
        } else if (event.name == "ON_START") {
            isAppBackgroud = false
            isFromBackground = true
        }
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                getString(R.string.app_name),
                "RummyTitans",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "RummyTitans"
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNotificationClickedPayloadReceived(payload: HashMap<String, Any>) {
        Log.e("payload is >>>", payload.toString())
    }

    override fun onPushAmpPayloadReceived(extras: Bundle) {}

    companion object {
        var forgroundActivityRef: WeakReference<Activity?>? = null
        private var sAnalytics: GoogleAnalytics? = null
        var analyticsHelper: AnalyticsHelper? = null
        var manager: ClipboardManager? = null
        var appUrl = ""
        var openGameAppFlyer = false
        fun fireEvent(key: String?, bundle: Bundle?) {
            analyticsHelper!!.fireEvent(
                key!!, bundle
            )
        }
    }
}