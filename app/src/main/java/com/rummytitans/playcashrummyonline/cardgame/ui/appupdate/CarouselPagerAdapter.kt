package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate

import com.rummytitans.playcashrummyonline.cardgame.models.CarouselModel
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CarouselPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val arrFragments = ArrayList<FragmentCarousel>()
    private val mList = ArrayList<CarouselModel>()

    constructor(fm: FragmentManager, response: List<CarouselModel>, viewTypeType:Int=FragmentCarousel.keyTypeFullScreen) : this(fm) {
        arrFragments.clear()
        mList.clear()
        mList.addAll(response)
        for (data in response) {
            arrFragments.add(FragmentCarousel.newInstance(data,viewTypeType))
        }
    }

    override fun getItem(position: Int): FragmentCarousel = arrFragments[position]

    override fun getCount(): Int = arrFragments.size

    override fun getPageTitle(position: Int): CharSequence? = ""
}
