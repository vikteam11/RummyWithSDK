package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.inappupdate

import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.models.VersionModel
import com.rummytitans.playcashrummyonline.cardgame.utils.bottomsheets.BottomSheetAlertDialog
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.gson.Gson
import com.rummytitans.playcashrummyonline.cardgame.utils.alertDialog.AlertdialogModel
import com.rummytitans.sdk.cardgame.RummyTitanSDK
import java.lang.ref.WeakReference


class InAppUpdateHelper (
    val activity:Activity,
    val analyticsHelper: AnalyticsHelper,
    val prefs: SharedPreferenceStorage,
    val gson: Gson) {

    class InAppUpdateModel(val appUpdateInfo: AppUpdateInfo, val updateType:Int, val requestCode:Int)
    var inAppUpdateModel: InAppUpdateModel?=null
    var isInAppUpdateAvailable=false
    var appUpdateManager: AppUpdateManager?=null
    companion object{
        const val REQUEST_FLEXIBLE_UPDATE=1232
        const val REQUEST_FORCE_UPDATE=1122
    }
    var installStateUpdatedListener: InstallStateUpdatedListener?=null
    var loginModel: LoginResponse =
        gson.fromJson(prefs.splashResponse, LoginResponse::class.java)
    var versionResp: VersionModel =
        gson.fromJson(prefs.splashResponse, VersionModel::class.java)

    fun startInAppUpdateIntent(){
        inAppUpdateModel?.let {
            prefs.isInAppAvailable=false
            analyticsHelper.fireEvent(AnalyticsKey.Names.InAppUpdateStart)
            appUpdateManager?.startUpdateFlowForResult(it.appUpdateInfo,it.updateType,activity,it.requestCode)
        }
    }

    fun activityOnResume(){
        Log.e("appUpdate", "resume")
        val isUpdateNeed=versionResp.IsAppUpdate && versionResp.playStoreApkUpdateFrom==versionResp.UPDATE_FROM_IN_APP_UPDATE
        if (BuildConfig.isPlayStoreApk==1 && isUpdateNeed){
            Log.e("appUpdate", "resume request")
            appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
                Log.e("appUpdate", "resume Update flow request ${appUpdateInfo.updateAvailability()}")
                if (appUpdateInfo.installStatus()==InstallStatus.DOWNLOADED) {
                    Log.e("appUpdate", "resume downloaded")
                    onDownloadComplete()
                    return@addOnSuccessListener
                }
                when(appUpdateInfo.updateAvailability()){
                    UpdateAvailability.UPDATE_AVAILABLE,
                    UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS->{

                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS){
                            val (updateType,requestCode)=if (versionResp.ForceUpdate)
                                Pair(AppUpdateType.IMMEDIATE,REQUEST_FORCE_UPDATE)
                            else
                                Pair(AppUpdateType.FLEXIBLE,REQUEST_FLEXIBLE_UPDATE)

                            analyticsHelper.fireEvent(AnalyticsKey.Names.InAppUpdateDataInProgress)
                            Log.e("appUpdate", "resume in progress start intent")
                            inAppUpdateModel=
                                InAppUpdateModel(appUpdateInfo, updateType, requestCode)
                            startInAppUpdateIntent()
                            installStateUpdatedListener?.let {
                                appUpdateManager?.registerListener(it)
                            }
                        }

                    }
                    else-> {
                        Log.e("appUpdate", "resume response else ${appUpdateInfo.installStatus()}")
                        if (appUpdateInfo.installStatus()==InstallStatus.DOWNLOADED)
                            onDownloadComplete()
                        else
                            return@addOnSuccessListener
                    }

                }
            }
        }
    }

    fun requestInAppUpdateAvailability(onUpdateAvailable:(appUpdateInfo:AppUpdateInfo?)->Unit={}){
        analyticsHelper.fireEvent(AnalyticsKey.Names.InAppUpdateRequest)
        Log.e("appUpdate", "main flow request ")
        appUpdateManager = AppUpdateManagerFactory.create(activity)
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo
        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            Log.e("appUpdate", "main flow request receive ${appUpdateInfo.updateAvailability()}")

            val (updateType,requestCode)=if (versionResp.ForceUpdate)
                Pair(AppUpdateType.IMMEDIATE,REQUEST_FORCE_UPDATE)
            else
                Pair(AppUpdateType.FLEXIBLE,REQUEST_FLEXIBLE_UPDATE)

            when(appUpdateInfo.updateAvailability()){
                UpdateAvailability.UPDATE_AVAILABLE->{
                    analyticsHelper.fireEvent(
                        AnalyticsKey.Names.InAppUpdateData, bundleOf(
                            AnalyticsKey.Keys.IsUpdateAvailable to appUpdateInfo.updateAvailability(),
                            AnalyticsKey.Keys.UpdateType to updateType,
                            AnalyticsKey.Keys.CurrentVersion to BuildConfig.VERSION_CODE,
                            AnalyticsKey.Keys.NewVersionCode to appUpdateInfo.availableVersionCode(),
                            AnalyticsKey.Keys.UserID to loginModel.UserId)
                    )
                    prefs.isInAppAvailable=true

                }
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS->{
                    analyticsHelper.fireEvent(AnalyticsKey.Names.InAppUpdateDataInProgress)
                    prefs.isInAppAvailable=true
                }
                else-> {
                    analyticsHelper.fireEvent(AnalyticsKey.Names.UpdateNotAvailableYet,
                        bundleOf(AnalyticsKey.Keys.CurrentVersion to BuildConfig.VERSION_CODE))
                    onUpdateAvailable(null)
                    return@addOnSuccessListener
                }
            }.let {
                Log.e("appUpdate", "main in progress start intent")
                inAppUpdateModel= InAppUpdateModel(appUpdateInfo, updateType, requestCode)
                isInAppUpdateAvailable=appUpdateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE && inAppUpdateModel!=null
                onUpdateAvailable(appUpdateInfo)
                //onDownloadComplete()
            }
        }

        installStateUpdatedListener=  InstallStateUpdatedListener {
            Log.e("appUpdate", "lisnter ${it.installStatus()} error ${it.installErrorCode()}")
            when {
                it.installStatus() == InstallStatus.DOWNLOADED -> {
                    prefs.isInAppAvailable=false
                    onDownloadComplete()
                }
                it.installStatus() == InstallStatus.INSTALLED ->
                    removeInstallStateUpdateListener()
            }
        }
        appUpdateInfoTask?.addOnFailureListener {
            Log.e("appUpdate", "failed lisnter ${it.message} error ${it.cause?.message}")
        }
        installStateUpdatedListener?.let {
            appUpdateManager?.registerListener(it)
        }
    }

    private fun onDownloadComplete() {
        val alertModel = AlertdialogModel(
            title = "Your Download Is Ready!",
            description = "New update successfully downloaded. Tap below to complete the installation",
            positiveText = "INSTALL NOW",
            imgRes = R.drawable.ic_dwonload_complete,
            onPositiveClick = {
                appUpdateManager?.completeUpdate()
            }
        )
        val activityRef= WeakReference(activity)
        activityRef.get()?.let {act->
            BottomSheetAlertDialog(
                act,
                alertdialogModel = alertModel,
                colorCode = if(prefs.onSafePlay)prefs.safeColor?:"" else prefs.regularColor?:""
            ).apply {
                setCancelable(false)
                show()
            }
        }

    }

    private fun removeInstallStateUpdateListener() {
        Log.e("appUpdate", "removeInstallStateUpdateListener $activity")

        installStateUpdatedListener?.let {
            appUpdateManager?.unregisterListener(it)
        }
    }

    fun activityOnResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!isInAppUpdateAvailable)return
        prefs.isInAppAvailable=false
        if (requestCode== REQUEST_FLEXIBLE_UPDATE || requestCode== REQUEST_FORCE_UPDATE){
            Log.e("appUpdate", "Update flow request code $requestCode  Result code $resultCode")
            analyticsHelper.fireEvent(
                AnalyticsKey.Names.InAppUpdateResult, bundleOf(
                    AnalyticsKey.Keys.UpdateStatus to if (resultCode== Activity.RESULT_OK)1 else 0,
                    AnalyticsKey.Keys.UserID to loginModel.UserId
                )
            )
            if (resultCode == Activity.RESULT_OK) {
                prefs.isInAppAvailable=false
                Log.e("appUpdate", "success install ")
            }else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("appUpdate", "failed update ")
                if(requestCode == REQUEST_FORCE_UPDATE) activity.finishAffinity()
            }
        }
    }

    fun activityOnStop(){
        removeInstallStateUpdateListener()
    }

}