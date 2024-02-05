package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate

import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.models.VersionModel
import com.rummytitans.playcashrummyonline.cardgame.ui.BaseViewModel
import com.rummytitans.playcashrummyonline.cardgame.ui.launcher.LaunchDestination
import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class AppUpdateViewModel @Inject constructor(val prefs: SharedPreferenceStorage, val gson: Gson,val analyticsHelper: AnalyticsHelper) :
    BaseViewModel<com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseNavigator>() {
    @JvmField
    var launchDestination: LaunchDestination
    var loginModel: LoginResponse

    val selectedColor =
        ObservableField(if (prefs.onSafePlay) prefs.safeColor else prefs.regularColor)
    var versionResp: VersionModel =
        gson.fromJson(prefs.splashResponse, VersionModel::class.java)

    val _bottomSheetStateEvent = MutableLiveData(BottomSheetBehavior.STATE_EXPANDED)
    val bottomSheetStateEvent: LiveData<Int>
        get() = _bottomSheetStateEvent

    init {
        launchDestination = getLaunchDestination()
        loginModel = if(!TextUtils.isEmpty(prefs.loginResponse)){
            gson.fromJson(prefs.loginResponse, LoginResponse::class.java)
        }else{
            LoginResponse().apply {
                UserId = 0
                AuthExpire = "0"
                ExpireToken = "0"
            }
        }
    }

    fun getLaunchDestination(): LaunchDestination {
        return if (!prefs.introductionCompleted) {
            LaunchDestination.ONBOARDING
        } else if (prefs.introductionCompleted && !prefs.loginCompleted) {
            LaunchDestination.LOGIN_ACTIVITY
        } else LaunchDestination.MAIN_ACTIVITY
    }
}