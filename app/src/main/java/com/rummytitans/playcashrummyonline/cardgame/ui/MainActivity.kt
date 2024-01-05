package com.rummytitans.playcashrummyonline.cardgame.ui

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.messaging.FirebaseMessaging
import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityHomeBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.newlogin.NewLoginActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants
import com.rummytitans.playcashrummyonline.cardgame.utils.openDeeplink
import com.rummytitans.sdk.cardgame.RummyTitanSDK
import com.rummytitans.sdk.cardgame.sdk_callbacks.AnalticsCallback
import com.rummytitans.sdk.cardgame.sdk_callbacks.RummySdkOptions
import com.rummytitans.sdk.cardgame.sdk_callbacks.RummyTitansCallback
import com.rummytitans.sdk.cardgame.ui.deeplink.DeepLinkNavigator
import org.json.JSONObject


class MainActivity : BaseActivity(),RummyTitansCallback,AnalticsCallback, DeepLinkNavigator {

    lateinit var viewModel: MainViewModel

    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().subscribeToTopic("global")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        launchRummy()
    }

    private fun launchRummy() {
        val pref = SharedPreferenceStorage(this)
        RummyTitanSDK.initialize(this)
        RummyTitanSDK.setCallback(this,this)
        RummyTitanSDK.setOptions(
            RummySdkOptions(
                currentAppType=8,
                baseUrl = MyConstants.APP_CURRENT_URL,
                baseUrl2  = viewModel.prefs.appUrl2?:"",
                gamePlayUrl = MyConstants.GAME_PLAY_URL,
                gameSplashUrl = MyConstants.SPLASH_URL,
                displayProfileIcon  = true,
                showKycOptions  = true,
                locationDelay  = 0L,
                fbInstanceId = viewModel.prefs.instanceId.toString()
            )
        )
        val endcode  = Base64.encodeToString( pref.loginResponse.toString().toByteArray(), Base64.DEFAULT)
        val endcodeSplashRes  = Base64.encodeToString( pref.splashResponse.toString().toByteArray(),
            Base64.DEFAULT)
        Log.e("intent>>>>> ",intent.getStringExtra("deepLink")?:"")
        RummyTitanSDK.startRummyTitans(
            context = this,
            encodedString = endcode,
            splashResponse = endcodeSplashRes,
            deeplink = if(intent.getStringExtra("deepLink").toString().contains("referCode"))""
            else intent.getStringExtra("deepLink")?:""
        )
    }
    override fun openDeeplink(deeplink: String?) {
        if (deeplink != null) {
            openDeeplink(deeplink,false)
        }
    }
    override fun openProfile() {
    }
    override fun redirectToHome() {

    }
    override fun sdkFinish() {
        finish()
    }

    override fun logoutUser() {
        finishAffinity()
        startActivity(Intent(this, NewLoginActivity::class.java))
    }



    override fun addTriggerSDK(key: String, value: String) {
        viewModel.analyticsHelper.addTrigger(key,value)
    }

    override fun fireAttributesEventSDK(eventName: String?, userId: String?) {
        viewModel.analyticsHelper.fireAttributesEvent(eventName,userId)
    }

    override fun fireEventSDK(key: String, bundle: Bundle?) {
        viewModel.analyticsHelper.fireEvent(key,bundle)
    }

    override fun sendEventToFireBaseSDK(eventName: String, eventData: Bundle) {
        viewModel.analyticsHelper.sendEventToFireBase(eventName,eventData)
    }

    override fun setJsonUserPropertySDK(json: JSONObject, update: Boolean) {
        viewModel.analyticsHelper.setJsonUserProperty(json)
    }

    override fun setCleverTapUserLocationSDK(lat:Double ,long:Double) {
        viewModel.analyticsHelper.setUserLocationToCT(lat,long)
    }


    override fun setUserDataToToolsSDK() {
        viewModel.analyticsHelper.setUserDataToTools(viewModel.loginResponse)
    }

    override fun setUserPropertySDK(property: String, value: String) {
        viewModel.analyticsHelper.setUserProperty(property,value)
    }

    override fun deepLinkOnNewIntent(bundle: Bundle?) {
        if(intent.getBooleanExtra("isFromClever",false)) {
            CleverTapAPI.getDefaultInstance(applicationContext)
                ?.pushNotificationClickedEvent(bundle)
        }
            }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        launchRummy()
    }

}
