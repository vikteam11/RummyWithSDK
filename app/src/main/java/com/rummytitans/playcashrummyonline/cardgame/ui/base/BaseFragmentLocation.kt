package com.rummytitans.playcashrummyonline.cardgame.ui.base

import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsKey
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.utils.bottomsheets.LottieBottomSheetDialog
import com.rummytitans.playcashrummyonline.cardgame.utils.bottomsheets.models.BottomSheetStatusDataModel
import com.rummytitans.playcashrummyonline.cardgame.widget.MyDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import com.rummytitans.sdk.cardgame.utils.locationservices.constants.LocationConstants

import com.rummytitans.sdk.cardgame.utils.locationservices.uiModules.RequestGPSActivity
import com.tapadoo.alerter.Alerter


abstract class BaseFragmentLocation : BaseFragment() {
    private var requestLocationCount=0
    fun showAllowBottomSheet(){
        RequestGPSActivity.startActivityForResultGetLatLong(requireActivity())
    }

    abstract fun onLocationFound(lat: Double, log: Double)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationConstants.REQUEST_USER_LOCATION) {
            when (resultCode) {
                LocationConstants.RESPONSE_LOCATION_OK -> {
                    data?.extras?.apply {
                        val latitude =
                            getDouble(
                                LocationConstants.RESPONSE_LATITUDE,
                                LocationConstants.RESPONSE_DEFAULT_LAT_LOG
                            )
                        val logitutde =
                            getDouble(
                                LocationConstants.RESPONSE_LOGITUDE,
                                LocationConstants.RESPONSE_DEFAULT_LAT_LOG
                            )
                        if (latitude != LocationConstants.RESPONSE_DEFAULT_LAT_LOG) {
                            onLocationFound(latitude, logitutde)
                        } else {
                            //Alert user to provideLocation
                            requestLocationCount-=1
                            showAllowBottomSheet()
                        }
                    }
                }

                LocationConstants.RESPONSE__LOCATION_FAILED -> {
                    //showAllowBottomSheet()
                }
            }
        }
    }

    fun onRestrictLocationFound(descriptionMsg: String) {
        val alertDialog = LottieBottomSheetDialog(
            requireActivity(), BottomSheetStatusDataModel().apply {
                title =getString(R.string.restricted_location_found)
                description = getString(R.string.restricted_location_msg)
                positiveButtonName= getString(R.string.dialog_times_up_go_back)
                imageIcon = R.drawable.ic_restrict
                cancelAble = true
            },
        )
        alertDialog.show()

        alertDialog.setOnDismissListener {

        }
    }
}
