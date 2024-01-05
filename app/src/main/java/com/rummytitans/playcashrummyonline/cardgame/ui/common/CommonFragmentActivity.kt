package com.rummytitans.playcashrummyonline.cardgame.ui.common
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appsflyer.AppsFlyerLib
import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityCommonFragmentBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.support.FragmentSupport
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_common_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class CommonFragmentActivity : BaseActivity() {

    var popupSportType: PopupWindow? = null
    val RESPONSE_LANG_UPDATE = "RESPONSE_LANG_UPDATE"
    val RESPONSE_THEME_UPDATE = "RESPONSE_THEME_UPDATE"
    val RESPONSE_SETTING_UPDATE = 100


    @Inject
    lateinit var pref: SharedPreferenceStorage

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper


    lateinit var binding: ActivityCommonFragmentBinding
    lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppsFlyerLib.getInstance().sendPushNotificationData(this)

        viewModel = ViewModelProvider(
            this
        ).get(CommonViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_fragment)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        viewModel.navigator = this
        binding.executePendingBindings()

        icBack.setOnClickListener { onBackPressed() }

        setUpFragment(savedInstanceState)





        hideKeyboard()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(container?.windowToken, 0)
    }

    fun updateTheme(colorCode: String) {
        viewModel.selectedColor.set(colorCode)
        val selectedColor = Color.parseColor(colorCode)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = selectedColor
        }
    }

    fun setUpFragment(savedInstanceState: Bundle?) {

        intent?.getStringExtra(MyConstants.INTENT_PASS_COMMON_TYPE).let {

            if (null == it) {
                titile.text = "Help Desk"
                var from = intent?.getStringExtra(MyConstants.INTENT_PASS_FROM) ?: ""
                addFragment(FragmentSupport.newInstance(from))
                return@let
            }

            when (it) {
                "support" -> {
                    titile.text = "Help Desk"
                    val from = intent?.getStringExtra(MyConstants.INTENT_PASS_FROM) ?: ""
                    val requestDialog =
                        intent?.getStringExtra(MyConstants.INTENT_PASS_COMING_FROM) ?: ""
                    addFragment(FragmentSupport.newInstance(from, requestDialog))
                }


            }
        }
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fragment_container.id, fragment).commit()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val currfrag = supportFragmentManager.findFragmentById(fragment_container.id)
        currfrag?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
