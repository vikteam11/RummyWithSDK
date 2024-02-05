package com.rummytitans.playcashrummyonline.cardgame.ui.deeplink

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityDeepLinkBinding
import com.rummytitans.playcashrummyonline.cardgame.models.MatchModel
import com.rummytitans.playcashrummyonline.cardgame.ui.MainActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.AppUpdateActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.common.CommonFragmentActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.newlogin.NewLoginActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.*
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.appsflyer.AppsFlyerLib
import com.clevertap.android.sdk.CleverTapAPI
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase

class DeepLinkActivity : BaseActivity(), DeepLinkNavigator {


    lateinit var viewModel: DeepLinkViewModel
    lateinit var binding: ActivityDeepLinkBinding

    var comingFor = 0
    val MATCH = 1
    val CONTEST = 2
    val CREATE_TEAM = 3
    val SPORTS_CHANGE = 4
    val APP_TYPE_CHANGE = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DeepLinkViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deep_link)
        binding.lifecycleOwner = this
        AppsFlyerLib.getInstance().sendPushNotificationData(this)
        viewModel.apply {
            navigator = this@DeepLinkActivity
            navigatorAct = this@DeepLinkActivity
            myDialog = MyDialog(this@DeepLinkActivity)
            binding.viewModel = this
            checkForceUpdate()
        }

        viewModel.isForceUpdate.observe(this, Observer {model->
            if (model.ForceUpdate) {
                if (model.playStoreApkUpdateFrom==model.UPDATE_FROM_APP_STORE)
                    sendToPlayStore(this,packageName)
                else{
                    startActivity(
                        Intent(this, AppUpdateActivity::class.java)
                            .putExtra("UPDATE_FROM_SPLASH", true))
                }
                finish()
            } else {
                handleIntent(intent)
            }
        })

        if (intent.hasExtra("data")) {
            val data = intent.getStringExtra("data")
            redirection(data)
        }

        binding.executePendingBindings()
    }

    private fun fetchDataFromFirebaseDynamicLink(){
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                Log.e("inAppTest", "getDynamicLink:addOnSuccessListener "+pendingDynamicLinkData?.link)
                val link =  pendingDynamicLinkData?.link?.toString()?:""
                var splitText = ""
                splitText = if(link.contains(MyConstants.AppDeeplink)){
                    MyConstants.AppDeeplink
                }else{
                    MyConstants.AppDeeplink_
                }
                link.split("$splitText://?").
                elementAtOrNull(1)?.let {
                    kotlin.runCatching {
                        val key= it.split("=")[0]
                        val value= it.split("=")[1]
                        redirection(link)
                    }
                }
            }
            .addOnFailureListener(this) {  Log.e("inAppTest", "getDynamicLink:onFailure $it") }
    }

    fun handleIntent(deepIntent: Intent?) {
        if (!viewModel.prefs.loginCompleted) {
            showError(R.string.err_login_account_to_continue)
            startActivity(Intent(this, NewLoginActivity::class.java))
            finishAffinity()
            return
        }
        if (Intent.ACTION_VIEW == deepIntent?.action && null != deepIntent.data) {
            val data = deepIntent.data?.query ?: deepIntent.data?.lastPathSegment ?: ""
            redirection(deepIntent.data.toString())
        }

        if (intent.hasExtra("deepLink")) {
            val deeplinkUrl = intent.getStringExtra("deepLink") ?: ""
            if (deeplinkUrl.contains("?")) {
                val params = deeplinkUrl.split("?")[1]

                redirection(deeplinkUrl)
            }
        }
        if (intent.hasExtra("fireBaseDeeplink")) {
            val inAppDeeplink = intent.getStringExtra("fireBaseDeeplink") ?: ""
            inAppDeeplink.split("//?")
                .elementAtOrNull(1)
                ?.split("=")
                ?.let {
                    redirection(inAppDeeplink)
                }
        }
        fetchDataFromFirebaseDynamicLink()
    }

    //matchId=12345&staId=1232
    private fun getKeyValueOfQuery(query: String?): Map<String, String> {
        val map = HashMap<String, String>()
        val pairs = query?.split("&")
        pairs?.forEach { pair ->
            if (pair.contains("=")) {
                val split = pair.split("=")
                if (split.size == 2) map[split[0]] = split[1]
                else if (split.size == 1) map[split[0]] = ""
            }else{
                if (viewModel.prefs.loginCompleted) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }else{
                    startActivity(Intent(this, NewLoginActivity::class.java))
                    finishAffinity()
                }
            }
        }
        return map
    }

    fun redirection(map: String?) {
        val info = CleverTapAPI.getNotificationInfo(intent.extras)
        startActivity(Intent(this, MainActivity::class.java)
            .putExtra("deepLink", map)
            .putExtra("isFromClever",info.fromCleverTap))
        finish()
    }



    override fun sendToContestActivity(matchModel: MatchModel?) {

    }

    override fun finishAllAndCallMainActivity() {
        finishAffinity()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun finishActivity() {
        Handler(mainLooper).postDelayed({
            if (isTaskRoot)
                finishAllAndCallMainActivity()
            else
                finish()
        },2000)
    }


}

interface DeepLinkNavigator {
    fun sendToContestActivity(matchModel: MatchModel?)
    fun finishAllAndCallMainActivity()
    fun finishActivity()
}