package com.rummytitans.playcashrummyonline.cardgame.ui.blockuser

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.databinding.ActivityBlockUserBinding
import com.rummytitans.playcashrummyonline.cardgame.models.BlockUserModel
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.changeStatusBarColor
import com.rummytitans.playcashrummyonline.cardgame.utils.setOnClickListenerDebounce
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.rummytitans.playcashrummyonline.cardgame.utils.LocaleHelper
import javax.inject.Inject


class BlockUserActivity : BaseActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mViewModel: BlockUserViewModel

    lateinit var binding: ActivityBlockUserBinding

    val blockUserModel : BlockUserModel? by lazy {
        intent.getSerializableExtra("block") as? BlockUserModel?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (TextUtils.isEmpty(LocaleHelper.getLanguage(this))) {
            LocaleHelper.setLocale(this)
        } else {
            LocaleHelper.onAttach(this)
        }
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(this, viewModelFactory).get(BlockUserViewModel::class.java)
        mViewModel.navigator = this
        mViewModel.navigatorAct = this
        mViewModel.myParentDialog = MyDialog(this)
        mViewModel.blockUserModel.set(
            intent.getSerializableExtra("block") as? BlockUserModel?
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_block_user)

        changeStatusBarColor(binding.root,R.color.block_user_status_color)

        binding.icBack.setOnClickListenerDebounce {
            finish()
        }
        binding.txtTitle.setOnClickListenerDebounce {
            binding.icBack.performClick()
        }

        mViewModel.analyticsHelper.fireEvent(
            AnalyticsKey.Names.ScreenLoadDone, bundleOf(
                AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.BlockScreen
            )
        )
    }

}