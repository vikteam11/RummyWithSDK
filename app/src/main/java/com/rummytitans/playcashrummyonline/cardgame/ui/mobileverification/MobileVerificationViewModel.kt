package com.rummytitans.playcashrummyonline.cardgame.ui.mobileverification

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.api.APIInterface
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.ui.BaseViewModel
import com.rummytitans.playcashrummyonline.cardgame.utils.*
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import android.text.SpannableString
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
@HiltViewModel
class MobileVerificationViewModel @Inject constructor(
    val prefs: SharedPreferenceStorage,
    val connectionDetector: ConnectionDetector, val analyticsHelper: AnalyticsHelper
) : BaseViewModel<com.rummytitans.playcashrummyonline.cardgame.ui.mobileverification.MobileNavigator>() {

    var mShowEnterOtpView = ObservableBoolean(false)
    var enableSendOTP = ObservableBoolean(true)
    var isLoading = ObservableBoolean(false)
    var btnSignInVisibility = ObservableBoolean(false)
    var mShowChangePassword = ObservableBoolean(false)
    var loginResponse: LoginResponse? = null
    var txtSendOTP = ObservableField<String>("")
    var txtDescription = ObservableField<String>("")
    var txtTitle = ObservableField<String>("")
    var txtSignInButton = ObservableField<SpannableString>()
    var verificationType: VerificationType = VerificationType.FORGOT_PASSWORD
    var timer: Disposable? = null
    var myDialog: MyDialog? = null
    var reSendOtpCount = 0
    var mobileVarifyFrom = "SIGNUP"

    @Inject
    lateinit var apiInterface: APIInterface

    @Inject
    lateinit var gson: Gson


    fun sendOtp(mobileNumber: String) {

        if (!validMobile(mobileNumber)) {
            navigator.showError(R.string.err_invalid_mobile_number)
            return
        }

        if (!connectionDetector.isConnected) {
            myDialog?.noInternetDialog {
                sendOtp(mobileNumber)
            }
            isLoading.set(false)
            return
        }


        val api =
            if (verificationType == VerificationType.MOBILE_VERIFICATION) apiInterface.sendOTP(
                mobileNumber,
                loginResponse?.UserId ?: 0,
                loginResponse?.ExpireToken ?: "",
                loginResponse?.AuthExpire ?: ""
            ) else
                apiInterface.forgotPasswordSendOTP(mobileNumber, "asdf")
        enableSendOTP.set(false)
        isLoading.set(true)
        compositeDisposable.add(
            api
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isLoading.set(false)
                    if (it.Status) {
                        reSendOtpCount++
                        timerObser()
                        mShowEnterOtpView.set(true)
                        navigator.showMessage(it.Message)
                    } else {
                        enableSendOTP.set(true)
                        navigator.showError(it.Message)
                    }

                }, {
                    isLoading.set(false)
                    enableSendOTP.set(true)
                    txtSendOTP.set(navigator.getStringResource(R.string.txt_send_otp))

                })
        )

    }


    fun timerObser() {
        enableSendOTP.set(false)
        if (timer != null && !(timer?.isDisposed)!!) timer?.dispose()
        timer = MyConstants.countDown(1, 60)
            .subscribe(
                {
                    txtSendOTP.set(it.toString())
                }, {
                    navigator.handleError(it)
                    enableSendOTP.set(true)
                    txtSendOTP.set(navigator.getStringResource(R.string.txt_send_otp))

                }, {
                    enableSendOTP.set(true)
                    txtSendOTP.set(navigator.getStringResource(R.string.txt_send_otp))

                }, {

                }
            )

    }


    fun verifyOTP(mobileNumber: String, otp: String) {
        if (!validMobile(mobileNumber)) {
            navigator.showError(R.string.err_invalid_mobile_number)
            return
        } else if (!validOTP(otp)) {
            navigator.showError(R.string.err_invalid_otp)
            return
        }

        if (!connectionDetector.isConnected) {
            myDialog?.noInternetDialog {
                verifyOTP(mobileNumber, otp)
            }
            isLoading.set(false)
            return
        }

        isLoading.set(true)
        val api =
            if (verificationType == VerificationType.MOBILE_VERIFICATION) apiInterface.verifyOTP(
                mobileNumber,
                otp,
                loginResponse?.UserId ?: 0,
                loginResponse?.ExpireToken ?: "",
                loginResponse?.AuthExpire ?: ""
            ) else apiInterface.forgotPasswordVerifyOTP(mobileNumber, otp, "asdf")

        compositeDisposable.add(
            api.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.Status) {
                        onMobileVarify(mobileNumber)
                        enableSendOTP.set(true)
                        if (verificationType == VerificationType.MOBILE_VERIFICATION) {
                            loginResponse?.MobileVerify = true
                            if (loginResponse?.IsFirstTime == true) {
                                prefs.loginCompleted = true
                                prefs.loginResponse = gson.toJson(loginResponse)
                                logoutStatus(
                                    apiInterface,
                                    loginResponse?.UserId ?: 0,
                                    prefs.androidId ?: "",
                                    "1"
                                )

                            }
                            navigatorAct.mobileVerified(loginResponse?.IsFirstTime!!)
                        } else {
                            verificationType = VerificationType.RESET_PASSWORD
                            if (!timer?.isDisposed!!)
                                timer?.dispose()
                            mShowChangePassword.set(true)
                            txtDescription.set(navigator.getStringResource(R.string.txt_successfully_verified))
                            txtSendOTP.set(navigator.getStringResource(R.string.txt_create_password))
                            txtTitle.set(navigator.getStringResource(R.string.reset_password))
                            mShowEnterOtpView.set(false)
                        }
                        navigator.showMessage(it.Message)
                    } else {
                        navigator.showError(it.Message)
                    }
                    isLoading.set(false)

                }, {
                    isLoading.set(false)
                    navigator.handleError(it)
                })
        )
    }

    fun onMobileVarify(mobileNumber: String) {

        val eventName = when (mobileVarifyFrom) {
            MyConstants.LOGIN -> AnalyticsEventsKeys.LGN_MOBILE_VARIFICATION_DONE
            MyConstants.FORGET_PASSWORD -> AnalyticsEventsKeys.FRG_MOBILE_VARIFICATION_DONE
            else -> AnalyticsEventsKeys.SNP_MOBILE_VARIFICATION_DONE
        }

        if (eventName != AnalyticsEventsKeys.FRG_MOBILE_VARIFICATION_DONE)
            navigatorAct.fireBranchEvent(eventName, loginResponse?.UserId ?: 0)
    }

    fun resetPassword(
        mobileNumber: String,
        password: String,
        confirmpassword: String,
        otp: String
    ) {
        if (!validMobile(mobileNumber)) {
            navigator.showError(R.string.err_invalid_mobile_number)
            return
        } else if (!validOTP(otp)) {
            navigator.showError(R.string.err_invalid_otp)
            return
        } else if (!passwordPolicy(password)) {
            navigator.showError(R.string.err_invalid_password)
            return
        } else if (password != confirmpassword) {
            navigator.showError(R.string.password_not_matched)
            return
        }

        if (!connectionDetector.isConnected) {
            myDialog?.noInternetDialog {
                resetPassword(mobileNumber, password, confirmpassword, otp)
            }
            isLoading.set(false)
            return
        }

        isLoading.set(true)
        enableSendOTP.set(false)
        compositeDisposable.add(
            apiInterface.resetPassword(mobileNumber, otp, "asdfasdf", password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isLoading.set(false)
                    enableSendOTP.set(true)

                    navigator.showMessage(it.Message)
                    if (it.Status) {
                        navigator.goBack()
                    }
                }, {
                    navigator.handleError(it)
                    isLoading.set(false)
                    enableSendOTP.set(true)

                })
        )


    }

/*

    fun getProfile() {
        isSwipeLoading.set(true)

        compositeDisposable.add(
            apiInterface.getProfile(
                loginResponse.UserId,
                loginResponse.ExpireToken,
                loginResponse.AuthExpire
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    isSwipeLoading.set(false)

                }, {
                    isSwipeLoading.set(false)

                })
        )
    }
*/

    val isPsdDigitDone = ObservableBoolean(false)
    val isPsdAlphabateDone = ObservableBoolean(false)
    val isPsdSpecialDone = ObservableBoolean(false)
    val isPsdLengthDone = ObservableBoolean(false)
    val isPasswordValid = ObservableBoolean(false)
    val isValidEmail = ObservableBoolean(false)
    fun checkPassword(password: String) {
        if (passwordPolicy(password)) {
            isPasswordValid.set(true)
            isPsdDigitDone.set(true)
            isPsdAlphabateDone.set(true)
            isPsdSpecialDone.set(true)
            isPsdLengthDone.set(true)
        } else {
            isPasswordValid.set(false)
            isPsdDigitDone.set(passwordDigit(password))
            isPsdAlphabateDone.set(passwordAlphabate(password))
            isPsdSpecialDone.set(passwordSpecialSymbol(password))
            isPsdLengthDone.set(passwordLength(password))
        }
    }


    fun sendOTP() {
        if (verificationType == VerificationType.RESET_PASSWORD) {
            navigatorAct.resetPassword()
            return
        }
        navigatorAct.sendOtp()
    }

    fun verifyOtp() {
        navigatorAct.verifyOtp()
    }

    fun goBack() {
        navigator.goBack()
    }

    companion object {
        const val TEXT_SEND_OTP = "Send OTP"
    }

    enum class VerificationType {
        FORGOT_PASSWORD, MOBILE_VERIFICATION, RESET_PASSWORD
    }

}