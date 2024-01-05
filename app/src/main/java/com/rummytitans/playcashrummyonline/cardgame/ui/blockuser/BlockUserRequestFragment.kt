package com.rummytitans.playcashrummyonline.cardgame.ui.blockuser

import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.databinding.FragmentBlockUserRequestBinding
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseFragment
import com.rummytitans.playcashrummyonline.cardgame.utils.*
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.rummytitans.playcashrummyonline.cardgame.ui.WebViewActivity
import com.rummytitans.sdk.cardgame.ui.WebChatActivity

class BlockUserRequestFragment : BaseFragment()  {
    private lateinit var binding:FragmentBlockUserRequestBinding
    private val mViewModel:BlockUserViewModel by activityViewModels()
    companion object {
        fun newInstance(): BlockUserRequestFragment {
            val fragment = BlockUserRequestFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setTheme(inflater)

        binding = FragmentBlockUserRequestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = mViewModel

        mViewModel.getHelpDesk()

        binding.cardCall.setOnClickListenerDebounce {
            onCallClick()
        }
        binding.cardEmail.setOnClickListenerDebounce {
            onEmailClick()
        }
        binding.cardChat.setOnClickListenerDebounce {
            onChatClick()
        }
        binding.cardFaq.setOnClickListenerDebounce {
            onFAQClick()
        }
    }

    fun onChatClick() {
        if (!ClickEvent.check(ClickEvent.BUTTON_CLICK)) return
        mViewModel.analyticsHelper.fireEvent(
            AnalyticsKey.Names.ButtonClick, bundleOf(
                AnalyticsKey.Keys.ButtonName to AnalyticsKey.Values.LiveChat,
                AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.BlockScreen
            )
        )
        startActivity(Intent(requireActivity(), WebChatActivity::class.java))
    }

    fun onCallClick() {
        if (!ClickEvent.check(ClickEvent.BUTTON_CLICK)) return

        if(mViewModel.supportResponse.get()?.MobileStaus == true){
            mViewModel.analyticsHelper.fireEvent(
                AnalyticsKey.Names.ButtonClick, bundleOf(
                    AnalyticsKey.Keys.ButtonName to AnalyticsKey.Values.CallUs,
                    AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.BlockScreen
                )
            )
            val mobile = mViewModel.supportResponse.get()?.Mobile ?:"+911414579900"
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${mobile}")
            startActivity(intent)
        }
    }

    fun onEmailClick() {
        if (!ClickEvent.check(ClickEvent.BUTTON_CLICK)) return
        val deviceDetails =
            "" + "\n\n\n" + "OS Version : " + System.getProperty("os.version") +
                    "\n OS Build Version : " + android.os.Build.VERSION.SDK +
                    "\n Device : " + android.os.Build.DEVICE +
                    "\n Device Model : " + android.os.Build.MODEL +
                    "\n Product : " + android.os.Build.PRODUCT +
                    "\n App Version : " + BuildConfig.VERSION_NAME +
                    "\n Version Code : " + BuildConfig.VERSION_CODE +
                    "\n Build Type : " + BuildConfig.BUILD_TYPE


        mViewModel.analyticsHelper.fireEvent(
            AnalyticsKey.Names.ButtonClick, bundleOf(
                AnalyticsKey.Keys.ButtonName to AnalyticsKey.Values.EmailUs,
                AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.BlockScreen
            )
        )


        val mailId = mViewModel.supportResponse.get()?.Email?:getString(R.string.supportEmail)
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL,mailId )
        intent.putExtra(Intent.EXTRA_SUBJECT, "Support - Android App")
        intent.putExtra(Intent.EXTRA_TEXT, deviceDetails)
        intent.data = Uri.parse("mailto:$mailId")
        kotlin.runCatching {
            startActivity(Intent.createChooser(intent, "Send Email"))
        }.onFailure { print(it.message) }
    }

    fun onFAQClick() {
        if (!ClickEvent.check(ClickEvent.BUTTON_CLICK)) return
        mViewModel.analyticsHelper.fireEvent(
            AnalyticsKey.Names.ButtonClick, bundleOf(
                AnalyticsKey.Keys.ButtonName to AnalyticsKey.Values.FAQ,
                AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.BlockScreen
            )
        )
        startActivity(
            Intent(requireActivity(), WebViewActivity::class.java)
                .putExtra(MyConstants.INTENT_PASS_SHOW_CROSS, true)
                .putExtra(MyConstants.INTENT_PASS_WEB_URL, getPlatformBasedWebViewUrl(WebViewUrls.FAQ))
                .putExtra(MyConstants.INTENT_PASS_WEB_TITLE, getString(R.string.faq_s))
        )
    }

}
