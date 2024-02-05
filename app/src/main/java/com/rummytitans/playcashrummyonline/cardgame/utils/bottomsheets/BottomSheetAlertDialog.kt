package com.rummytitans.playcashrummyonline.cardgame.utils.bottomsheets

import android.app.Activity
import com.rummytitans.playcashrummyonline.cardgame.databinding.BottomSheetDialogAlertBinding
import com.rummytitans.playcashrummyonline.cardgame.utils.setOnClickListenerDebounce
import android.content.Context
import android.view.LayoutInflater
import com.rummytitans.playcashrummyonline.cardgame.utils.alertDialog.AlertdialogModel


class BottomSheetAlertDialog(context: Activity, val alertdialogModel: AlertdialogModel, val colorCode:String="#fff") : BaseBottomSheetDialog(context){
    private lateinit var binding:BottomSheetDialogAlertBinding
    init {
        binding = BottomSheetDialogAlertBinding.inflate(LayoutInflater.from(context),null,false)
        binding.alertModel = alertdialogModel
        setContentView(binding.root)
        onViewRender()
    }

    private fun onViewRender() {
        binding.btnContinue.setOnClickListenerDebounce {
            dismiss()
            alertdialogModel.onPositiveClick()
        }
        binding.txCancel.setOnClickListenerDebounce {
            dismiss()
            alertdialogModel.onNegativeClick()
        }

        binding.ivClose.setOnClickListenerDebounce {
            dismiss()
            alertdialogModel.onCloseClick()
        }
    }
}
