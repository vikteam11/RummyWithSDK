package com.rummytitans.playcashrummyonline.cardgame.widget

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.Group
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rummytitans.sdk.cardgame.BR
import com.rummytitans.sdk.cardgame.databinding.DialogCouponAppliedRummyBinding
import com.rummytitans.sdk.cardgame.databinding.DialogMessageV6RummyBinding

class MyDialog(private var act: Activity) {

    var internetDialog: Dialog? = null

    fun getMyDialog(layout: Int): Dialog {
        val d = Dialog(act)
        d.window?.requestFeature(Window.FEATURE_NO_TITLE)
        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.setContentView(layout)
        return d
    }

    fun <T : ViewDataBinding> getDataBindingDialog(layout: Int): T {
        return DataBindingUtil.inflate(LayoutInflater.from(act), layout, null, false)
    }

    fun getMyDialog(layout: View): Dialog {
        val d = Dialog(act)
        d.window?.requestFeature(Window.FEATURE_NO_TITLE)
        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        d.setContentView(layout)
        return d
    }

    fun getFullScreenDialog(layout: View): Dialog {
        val d = Dialog(act, com.rummytitans.sdk.cardgame.R.style.RummySdk_DialogTheme)
        d.window?.let { dialogWindow->
            dialogWindow.requestFeature(Window.FEATURE_NO_TITLE)
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            d.setContentView(layout)
            val wlp: WindowManager.LayoutParams = dialogWindow.attributes
            wlp.gravity = Gravity.CENTER
            wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
            dialogWindow.attributes = wlp
            dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        }
        return d
    }

    fun getMyPopupWindow(layout: Int): PopupWindow {
        val popupSportType = PopupWindow(act)
        popupSportType.contentView = act.layoutInflater.inflate(layout, null)
        popupSportType.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupSportType.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupSportType.isOutsideTouchable = true
        popupSportType.isFocusable = true
        return popupSportType
    }



    fun getMyPopupWindow(layout: View): PopupWindow {
        val popupSportType = PopupWindow(act)
        popupSportType.contentView = layout
        popupSportType.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupSportType.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupSportType.isOutsideTouchable = false
        popupSportType.isFocusable = false
        popupSportType.setBackgroundDrawable(null)
        return popupSportType
    }


    fun noInternetDialog(
        retryListener: () -> Unit = { }
    ): Dialog {
        var txtCancel : TextView?= null
        var txtRetry : TextView?= null
        if (internetDialog == null) {
            internetDialog = Dialog(act)
            internetDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            internetDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            internetDialog?.setContentView(com.rummytitans.sdk.cardgame.R.layout.dialog_internet_rummy)
            internetDialog?.setCancelable(false)
            txtCancel = internetDialog?.findViewById<TextView>(com.rummytitans.sdk.cardgame.R.id.txtCancel)
            txtRetry = internetDialog?.findViewById<TextView>(com.rummytitans.sdk.cardgame.R.id.txtRetry)
            if (internetDialog?.isShowing == false) {
                kotlin.runCatching {
                    internetDialog?.show()
                }.onFailure { print(it.message) }
            }
            txtCancel?.setOnClickListener {
                internetDialog?.dismiss()
            }
        } else {
            if (internetDialog?.isShowing == false) {
                try {
                    internetDialog?.show()
                } catch (e: Exception) {
                }
            }

        }
        txtRetry?.setOnClickListener {
            internetDialog?.dismiss()
            retryListener()
        }
        return internetDialog!!
    }

    fun retryInternetDialog(
        retryListener: () -> Unit = { }
    ): Dialog? {
        var ok : TextView?= null
        var group : Group?= null
        if (internetDialog == null) {
            internetDialog = Dialog(act)
            internetDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            internetDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            internetDialog?.setContentView(com.rummytitans.sdk.cardgame.R.layout.dialog_internet_rummy)
            ok = internetDialog?.findViewById(com.rummytitans.sdk.cardgame.R.id.ok)
            group = internetDialog?.findViewById(com.rummytitans.sdk.cardgame.R.id.group)
            ok?.text = "Retry"
            group?.visibility = View.GONE
            ok?.visibility = View.VISIBLE
            internetDialog?.setCancelable(false)
            if (internetDialog?.isShowing == false) {
                try {
                    internetDialog?.show()
                } catch (e: Exception) {
                }
            }
        } else {
            if (internetDialog?.isShowing == false) {
                try {
                    internetDialog?.show()
                } catch (e: Exception) {
                }
            }

        }
        ok?.setOnClickListener {
            internetDialog?.dismiss()
            retryListener()
        }
        return internetDialog
    }

    fun noInternetDialogExit(
        retryListener: () -> Unit = { },
        cancelListener: () -> Unit = { }
    ): Dialog {
        var txtCancel : TextView? = null
        var txtRetry : TextView? = null
        if (internetDialog == null) {
            internetDialog = Dialog(act)
            internetDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            internetDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            internetDialog?.setContentView(com.rummytitans.sdk.cardgame.R.layout.dialog_internet_rummy)
            internetDialog?.setCancelable(false)
            txtCancel = internetDialog?.findViewById(com.rummytitans.sdk.cardgame.R.id.txtCancel)
            txtRetry = internetDialog?.findViewById(com.rummytitans.sdk.cardgame.R.id.txtRetry)
            txtCancel?.text = "Exit"

            if (internetDialog?.isShowing == false) {
                try {
                    internetDialog?.show()
                } catch (e: Exception) {
                }
            }

        } else {
            if (internetDialog?.isShowing == false) {
                try {
                    internetDialog?.show()
                } catch (e: Exception) {
                }
            }

        }

        txtCancel?.setOnClickListener {
            internetDialog?.dismiss()
            cancelListener()
        }
        txtRetry?.setOnClickListener {
            internetDialog?.dismiss()
            retryListener()
        }
        return internetDialog!!
    }


    fun noInternetDialogExit(
        exit: () -> Unit = { }
    ): Dialog {
        var txtRetry : TextView? = null
        internetDialog = Dialog(act)
        internetDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        internetDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        internetDialog?.setContentView(com.rummytitans.sdk.cardgame.R.layout.dialog_no_internet_exit_rummy)
        txtRetry = internetDialog?.findViewById(com.rummytitans.sdk.cardgame.R.id.txtRetry)
        internetDialog?.setCancelable(false)
        internetDialog?.show()
        txtRetry?.setOnClickListener {
            internetDialog?.dismiss()
            exit()
        }
        return internetDialog!!
    }


    fun noInternetDialogWithCancelListener(
        retryListener: () -> Unit = { },
        onCancel: () -> Unit = {}
    ): Dialog {
        var txtRetry : TextView? = null
        var txtCancel : TextView? = null
        if (internetDialog == null) {
            internetDialog = Dialog(act)
            internetDialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            internetDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            internetDialog?.setContentView(com.rummytitans.sdk.cardgame.R.layout.dialog_internet_rummy)
            txtRetry = internetDialog?.findViewById(com.rummytitans.sdk.cardgame.R.id.txtRetry)
            txtCancel = internetDialog?.findViewById(com.rummytitans.sdk.cardgame.R.id.txtCancel)
            internetDialog?.setCancelable(false)
            if (internetDialog?.isShowing == false)
                internetDialog?.show()
        } else {
            if (internetDialog?.isShowing == false)
                internetDialog?.show()
        }
        txtCancel?.setOnClickListener {
            internetDialog?.dismiss()
            onCancel()
        }
        txtRetry?.setOnClickListener {
            internetDialog?.dismiss()
            retryListener()
        }
        return internetDialog!!
    }




    fun getAlertDialog(model: com.rummytitans.sdk.cardgame.utils.alertDialog.AlertdialogModel):Dialog {
        val bindingView = getDataBindingDialog<DialogMessageV6RummyBinding>(com.rummytitans.sdk.cardgame.R.layout.dialog_message_v6_rummy).apply {
            alertModel=model
        }
        return getMyDialog(bindingView.root).apply {
            setCancelable(false)
        }
    }

    fun showCouponAppliedDialog(title:String,des:String,color:String) {
        val bindingView = getDataBindingDialog<DialogCouponAppliedRummyBinding>(com.rummytitans.sdk.cardgame.R.layout.dialog_coupon_applied_rummy).apply {
            this.title = title
            this.description = des
            this.color = color
        }
        val dialog =  getMyDialog(bindingView.root)
        bindingView.txtStart.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun <T:ViewDataBinding> getAlertPopup(model: com.rummytitans.sdk.cardgame.utils.alertDialog.AlertdialogModel, @LayoutRes layoutName: Int):Dialog {
        val bindingView = getDataBindingDialog<T>(layoutName)
        bindingView.setVariable(BR.alertModel,model)
        val dialog = getMyDialog(bindingView.root).apply {
            setCancelable(true)
        }
        bindingView.root.findViewById<View>(com.rummytitans.sdk.cardgame.R.id.btnPositive)?.setOnClickListener {
            model.onPositiveClick.invoke()
            dialog.dismiss()
        }
        bindingView.root.findViewById<View>(com.rummytitans.sdk.cardgame.R.id.btnNegative)?.setOnClickListener {
            model.onNegativeClick.invoke()
            dialog.dismiss()
        }
        bindingView.root.findViewById<View>(com.rummytitans.sdk.cardgame.R.id.btnClose)?.setOnClickListener {
            model.onCloseClick.invoke()
            dialog.dismiss()
        }
        return dialog
    }




}