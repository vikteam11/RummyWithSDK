package com.rummytitans.playcashrummyonline.cardgame.ui.mobileverification

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityMobileVerificationsBinding
import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.ui.MainActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.INTENT_LOGIN_RESPONSE
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.MOBILE_STATUS_CANCELED
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.MOBILE_STATUS_NONE
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.MOBILE_STATUS_SELECT
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants.PHONE_PICKER_REQUEST
import com.rummytitans.playcashrummyonline.cardgame.utils.otp_read.OtpReceiver
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MobileVerificationActivity : BaseActivity(),
    com.rummytitans.playcashrummyonline.cardgame.ui.mobileverification.MobileNavigator, OtpReceiver.OTPReceiveListener {


    lateinit var binding: ActivityMobileVerificationsBinding
    lateinit var viewModel: MobileVerificationViewModel
    val REQUEST_CODE_LOGIN = 111
    val mOtpReceiver by lazy { OtpReceiver() }
    override fun onCreate(savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper.getLanguage(this)))
            com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper.setLocale(this)
        else com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper.onAttach(this)

        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)
            .get(MobileVerificationViewModel::class.java)
        viewModel.navigator = this
        viewModel.navigatorAct = this
        viewModel.txtSendOTP.set(getString(R.string.txt_send_otp))
        viewModel.txtTitle.set(getString(R.string.txt_verify_mobile_number))
        viewModel.myDialog = MyDialog(this)
        intent?.getStringExtra(MyConstants.VARIFY_fROM)?.let {
            viewModel.mobileVarifyFrom = it
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_verifications)
        setSnackbarView(binding.fadingSnackbar)
        binding.viewModel = viewModel
        if (null != intent.getSerializableExtra(INTENT_LOGIN_RESPONSE))
            viewModel.loginResponse =
                intent.getSerializableExtra(INTENT_LOGIN_RESPONSE) as LoginResponse


        if (intent.getBooleanExtra(MyConstants.INTENT_FORGOT_PASSWORD, false)) {

            viewModel.verificationType =
                MobileVerificationViewModel.VerificationType.FORGOT_PASSWORD
            viewModel.btnSignInVisibility.set(true)
            viewModel.txtDescription.set(getStringResource(R.string.verify_mobile_number))

            val text = SpannableString(getStringResource(R.string.txt_remember_password))
            text.setSpan(
                RelativeSizeSpan(1.1f),
                text.length - getStringResource(R.string.txt_sign_in).length,
                text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimaryDark)),
                text.length - getStringResource(R.string.txt_sign_in).length,
                text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            viewModel.txtSignInButton.set(text)
        } else {
            viewModel.verificationType =
                MobileVerificationViewModel.VerificationType.MOBILE_VERIFICATION
            viewModel.txtDescription.set(getStringResource(R.string.enter_the_mobile_number_to_associated_with_your_account_we_will_send_you_4_digit_otp_to_reset_your_password))

        }

        RxTextView.afterTextChangeEvents(binding.editSetPassword).skipInitialValue()
            .debounce(100, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewModel.checkPassword(it.editable().toString()) }

        requestForUserPhoneNumber()
        startSMSListener()

        binding.executePendingBindings()
    }

    override fun fireBranchEvent(eventName: String, userId: Int) {
        if (!this.isDestroyed) {
            viewModel.analyticsHelper.fireAttributesEvent(eventName, userId.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHONE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val credential = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                var mPhone = credential?.id ?: ""
                if (mPhone.length >= 10) {
                    mPhone = mPhone.substring(mPhone.length - 10)
                    onNumberReceived(mPhone, MOBILE_STATUS_SELECT)
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                onNumberReceived("", MOBILE_STATUS_CANCELED)
            } else {
                onNumberReceived("", MOBILE_STATUS_NONE)
            }
        } else if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mOtpReceiver)
    }

    override fun resetPassword() {
        viewModel.resetPassword(
            binding.editMobileNumber.text.toString(),
            binding.editSetPassword.text.toString(),
            binding.editConfirmSetPassword.text.toString(),
            binding.otpView.text.toString()
        )
    }

    override fun mobileVerified(teamVerified: Boolean) {

        val newlogin = viewModel.loginResponse
        newlogin?.Mobile = binding.editMobileNumber.text.toString()
        val s = Gson().toJson(newlogin)
        viewModel.prefs.loginResponse = s

        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()

    }

    override fun sendOtp() {
        viewModel.sendOtp(binding.editMobileNumber.text.toString())
    }

    override fun verifyOtp() {
        viewModel.verifyOTP(
            binding.editMobileNumber.text.toString(),
            binding.otpView.text.toString()
        )
    }

    private fun startSMSListener() {
        try {
            mOtpReceiver.setOTPListener(this)
            val intentFilter = IntentFilter()
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
            this.registerReceiver(mOtpReceiver, intentFilter)
            val client = SmsRetriever.getClient(this)
            client.startSmsRetriever()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun requestForUserPhoneNumber() {
        binding.editMobileNumber.isFocusableInTouchMode = false
        binding.editMobileNumber.setOnClickListener {
            if (!binding.editMobileNumber.isFocusableInTouchMode) {
                val hintRequest = HintRequest.Builder()
                    .setPhoneNumberIdentifierSupported(true)
                    .build()
                val credentialsClient = Credentials.getClient(this)
                val intent = credentialsClient.getHintPickerIntent(hintRequest)
                startIntentSenderForResult(
                    intent.intentSender, PHONE_PICKER_REQUEST,
                    null, 0, 0, 0
                )
            }
        }
    }

    override fun onOTPReceived(otp: String?) {
        binding.otpView.setText(otp.toString())
    }

    override fun onNumberReceived(number: String, status: String) {
        when (status) {
            MOBILE_STATUS_SELECT -> {
                binding.editMobileNumber.isFocusableInTouchMode = true
                binding.editMobileNumber.requestFocus()
                binding.editMobileNumber.setText(number)
                binding.editMobileNumber.setSelection(number.length)
            }
            MOBILE_STATUS_CANCELED -> {
                binding.editMobileNumber.isFocusableInTouchMode = true
                binding.editMobileNumber.requestFocus()
            }
            MOBILE_STATUS_NONE -> {
                binding.editMobileNumber.isFocusableInTouchMode = true
                binding.editMobileNumber.requestFocus()
            }
            else -> {
                binding.editMobileNumber.isFocusableInTouchMode = true
                binding.editMobileNumber.requestFocus()
            }
        }
    }

}