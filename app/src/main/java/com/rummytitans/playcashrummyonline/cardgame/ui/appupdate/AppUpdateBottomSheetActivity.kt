package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityBottomsheetAppUpdateBinding
import com.rummytitans.playcashrummyonline.cardgame.databinding.BottomSheetAppUpdateBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.update_utils.BaseAppUpdateActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants
import com.rummytitans.playcashrummyonline.cardgame.utils.bottomsheets.BottomSheetDialogBinding
import com.rummytitans.playcashrummyonline.cardgame.utils.sendToPlayStore
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rummytitans.playcashrummyonline.cardgame.models.CarouselModel
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.APP_UPDATE_BOTTOM_SHEET


class AppUpdateBottomSheetActivity : BaseAppUpdateActivity() ,BottomSheetEvents{

    lateinit var binding: ActivityBottomsheetAppUpdateBinding
    private var updateDialog:BottomSheetDialogBinding<BottomSheetAppUpdateBinding>?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(
            ContextCompat
                .getDrawable(this, android.R.drawable.screen_background_dark_transparent)
        )
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bottomsheet_app_update)
        binding.lifecycleOwner = this
        mViewModel.navigatorAct=this
        mViewModel.navigator=this
        binding.viewModel = mViewModel
        binding.sheetListner=this
        implementView()
    }

    private fun implementView() {
       // showUpdateDialog()
        val appUpdateString = StringBuilder()
            .append(getString(R.string.version))
            .append(": ")
            .append(mViewModel.versionResp.newAppVersionCode)
        binding.bottomSheet.apply {
            txtVersion.text = appUpdateString
            txtUpdateLater.setOnClickListener {
                onUpdateLaterClick(mViewModel.versionResp)
            }
            btnUpdateApp.setOnClickListener {
                val versionModel = mViewModel.versionResp
                when (versionModel.playStoreApkUpdateFrom) {

                    versionModel.UPDATE_FROM_APP_STORE -> {
                        sendToPlayStore(this@AppUpdateBottomSheetActivity, packageName)
                        if (!mViewModel.versionResp.ForceUpdate) onSheetClose()
                    }

                    versionModel.UPDATE_FROM_IN_APP_UPDATE ->
                        mInAppUpdateHelper?.requestInAppUpdateAvailability {appUpdateInfo->
                            if(appUpdateInfo!=null)
                                mInAppUpdateHelper?.startInAppUpdateIntent()
                            else
                                sendToPlayStore(this@AppUpdateBottomSheetActivity, packageName)
                            onSheetClose(false)
                        }

                    else -> onDownloadPerform()
                }
            }
            val updateList = arrayListOf(
                CarouselModel().apply {
                    Title = mViewModel.versionResp.Title
                    Description = mViewModel.versionResp.Message
                }
            )
            setAdapter(viewPager, tabs, mViewModel.versionResp.updateDetailList?:updateList,APP_UPDATE_BOTTOM_SHEET)
            ivClose.setOnClickListener { onSheetClose() }
        }
        updateDialog?.behavior?.isDraggable =!mViewModel.versionResp.ForceUpdate
    }

    override fun onDownloadingFailed(e: Exception?) {
        runOnUiThread {
//            updateDialog?.binding?.progressBar?.visibility = View.GONE
//            updateDialog?.binding?.txtDownloadingStatus?.text = "Downloading Failed"
            binding.bottomSheet.progressBar.visibility = View.GONE
            binding.bottomSheet.txtDownloadingStatus?.text = "Downloading Failed"
        }
    }

    override fun onDownloadingStart() {
        binding.bottomSheet.apply {
            txtDownloadingStatus.text = getString(R.string.txt_downloading_started)
            txtDownloadingStatus.visibility = View.VISIBLE
            progressBar.visibility = View.VISIBLE
            btnUpdateApp.visibility = View.GONE
            txtUpdateLater.visibility = View.GONE
        }
    }

    override fun onDownloadingProgress(progress: Int) {
        runOnUiThread {
            binding.bottomSheet.progressBar.progress = progress
            binding.bottomSheet.txtDownloadingStatus.text = progress.toString() + getString(R.string.txt_percentage_completed)
        }
    }

    override fun onDownloadingComplete() {
        runOnUiThread {
            /* binding.btnUpdateApp.setVisibility(View.VISIBLE);
            binding.txtUpdateLater.setVisibility(View.VISIBLE);*/
            binding.bottomSheet.progressBar?.visibility = View.GONE
            binding.bottomSheet.txtDownloadingStatus?.text = getString(R.string.txt_downloading_completed)
            installApk()
        }
    }

    override fun onBackPressed() {
        onSheetClose()
    }

    override fun onSheetClose(finishActivity: Boolean){
        if (mViewModel.versionResp.ForceUpdate)
            finishAffinity()
        else{
            //updateDialog?.dismiss()
            mViewModel._bottomSheetStateEvent.value= BottomSheetBehavior.STATE_HIDDEN
            Handler(mainLooper)
                .postDelayed({
                    finish()
                }, 300)
        }
    }
}

interface BottomSheetEvents {
    fun onSheetClose(finishActivity:Boolean=true)
}