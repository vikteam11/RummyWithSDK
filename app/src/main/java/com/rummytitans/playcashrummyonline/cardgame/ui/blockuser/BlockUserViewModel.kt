package com.rummytitans.playcashrummyonline.cardgame.ui.blockuser

import com.rummytitans.playcashrummyonline.cardgame.analytics.AnalyticsHelper
import com.rummytitans.playcashrummyonline.cardgame.api.APIInterface
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.models.BlockUserModel
import com.rummytitans.playcashrummyonline.cardgame.models.HelpDeskModel
import com.rummytitans.playcashrummyonline.cardgame.models.LoginResponse
import com.rummytitans.playcashrummyonline.cardgame.ui.BaseViewModel
import com.rummytitans.playcashrummyonline.cardgame.utils.ConnectionDetector
import androidx.databinding.ObservableField
import com.google.gson.Gson
import javax.inject.Inject

class BlockUserViewModel @Inject constructor(
    val prefs: SharedPreferenceStorage,
    val apis: APIInterface,
    val gson: Gson,
    val analyticsHelper: AnalyticsHelper,
    val connectionDetector: ConnectionDetector
) : BaseViewModel<com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseNavigator>(connectionDetector) {

    val selectedColor = ObservableField(prefs.safeColor)
    val supportResponse = ObservableField<HelpDeskModel>()
    val blockUserModel = ObservableField<BlockUserModel>()
    val loginResponse: LoginResponse = gson.fromJson(prefs.loginResponse, LoginResponse::class.java)

    fun getHelpDesk() {
        apiCall(apis.getHelpDesk(
            "",
            "",
            ""
        ), {
            supportResponse.set(it.Response)
        })
    }


}