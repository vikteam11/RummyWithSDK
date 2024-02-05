package com.rummytitans.playcashrummyonline.cardgame.ui.onboarding


import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.api.APIInterface
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.models.BaseModel

import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.ui.BaseViewModel
import com.rummytitans.playcashrummyonline.cardgame.utils.AnalyticsEventsKeys
import com.rummytitans.playcashrummyonline.cardgame.utils.ConnectionDetector
import android.widget.CompoundButton
import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.appsflyer.AppsFlyerLib
import com.google.gson.Gson
import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.models.BlockUserModel
import dagger.hilt.android.lifecycle.HiltViewModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    val apiInterface: APIInterface, val prefs: SharedPreferenceStorage,
    val gson: Gson, val connectionDetector: ConnectionDetector, val analyticsHelper: AnalyticsHelper
) : BaseViewModel<OnBoardNavigator>(connectionDetector) {

    val referCode = ObservableBoolean(false)
    val userReferCode = ObservableField("")
    val confirmByUser = ObservableBoolean(false)

    var loginModel: LoginResponse? = null
    var isPlayNowUser = false
    var regularColor = prefs.regularColor
    var safeColor = prefs.safeColor
    val selectedColor = ObservableField(safeColor)
    var mEmail = ""
    var mPassword = ""
    var advertisingId = ""
    var appsFlyerId = ""
    val isTrueCallerInstalled = ObservableBoolean(false)
    private val emptyJson = JSONObject()

    fun onConfirmByUser(button: CompoundButton?, check: Boolean) {
        confirmByUser.set(check)
    }

    fun loginWithTrueCaller(authCode:String, verificationCode:String) {
        if (isParentLoading.get()) return
        analyticsHelper.fireEvent(
            AnalyticsKey.Names.ButtonClick, bundleOf(
                AnalyticsKey.Keys.ButtonName to AnalyticsKey.Values.LoginWithTrueCaller,
                AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.LOGIN
            )
        )
        compositeDisposable.add(
            apiInterface.loginTrueCaller(
                authCode,
                verificationCode,
                userReferCode.get().toString(),
                prefs.campaignId.toString(),
                prefs.androidId.toString(),
                prefs.firebaseToken.toString(),
                advertisingId,
                appsFlyerId
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.Status) {
                        prefs.loginType = "TrueCaller"
                        loginModel =
                            gson.fromJson(gson.toJson(it.Response), LoginResponse::class.java)
                        fireEvent(loginModel, it.IsRegister,appsFlyerId)
                        verificationCheckup(it)
                    } else {
                        if (it.IsSuspended) {
                            val blockUserModel = gson.fromJson(
                                gson.toJson(it.Response),
                                BlockUserModel::class.java
                            )
                            blockUserModel?.let { blockUser ->
                                //avigatorAct.userRestricted(blockUser)
                                navigator.showError(it.Message)
                            } ?: kotlin.run {
                                navigator.showError(it.Message)
                            }
                        } else {
                            navigator.showError(it.Message)
                        }
                    }

                }, {
                    analyticsHelper.fireEvent(
                        AnalyticsKey.Names.TCLoginFailed, bundleOf(AnalyticsKey.Keys.ApiFailedMessage to it.message.toString()))
                    isParentLoading.set(false)
                    navigator.showError(it.message)
                })
        )
    }

    private fun verificationCheckup(response: BaseModel<Any>) {
        navigator.showMessage(response.Message)
        loginModel = gson.fromJson(gson.toJson(response.Response), LoginResponse::class.java)
        if (loginModel?.IsFairPlay == true)
            navigatorAct.showFairplayVoilationDialog(loginModel?.FairPlayMessage ?: "")
        else loginResponseCheckup()
    }

    fun loginResponseCheckup() {
        loginModel?.apply {
            navigatorAct.dismissFairplayVoilationDialog()
            prefs.loginCompleted = true
            prefs.avtarId = -1
            prefs.introductionCompleted = true
            if (BuildConfig.isPlayStoreApk==1){
                //save userState received from SplashScreen Api in case of playStore Apk
                //update in login model
                this.gameState=""//gson.fromJson(prefs.loginResponse, LoginResponse::class.java).gameState?:""
            }
            prefs.loginResponse = gson.toJson(loginModel)
            navigatorAct.loginSuccess()
            logoutStatus(apiInterface, loginModel?.UserId ?: 0, prefs.androidId ?: "", "1")
        }
    }

    private fun fireEvent(loginModel: LoginResponse?, isRegister: Boolean, campainID:String) {
        analyticsHelper.setUserID("" + loginModel?.UserId)

        if (isRegister) {
            analyticsHelper.fireEvent(
                AnalyticsKey.Names.UserRegister, bundleOf(
                    AnalyticsKey.Keys.LoginType to AnalyticsKey.Values.TypeTrueCaller,
                    AnalyticsKey.Keys.ReferCode to userReferCode.get(),
                    AnalyticsKey.Keys.MobileNo to loginModel?.Mobile,
                    AnalyticsKey.Keys.UserID to loginModel?.UserId,
                    AnalyticsKey.Keys.IPAddress to prefs.androidId.toString(),
                    AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.LOGIN,
                    AnalyticsKey.Keys.CampaignID to campainID
                )
            )
            prefs.isUserProperySet = false
        } else {
            analyticsHelper.fireEvent(
                AnalyticsKey.Names.UserLogin, bundleOf(
                    AnalyticsKey.Keys.LoginType to AnalyticsKey.Values.TypeTrueCaller,
                    AnalyticsKey.Keys.ReferCode to userReferCode.get(),
                    AnalyticsKey.Keys.MobileNo to loginModel?.Mobile,
                    AnalyticsKey.Keys.UserID to loginModel?.UserId,
                    AnalyticsKey.Keys.UserName to loginModel?.Name,
                    AnalyticsKey.Keys.Email to loginModel?.Email,
                    AnalyticsKey.Keys.IPAddress to prefs.androidId.toString(),
                    AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.LOGIN
                )
            )
            analyticsHelper.fireAttributesEvent(
                AnalyticsEventsKeys.LOGIN_DONE,
                loginModel?.UserId.toString()
            )
        }
        AppsFlyerLib.getInstance().setCustomerUserId(loginModel?.UserId.toString())
        /*val attributeEvent =
            if (isRegister) AnalyticsEventsKeys.ACCOUNT_CREATE else
                AnalyticsEventsKeys.LOGIN_DONE*/


        analyticsHelper.setJsonUserProperty(emptyJson.apply {
            put(AnalyticsKey.Properties.LoginType, AnalyticsKey.Values.TypeTrueCaller)
            put(AnalyticsKey.Properties.Mobile, loginModel?.Mobile)
            put(AnalyticsKey.Properties.Email, loginModel?.Email)
            put(AnalyticsKey.Properties.UserID, loginModel?.UserId)
            put(AnalyticsKey.Properties.FullName, loginModel?.Name)
            put(AnalyticsKey.Properties.DeviceID, prefs.androidId.toString())
            put(AnalyticsKey.Properties.Token, prefs.firebaseToken.toString())
            loginModel?.Name?.split(" ")?.let {list->
                if (list.isEmpty()) return@let
                list.elementAtOrNull(0)?.let { fName ->
                    put(AnalyticsKey.Properties.FirstName, fName)
                }
                list.elementAtOrNull(1)?.let { lName ->
                    put(AnalyticsKey.Properties.LastName, lName)
                }
            }
        })
    }


}