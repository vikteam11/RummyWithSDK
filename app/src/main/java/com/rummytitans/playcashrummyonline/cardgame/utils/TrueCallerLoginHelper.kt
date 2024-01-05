package com.rummytitans.playcashrummyonline.cardgame.utils

import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.os.bundleOf
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.truecaller.android.sdk.oAuth.*
import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

class TrueCallerLoginHelper(val context: BaseActivity,
                            val preferenceStorage: SharedPreferenceStorage,
                            val analyticHelper: AnalyticsHelper,
                            val onTcSuccess:(authCode:String,verifyCode:String)->Unit,
                            val onTcFailed:(tcOAuthError: TcOAuthError?)->Unit,
                            val showError:(tcOAuthError: String)->Unit) {

    var tcSdk:TcSdk?=null
    var codeVerifier:String=""
    var stateRequested=""
    var authCode=""

    fun initializeTrueCaller(){
         val trueCallerCallback=object: TcOAuthCallback{
             override fun onFailure(tcOAuthError: TcOAuthError) {
                 analyticHelper.fireEvent(
                     AnalyticsKey.Names.TrueCallerSDKError, bundleOf(
                         AnalyticsKey.Keys.TCSdkErrorCode to tcOAuthError.errorCode
                     )
                 )
                 onTcFailed(tcOAuthError)
             }

             override fun onSuccess(tcOAuthData: TcOAuthData) {
                 val stateReceived = tcOAuthData.state
                 if (stateReceived == stateRequested) {
                     authCode=tcOAuthData.authorizationCode
                     onTcSuccess(authCode,codeVerifier)
                 } else {
                     print ("verification failed")
                 }
                 Log.e("truecallerCode","auth $authCode")
                 Log.e("truecallerCode","$codeVerifier")
             }
         }

         val tcSdkOptions = TcSdkOptions.Builder(context, trueCallerCallback)
             .buttonColor(Color.parseColor(preferenceStorage.safeColor))
             .loginTextPrefix(TcSdkOptions.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
             .ctaText(TcSdkOptions.CTA_TEXT_CONTINUE)
             .buttonShapeOptions(TcSdkOptions.BUTTON_SHAPE_ROUNDED)
             .footerType(TcSdkOptions.FOOTER_TYPE_ANOTHER_METHOD)
             .consentTitleOption(TcSdkOptions.SDK_CONSENT_TITLE_LOG_IN)
             .buttonTextColor(Color.WHITE)
             .build()
         TcSdk.init(tcSdkOptions)
        tcSdk= TcSdk.getInstance()
         val sdkStatus=if (isAvailable()){
             tcSdk?.setLocale(Locale(LocaleHelper.getLanguage(context)))
             "1"
         }else
             "0"

        analyticHelper.fireEvent(
            AnalyticsKey.Names.IsTCSdkPresent, bundleOf(
                AnalyticsKey.Keys.TCSdkState to sdkStatus
            )
        )
     }

    fun launchTrueCaller():Boolean{
        kotlin.runCatching {
            CodeVerifierUtil.generateRandomCodeVerifier().let {
                codeVerifier=it
                val codeChallenge=CodeVerifierUtil.getCodeChallenge(it)?:""
                tcSdk?.setCodeChallenge(codeChallenge)
            }
            BigInteger(130, SecureRandom()).toString(32).let {
                stateRequested=it
                tcSdk?.setOAuthState(stateRequested)
            }
            tcSdk?.setOAuthScopes(arrayOf("profile","openid","phone"))
            tcSdk?.getAuthorizationCode(context)
        }.onFailure {
            analyticHelper.fireEvent(
                AnalyticsKey.Names.IsTCDialogueOpen, bundleOf(
                    AnalyticsKey.Keys.TCDialogState to "0"
                )
            )
            onTcFailed(null)
        }.onSuccess {
            analyticHelper.fireEvent(
                AnalyticsKey.Names.IsTCDialogueOpen, bundleOf(
                    AnalyticsKey.Keys.TCDialogState to "1"
                )
            )
        }
        return true
    }

    fun isAvailable():Boolean{
        return tcSdk?.isOAuthFlowUsable?:false
    }

     fun onTrueCallerResultReceived(requestCode:Int,resultCode: Int, data: Intent?) {
        if (requestCode == TcSdk.SHARE_PROFILE_REQUEST_CODE) {
            tcSdk?.onActivityResultObtained(context, requestCode, resultCode, data)
        }
    }

}

/*val trueScope = TruecallerSdkScope.Builder(this, sdkCallback)
             .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
             .buttonColor(ContextCompat.getColor(this, R.color.colorPrimary))
             .buttonTextColor(Color.WHITE)
             .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
             .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
             .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
             .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
             .privacyPolicyUrl(WebViewUrls.WebPrivacyPolicy)
             .termsOfServiceUrl(WebViewUrls.Terms_And_Conditions)
             .footerType(TruecallerSdkScope.FOOTER_TYPE_ANOTHER_METHOD)
             .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
             .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
             .build()
         TruecallerSDK.init(trueScope)
         analyticsHelper.fireEvent(
             AnalyticsKey.Names.IsTCSdkPresent, bundleOf(
                 AnalyticsKey.Keys.TCSdkState to if (TruecallerSDK.getInstance().isUsable)"1" else "0"
             )
         )
         if (TruecallerSDK.getInstance().isUsable) {
             TruecallerSDK.getInstance().setLocale(Locale(LocaleHelper.getLanguage(this)))
         }
         mViewModel.isTrueCallerInstalled.set(TruecallerSDK.getInstance().isUsable)*/


/*  private val sdkCallback = object : ITrueCallback {
      override fun onSuccessProfileShared(trueProfile: TrueProfile) {
          mViewModel.loginWithTrueCaller(trueProfile)
      }

      override fun onFailureProfileShared(trueError: TrueError) {
          analyticsHelper.fireEvent(
              AnalyticsKey.Names.TrueCallerSDKError, bundleOf(
                  AnalyticsKey.Keys.TCSdkErrorCode to trueError.errorType
              )
          )
          when (trueError.errorType) {
              TrueError.ERROR_TYPE_NETWORK -> {
                  if (!mViewModel.connectionDetector.isConnected) {
                      try {
                          mViewModel.myParentDialog?.noInternetDialog {
                              launchTrueCaller()
                          }
                      } catch (e: Exception) {
                      }
                  }
              }
              TrueError.ERROR_TYPE_USER_DENIED -> {//user BackPressed
              }
              else ->
                  redirectToLogin()
          }
//            else showError(R.string.something_went_wrong)
      }

       override fun onVerificationRequired(trueError: TrueError) {
           showError(trueError.toString())
       }
   }*/
