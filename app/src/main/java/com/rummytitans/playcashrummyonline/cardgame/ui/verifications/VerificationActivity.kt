package com.rummytitans.playcashrummyonline.cardgame.ui.verifications

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityVerificationBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper
import com.rummytitans.playcashrummyonline.cardgame.utils.inTransaction
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rummytitans.playcashrummyonline.cardgame.ui.profile.info.MainNavigationFragment
import kotlinx.android.synthetic.main.activity_verification.*
import javax.inject.Inject


class VerificationActivity : BaseActivity() {

    @Inject
    lateinit var prefs: SharedPreferenceStorage
    val FRAGMENT_ID = R.id.fragment_container
    lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification)
        icBack.setOnClickListener { onBackPressed() }

        intent.getStringExtra("for").let {
            if (it == "pan") {
                // TODO : handel fragmentpanverifcation
                // replaceFragment(FragmentPanVerification.newInstance())
                txtTitle.text = getString(R.string.act_verification_pan_card_verification)
            } else {
                replaceFragment(FragmentBankVerification.newInstance())
                txtTitle.text = getString(R.string.act_verification_bank_verification)
            }
        }
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment, F : MainNavigationFragment {
        supportFragmentManager.inTransaction { replace(FRAGMENT_ID, fragment) }
    }

}
