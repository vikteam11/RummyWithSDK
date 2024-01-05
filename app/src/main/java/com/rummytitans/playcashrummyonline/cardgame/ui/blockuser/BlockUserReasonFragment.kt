package com.rummytitans.playcashrummyonline.cardgame.ui.blockuser

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.databinding.FragmentBlockUserReasonsBinding
import com.rummytitans.playcashrummyonline.cardgame.utils.sendToCloseAbleInternalBrowser
import com.rummytitans.playcashrummyonline.cardgame.utils.setOnClickListenerDebounce
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseFragment

class BlockUserReasonFragment : BaseFragment() {
    private lateinit var binding:FragmentBlockUserReasonsBinding
    private val mViewModel:BlockUserViewModel by activityViewModels()

    companion object {
        fun newInstance(): BlockUserReasonFragment {
            val fragment = BlockUserReasonFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setTheme(inflater)

        binding = FragmentBlockUserReasonsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.blockUser = mViewModel.blockUserModel.get()

        binding.rvReasons.adapter = BlockUserReasonsAdapter(
            mViewModel.blockUserModel.get()?.Reasons?: arrayListOf()
        )

        binding.btnRequest.setOnClickListenerDebounce {
            mViewModel.analyticsHelper.fireEvent(
                AnalyticsKey.Names.RaiseARequest, bundleOf(
                    AnalyticsKey.Keys.UserID to mViewModel.loginResponse.UserId,
                    AnalyticsKey.Keys.MobileNo to mViewModel.loginResponse.Mobile,
                    AnalyticsKey.Keys.Screen to AnalyticsKey.Screens.BlockScreen
                )
            )
            findNavController()?.navigate(R.id.action_to_RequestFragment)
        }

        binding.txtHelp.setOnClickListenerDebounce {
            sendToCloseAbleInternalBrowser(
                requireActivity(),
                mViewModel.blockUserModel.get()?.HelpUrl?:"",
                getString(R.string.app_name)
            )
        }
    }
}
