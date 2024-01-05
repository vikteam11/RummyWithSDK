package com.rummytitans.playcashrummyonline.cardgame.ui.profile.info

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseNavigator
import com.rummytitans.playcashrummyonline.cardgame.ui.newlogin.NewLoginActivity

import com.rummytitans.playcashrummyonline.cardgame.ui.profile.info.viewmodel.ChangePasswordViewModel
import com.rummytitans.playcashrummyonline.cardgame.utils.passwordPolicy
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.rummytitans.playcashrummyonline.cardgame.databinding.FragmentChangePasswordBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_change_password.*
import javax.inject.Inject

class FragmentChangePassword : BaseFragment(), MainNavigationFragment,
    BaseNavigator {

    lateinit var binding: FragmentChangePasswordBinding
    lateinit var viewModel: ChangePasswordViewModel

    companion object {
        fun newInstance() = FragmentChangePassword()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setLanguage()
        setTheme(inflater)
        viewModel = ViewModelProvider(
            this
        ).get(ChangePasswordViewModel::class.java)
        binding = FragmentChangePasswordBinding.inflate(localInflater ?: inflater, container, false)
            .apply {
                lifecycleOwner = this@FragmentChangePassword
               // viewmodel = this@FragmentChangePassword.viewModel
            }

        viewModel.myDialog = MyDialog(requireActivity()!!)

        binding.editOldPassword.addTextChangedListener {
            if (it.toString().isNotEmpty())
                binding.inputOldPassword.error = null
        }
        binding.editNewPassword.addTextChangedListener {
            if (it.toString().isNotEmpty())
                binding.inputNewPassword.error = null
        }
        binding.editConfirmPassword.addTextChangedListener {
            if (it.toString().isNotEmpty())
                binding.inputConfirmPassword.error = null
        }
        binding.executePendingBindings()
        return binding.root
    }

    override fun onResume() {
        context?.let {
            if (TextUtils.isEmpty(com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper.getLanguage(it))) {
                com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper.setLocale(it, getString(R.string.english_code), getString(R.string.english))
            } else {
                com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper.onAttach(it)
            }
        }
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigator = this
        icBack.setOnClickListener { activity?.onBackPressed() }


        btnUpdatePassword.setOnClickListener {
            if (validatePassword()){
                viewModel.updatePassword(
                        editOldPassword.text.toString(),
                        editNewPassword.text.toString(),
                        editConfirmPassword.text.toString()
                )
            }

        }
    }

    private fun validatePassword(): Boolean {
        when {
            TextUtils.isEmpty(editOldPassword.text.toString()) -> {
                inputOldPassword.error = getStringResource(R.string.enter_old_pasword)
                return false
            }
            TextUtils.isEmpty(editNewPassword.text.toString()) -> {
                inputNewPassword.error = getStringResource(R.string.enter_new_pasword)
                return false
            }
            editNewPassword.text.toString().length < 5 -> {
                inputNewPassword.error = getStringResource(R.string.password_length_error)
                return false
            }
            TextUtils.isEmpty(editConfirmPassword.text.toString()) -> {
                inputConfirmPassword.error = getStringResource(R.string.reenter_new_pasword)
                return false
            }
            editConfirmPassword.text.toString().length < 5 -> {
                inputConfirmPassword.error = getStringResource(R.string.password_length_error)
                return false
            }
            editNewPassword.text.toString() != editConfirmPassword.text.toString() -> {
                inputConfirmPassword.error = getStringResource(R.string.password_not_matched)
                return false
            }

            (editOldPassword.text.toString() == editNewPassword.text.toString()) || (editOldPassword.text.toString() == editConfirmPassword.text.toString()) -> {
                inputConfirmPassword.error = getStringResource(R.string.password_should_be_diffrent_with_old_password)
                return false
            }
            !passwordPolicy(editNewPassword.text.toString()) -> {
                inputNewPassword.error = getStringResource(R.string.err_invalid_password)
                return false
            }
            else -> return true
        }
    }


    override fun goBack() {
        activity?.onBackPressed()
    }


    override fun handleError(throwable: Throwable?) {
    }

    override fun showMessage(message: String?) {
        if (TextUtils.isEmpty(message)) return
        binding.fadingSnackbar.show(messageText = message, longDuration = false, isError = false)

    }

    override fun showError(message: String?) {
        if (TextUtils.isEmpty(message)) return
        binding.fadingSnackbar.show(messageText = message, longDuration = true, isError = true)
    }

    override fun showError(message: Int) {
        if (message == 0) return
        binding.fadingSnackbar.show(longDuration = true, messageId = message, isError = true)
    }

    override fun signOutUser() {
        showError(R.string.err_session_expired)
        activity?.finishAffinity()
        startActivity(Intent(activity, NewLoginActivity::class.java))
    }

    override fun getStringResource(resourseId: Int) = getString(resourseId)

}