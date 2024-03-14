package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityAppUpdateBinding
import com.rummytitans.playcashrummyonline.cardgame.models.VersionModel
import com.rummytitans.playcashrummyonline.cardgame.ui.MainActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.update_utils.BaseAppUpdateActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.launcher.LaunchDestination
import com.rummytitans.playcashrummyonline.cardgame.ui.newlogin.NewLoginActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.onboarding.OnBoardingActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.APP_UPDATE_FULL_SCREEN
import com.rummytitans.playcashrummyonline.cardgame.utils.sendToPlayStore
import android.content.Intent
import android.os.*
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityAppUpdateOldBinding
import com.rummytitans.playcashrummyonline.cardgame.models.CarouselModel
import com.rummytitans.playcashrummyonline.cardgame.utils.sendToExternalBrowser
import java.lang.Exception
import java.lang.StringBuilder

class AppUpdateActivity : BaseAppUpdateActivity() {
    lateinit var binding: ActivityAppUpdateOldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel =
            ViewModelProvider(this).get(
                AppUpdateViewModel::class.java
            )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_app_update_old)
        binding.lifecycleOwner = this
        binding.viewmodel = mViewModel
        mViewModel.navigatorAct = this
        mViewModel.navigator = this
        implementView()
    }

    private fun implementView() {
        val fromSplash: Boolean = intent.getBooleanExtra("UPDATE_FROM_SPLASH", false)
        if (fromSplash) binding.icBack.visibility = View.GONE else {
            binding.icBack.visibility = View.VISIBLE
            binding.icBack.setOnClickListener { view: View? -> onBackPressed() }
        }
        val appUpdateString: StringBuilder = StringBuilder()
            .append(getString(R.string.version))
            .append(": ")
            .append(mViewModel.versionResp.newAppVersionCode)
        binding.txtVersion.text = appUpdateString
        binding.txtUpdateLater.setOnClickListener { onUpdateLaterClick(mViewModel.versionResp) }
        binding.btnUpdateApp.setOnClickListener {
            val versionModel: VersionModel = mViewModel.versionResp
            if(BuildConfig.installFrom ==2){
                sendToExternalBrowser(this,versionModel.WebURl)
            }else{
                when (versionModel.playStoreApkUpdateFrom) {

                    versionModel.UPDATE_FROM_IN_APP_UPDATE ->
                        mInAppUpdateHelper?.requestInAppUpdateAvailability {appUpdateInfo->
                            if(appUpdateInfo!=null)
                                mInAppUpdateHelper?.startInAppUpdateIntent()
                            else
                                sendToPlayStore(this, packageName)
                            onBackPressed()
                        }

                    versionModel.UPDATE_FROM_APP_STORE ->
                        sendToPlayStore(this, packageName)

                    else -> onDownloadPerform()
                }
            }

        }

        val updateList = arrayListOf(
            CarouselModel().apply {
                Title = mViewModel.versionResp.Title
                Description = mViewModel.versionResp.Message
                ImageIcon = R.drawable.ic_new_update
            }
        )

        setAdapter(
            binding.viewPager,
            binding.tabs,
            mViewModel.versionResp.updateDetailList?:updateList,
            APP_UPDATE_FULL_SCREEN
        )
    }

    override fun onDownloadingFailed(e: Exception?) {
        runOnUiThread {
            binding.progressBar.visibility = View.GONE
            binding.txtDownloadingStatus.text = "Downloading Failed"
        }
    }

    override fun onDownloadingStart() {
        binding.txtDownloadingStatus.text = getString(R.string.txt_downloading_started)
        binding.txtDownloadingStatus.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.btnUpdateApp.visibility = View.GONE
        binding.txtUpdateLater.visibility = View.GONE
    }

    override fun onDownloadingProgress(progress: Int) {
        runOnUiThread {
            binding.progressBar.progress = progress
            binding.txtDownloadingStatus.text =
                progress.toString() + getString(R.string.txt_percentage_completed)
        }
    }

    override fun onDownloadingComplete() {
        runOnUiThread {
            binding.progressBar.visibility = View.GONE
            binding.txtDownloadingStatus.text = getString(R.string.txt_downloading_completed)
            installApk()
        }
    }

    override fun onBackPressed() {
        if (comeFrom == "MainActivity") {
            finish()
        } else if (!mViewModel.versionResp.ForceUpdate) {
            when {
                mViewModel.launchDestination === LaunchDestination.MAIN_ACTIVITY ->
                    startActivity(Intent(this, MainActivity::class.java))

                mViewModel.launchDestination === LaunchDestination.LOGIN_ACTIVITY ->
                    startActivity(Intent(this, NewLoginActivity::class.java))

                mViewModel.launchDestination === LaunchDestination.ONBOARDING ->
                    startActivity(Intent(this, OnBoardingActivity::class.java))
            }
            finish()
        } else {
            finish()
        }
    }
}