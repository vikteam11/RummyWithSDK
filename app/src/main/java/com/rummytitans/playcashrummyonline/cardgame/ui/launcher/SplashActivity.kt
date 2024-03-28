package com.rummytitans.playcashrummyonline.cardgame.ui.launcher

import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.models.VersionModel
import com.rummytitans.playcashrummyonline.cardgame.observer.MyFirebaseMessagingService
import com.rummytitans.playcashrummyonline.cardgame.ui.MainActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.AppUpdateActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.newlogin.NewLoginActivity
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.webkit.URLUtil
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.appsflyer.AppsFlyerLib
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.rummytitans.playcashrummyonline.cardgame.MainApplication
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivitySplashBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.WebViewActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.AppUpdateBottomSheetActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseNavigator
import com.rummytitans.playcashrummyonline.cardgame.ui.onboarding.OnBoardingActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.*
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.APP_UPDATE_FULL_SCREEN
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
@AndroidEntryPoint
class SplashActivity : AppCompatActivity(),
    BaseNavigator {
    lateinit var binding: ActivitySplashBinding
    lateinit var viewModel: LaunchViewModel
    private val fromAppUpdateBottomSheet: Int = 1221

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.transparentStatusBar()
        super.onCreate(savedInstanceState)
        AppsFlyerLib.getInstance().sendPushNotificationData(this)
        viewModel = ViewModelProvider(
            this
        ).get(LaunchViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        FirebaseMessaging.getInstance().subscribeToTopic("global")
        fetchAdvertisingId()
        viewModel.navigator = this
        viewModel.myDialog= MyDialog(this)

        if (viewModel.prefs.firstOpen) {
            viewModel.prefs.firstOpen = false
        }

        viewModel.showGameAnimationLiveData.observe(this) {
            (application as? MainApplication)?.showGameAnimation = true
        }

        viewModel.versionResp.observe(this) {
            showLanguage(it)
            viewModel.prefs.let { pref ->
                pref.splashImageUrl = it.SplashImage
            }
        }


        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.global))

        if (TextUtils.isEmpty(viewModel.prefs.androidId)) {
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)?.let {
                viewModel.prefs.androidId = it
            }
        }

        viewModel.failedReason.observe(this, Observer {
            if (it.message == "timeout") {
                MyDialog(this).noInternetDialog { apiCall() }.findViewById<TextView>(R.id.txtCancel).setOnClickListener { finish() }
            } else showError(R.string.something_went_wrong_restart)
        })

        viewModel.analyticsHelper.fireEvent(AnalyticsKey.Names.AppOpened, bundleOf())

        viewModel.prefs.onSafePlay = true
        viewModel.analyticsHelper.fireEvent(
            AnalyticsKey.Names.ScreenLoadDone, bundleOf(
                AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.SPLASH
            )
        )

        getFAInstanceId()

    }

    private fun getFAInstanceId(){
        lifecycleScope.launch(Dispatchers.IO){
            FirebaseAnalytics.getInstance(this@SplashActivity).appInstanceId.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.prefs.instanceId = task.result
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (packageName == "com.rummytitans.playcashrummyonline.cardgame") apiCall()
        else Toast.makeText(this, "Please use original ${getString(R.string.app_name)} App.", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        Firebase.dynamicLinks.getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                pendingDynamicLinkData?.let {
                    val deepLink = pendingDynamicLinkData.link
                    val referCode = deepLink?.getQueryParameter("referCode")
                    viewModel.prefs.referCode = referCode
                }
            }.addOnFailureListener(this) { e -> println("getDynamicLink:onFailure$e") }
    }

    private fun fetchAdvertisingId() {

           val d= Observable.fromCallable {
               com.rummytitans.playcashrummyonline.cardgame.AdvertisingIdClient.getAdvertisingIdInfo(this)?.id
           }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
               .doOnError {
                   it.printStackTrace()
               }
               .subscribe( {
                   if (it==null) return@subscribe
                   viewModel.prefs.advertisingId = it
               },{
                   it.printStackTrace()
               })

    }

    //check received DeepLinks
    private fun checkFireBaseDeepLinks():Intent?{
        var resultIntent:Intent?=null
        intent.extras?.let {data->
            val screenName=data.getString(MyFirebaseMessagingService.FCM_PARAM_SEND_TO)?:""
             if(!TextUtils.isEmpty(screenName)){
                 val info = CleverTapAPI.getNotificationInfo(data)
                 resultIntent = Intent(this, MainActivity::class.java)
                     .putExtra("deepLink", data.getString("deeplink"))
                     .putExtra("isFromClever",info.fromCleverTap)
             }
        }
        return resultIntent
    }

    private fun apiCall() {
        viewModel.fetchVersion()
    }

    private fun checkConditions(model: VersionModel) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            kotlin.runCatching {
                (this as LifecycleOwner).lifecycleScope.launchWhenResumed {
                    moveToNextScreen(model)
                }
            }.onFailure {
                moveToNextScreen(model)
            }
        }else{
            moveToNextScreen(model)
        }

    }

    private fun moveToNextScreen(model: VersionModel) {
        //        model.IsAppUpdate=true
       //model.ForceUpdate=true
        //   model.showUpdateOnSplash = true
        //   model.playStoreApkUpdateFrom = model.UPDATE_FROM_IN_APP_UPDATE
//        model.UpdateType = APP_UPDATE_FULL_SCREEN
        // model.DownLoadURl="https://a3.files.diawi.com/app-file/WkBqSj9nU9ldYvc2RMZ0.apk"

        when {
            model.RunOnWeb -> {
                if (TextUtils.isEmpty(model.WebURl) || !URLUtil.isValidUrl(model.WebURl)) return
                startActivity(
                    Intent(this, WebViewActivity::class.java)
                        .putExtra(MyConstants.INTENT_PASS_WEB_URL, model.WebURl)
                        .putExtra(
                            MyConstants.INTENT_PASS_WEB_TITLE, getStringResource(R.string.app_name)
                        )
                )
            }

            model.IsAppUpdate -> {
                if (!model.ForceUpdate){  // redirect only if force update not available
                    if (!model.showUpdateOnSplash){ //redirect only if update on splash not allowed
                        redirectUser()
                        return
                    }
                }
                if (redirectUserToHome(model)){
                    redirectUser()
                    return
                }
                if (model.UpdateType == APP_UPDATE_FULL_SCREEN){
                    val intent = Intent(this, AppUpdateActivity::class.java)
                        .putExtra("UPDATE_FROM_SPLASH", true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }else{
                    val intent =  Intent(this, AppUpdateBottomSheetActivity::class.java)
                        .putExtra("UPDATE_FROM_SPLASH", true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivityForResult(intent,fromAppUpdateBottomSheet)
                }
            }

            else -> {
                binding.root.postDelayed({
                    redirectUser()
                },3000)

            }
        }
    }

    /**redirect user for play store apk,
     * 1.no data from playStore updates
     * 2.not force update*/
    private fun isAppUpdateAvailable(model: VersionModel):Boolean{
        return model.IsAppUpdate && viewModel.prefs.isInAppAvailable
    }

    /**redirect user for play store apk,
     * 1.no data from playStore updates
     * 2.not force update*/
    private fun redirectUserToHome(model: VersionModel):Boolean{
        return if (BuildConfig.isPlayStoreApk==1){
            !viewModel.prefs.isInAppAvailable
        }else false
    }

    private fun redirectUser(){
        if (viewModel.prefs.loginCompleted) {
            val loginModel: LoginResponse? = viewModel.gson.fromJson(
                viewModel.prefs.loginResponse,
                LoginResponse::class.java
            )
            viewModel.analyticsHelper.setUserID(loginModel?.UserId.toString())
            val fireBaseIntent=checkFireBaseDeepLinks()
            fireBaseIntent?.let {
                startActivity(it)
                viewModel.prefs.isOldUser = true
                finish()
                return
            }

            val i = Intent(this, MainActivity::class.java)
            if (!TextUtils.isEmpty(viewModel.prefs.appsFlyerDeepLink)) {
                i.putExtra("comingForGame", true)
                viewModel.prefs.appsFlyerDeepLink = ""
            }
            startActivity(i)
            viewModel.prefs.isOldUser = true
            finish()
        } else {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
            overridePendingTransition(0, 0)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fromAppUpdateBottomSheet) {
            redirectUser()
        }
    }

    private fun showLanguage(model: VersionModel) {
        checkConditions(model)
    }

    override fun goBack() {
        finishAffinity()
    }

    override fun handleError(throwable: Throwable?) {}

    override fun showError(message: String?) {}

    override fun showError(message: Int?) {}

    override fun showMessage(message: String?) {}

    override fun getStringResource(resourseId: Int) = getString(resourseId)

    override fun signOutUser() {
        showError(R.string.err_session_expired)
        finishAffinity()
        startActivity(Intent(this, NewLoginActivity::class.java))
    }

    override fun onBackPressed() {
        viewModel.versionResp.value?.let {
            checkConditions(it)
        }
        super.onBackPressed()
    }
}
