package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate

import com.rummytitans.playcashrummyonline.cardgame.databinding.FragmentAppUpdateBinding
import com.rummytitans.playcashrummyonline.cardgame.models.CarouselModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseFragment

class FragmentCarousel : BaseFragment() {

    lateinit var binding: FragmentAppUpdateBinding
    var appUpdateDetailModel=ObservableField<CarouselModel>()

    companion object {
        const val keyCarouselModel="carouselModel"
        const val keyViewType="viewType"
        const val keyTypeFullScreen=1
        const val keyTypeNormalScreen=2
        const val keyTypeLottieFile=3
        fun newInstance(updateModel: CarouselModel, updateType:Int): FragmentCarousel {
            val bundle = Bundle()
            bundle.putSerializable(keyCarouselModel, updateModel)
            bundle.putInt(keyViewType, updateType)
            val f = FragmentCarousel()
            f.arguments = bundle
            return f
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        setLanguage()
        setTheme(inflater)

        binding = FragmentAppUpdateBinding.inflate(
            inflater  , container, false
        ).apply {
            lifecycleOwner = this@FragmentCarousel
        }
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener(null)
         (arguments?.getSerializable(keyCarouselModel) as? CarouselModel)?.let {
             it.viewType=arguments?.getInt(keyViewType)?: keyTypeNormalScreen
             appUpdateDetailModel.set(it)
             binding.model=appUpdateDetailModel.get()
        }
    }

}