package com.rummytitans.playcashrummyonline.cardgame.widget

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.viewpager.widget.ViewPager
import java.util.*

class ViewPagerAutoScroller(private val viewPager: ViewPager,private var currentOfferPage:Int=0) {

   private var timer: Timer? = null
   private var pagesCount =0
    init {
        pagesCount=viewPager.adapter?.count?:0
    }

    fun startScrollByPosition(position:Int,delay:Long=2){
        currentOfferPage=position
        timer?.cancel()
        val handler = Handler(Looper.getMainLooper())
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    if (currentOfferPage == pagesCount - 1) currentOfferPage = 0
                    else currentOfferPage++
                    viewPager.setCurrentItem(currentOfferPage, currentOfferPage!=0)
                }
            }
        }, delay * 1000, delay * 1000)
    }

    fun onPause(){
        timer?.cancel()
    }

    fun onResume(){
        startScrollByPosition(currentOfferPage)
    }
}