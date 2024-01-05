package com.rummytitans.playcashrummyonline.cardgame.ui.onboarding

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.appsflyer.AppsFlyerLib
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityLandingBinding
import com.rummytitans.playcashrummyonline.cardgame.models.BlockUserModel
import com.rummytitans.playcashrummyonline.cardgame.models.CarouselModel
import com.rummytitans.playcashrummyonline.cardgame.models.VersionModel
import com.rummytitans.playcashrummyonline.cardgame.ui.MainActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.CarouselPagerAdapter
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.FragmentCarousel
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.blockuser.BlockUserActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.newlogin.NewLoginActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.*
import com.rummytitans.playcashrummyonline.cardgame.widget.CustomViewPagerChangeListener
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import com.rummytitans.playcashrummyonline.cardgame.widget.ViewPagerAutoScroller
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.rummytitans.playcashrummyonline.cardgame.R
class OnBoardingActivity : BaseActivity(), OnBoardNavigator {

    lateinit var binding: ActivityLandingBinding

    lateinit var mViewModel: OnBoardingViewModel

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper
    var fairPlayViolationDialog: Dialog? = null
    private var mViewPagerAutoScroller: ViewPagerAutoScroller?=null
    var trueCallerLoginHelper:TrueCallerLoginHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(LocaleHelper.getLanguage(this))) {
            LocaleHelper.setLocale(
                this, getString(R.string.english_code), getString(R.string.english)
            )
        } else LocaleHelper.onAttach(this)
        super.onCreate(savedInstanceState)
        window.transparentStatusBar()
        mViewModel = ViewModelProvider(this).get(OnBoardingViewModel::class.java)
        mViewModel.navigator = this
        mViewModel.navigatorAct = this
        mViewModel.myParentDialog = MyDialog(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_landing)
        binding.viewModel = mViewModel
        binding.lifecycleOwner = this
        initView()
        initTrueCaller()
        fetchAdvertisingId()

        binding.btnLogin.visibility = View.VISIBLE

        binding.btnLogin.setOnClickListenerDebounce {
            mViewModel.analyticsHelper.fireEvent(
                AnalyticsKey.Names.ButtonClick,
                bundleOf(
                    AnalyticsKey.Keys.ButtonName to AnalyticsKey.Keys.Register_Login,
                    AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.ONBOARDING
                )
            )
            val versionResponse: VersionModel =
                mViewModel.gson.fromJson(mViewModel.prefs.splashResponse, VersionModel::class.java)
            if(versionResponse.isTrueCallerEnable)
                launchTrueCaller()
            else
                redirectToLogin()
        }
        mViewModel.analyticsHelper.fireEvent(
            AnalyticsKey.Names.ScreenLoadDone, bundleOf(
                AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.ONBOARDING
            )
        )
        //fireBaseLogin()
    }

    override fun onPause() {
        super.onPause()
        mViewPagerAutoScroller?.onPause()
    }

    override fun onResume() {
        super.onResume()
        mViewPagerAutoScroller?.onResume()
    }

    fun initView(){
        val titleList= resources.getStringArray(R.array.onboarding_titles)
        val descList= resources.getStringArray(R.array.onboarding_descriptions)
        val icons = arrayListOf(
            R.drawable.onboarding_lowest_fee,
            R.drawable.onboarding_refer_earn,
            R.drawable.onboarding_instant_withdrawl,
        )
        val carouselList= mutableListOf<CarouselModel>()
        for (i in 0..2){
            carouselList.add(
                CarouselModel()
                    .apply {
                Title=titleList[i]
                Description=descList[i]
                ImageIcon =icons[i]
            })
        }

        binding.viewPager.apply {
            adapter =
                CarouselPagerAdapter(supportFragmentManager,carouselList, viewTypeType = FragmentCarousel.keyTypeLottieFile)
            addOnPageChangeListener(object : CustomViewPagerChangeListener(){
                override fun onPageSelected(position: Int) {
                    mViewPagerAutoScroller?.startScrollByPosition(position)
                    kotlin.runCatching {
                        (adapter as? CarouselPagerAdapter)?.getItem(position)?.binding?.ivGames?.playAnimation()
                    }
                }
            })
            mViewPagerAutoScroller=ViewPagerAutoScroller(this).apply {
                startScrollByPosition(0)
            }
            offscreenPageLimit=carouselList.size
            binding.tabs.setupWithViewPager(this)
        }
    }

    fun fetchAdvertisingId() {
        mViewModel.appsFlyerId = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        val d = Observable.fromCallable {
            com.rummytitans.playcashrummyonline.cardgame.AdvertisingIdClient.getAdvertisingIdInfo(this)?.id?:"0"
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mViewModel.advertisingId = it
                println("Advertising id is ---> $it")
            }, { println("Failed Advertising id is ---> ${it.message}") })

    }

    fun redirectToLogin() {
        startActivity(Intent(this, NewLoginActivity::class.java))
    }


    fun initTrueCaller() {
        trueCallerLoginHelper= TrueCallerLoginHelper(this,mViewModel.prefs,mViewModel.analyticsHelper,
            {authCode, verifyCode->
                //copyCode("auth $authCode   verify $verifyCode")
                mViewModel.loginWithTrueCaller(authCode,verifyCode)
            },{ tcOAuthError->
                redirectToLogin()
            },{
                //showError(it)
                redirectToLogin()
            })
        trueCallerLoginHelper?.initializeTrueCaller()
        mViewModel.isTrueCallerInstalled.set(trueCallerLoginHelper?.isAvailable()?:false)
    }

    fun launchTrueCaller() {
        if (trueCallerLoginHelper?.isAvailable()==true)
            trueCallerLoginHelper?.launchTrueCaller()
        else
            redirectToLogin()
    }

    override fun dismissFairplayVoilationDialog() {
        fairPlayViolationDialog?.dismiss()
    }

    override fun showFairplayVoilationDialog(fairPlayMessage: String) {
        //TODO : for fair play dialog
       /* fairPlayViolationDialog = MyDialog(this).getMyDialog(R.layout.dialog_change_language)
        fairPlayViolationDialog?.show()
        fairPlayViolationDialog?.txtMessage?.text = fairPlayMessage
        fairPlayViolationDialog?.textView29?.text = getString(R.string.attention)
        fairPlayViolationDialog?.btnContinue?.setOnClickListener {
            mViewModel.loginResponseCheckup()
        }*/
    }

    override fun userRestricted(blockUserModel: BlockUserModel?) {
        val intent = Intent(this, BlockUserActivity::class.java)
        intent.putExtra("block",blockUserModel)
        startActivity(intent)
    }

    override fun loginSuccess() {
        if (intent.hasExtra(MyConstants.INTENT_PASS_COMING_FROM)) {
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            finishAffinity()
            val intent = Intent(this, MainActivity::class.java)
            if (!TextUtils.isEmpty(mViewModel.prefs.appsFlyerDeepLink)) {
                intent.putExtra("comingForGame", true)
            }
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        trueCallerLoginHelper?.onTrueCallerResultReceived(requestCode, resultCode, data)
    }

    /*private fun fireBaseLogin(){
        Firebase.auth.signInWithEmailAndPassword("myteam11@gmail.com", "Myteam11Events@7070")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    Toast.makeText(this,"success",Toast.LENGTH_LONG).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()

                }
            }
    }*/
}

interface OnBoardNavigator {
    fun loginSuccess() {}
    fun dismissFairplayVoilationDialog() {}
    fun userRestricted(blockUserModel: BlockUserModel?){}
    fun showFairplayVoilationDialog(fairPlayMessage: String) {}
}