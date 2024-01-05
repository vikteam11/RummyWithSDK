package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.update_utils

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus


class InAppDownloadService :Service(), InstallStateUpdatedListener {
    override fun onBind(intent: Intent?): IBinder? {
    return null
    }

    override fun onStateUpdate(it: InstallState) {
        when {
            it.installStatus() == InstallStatus.INSTALLED -> {
                //installed
            }
            it.installStatus() == InstallStatus.DOWNLOADED -> {
                //downloaded.
            }
        }
    }

    private fun popupSnackBarForCompleteUpdate() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val recentTasks = activityManager.runningAppProcesses
/*
        for (i in recentTasks.indices) {
            Log.d(
                "Executed app",
                "Application executed : " + recentTasks[i].pkgList[0] + "\t\t ID: " + recentTasks[i].pid + ""
            )
        }
        Snackbar.make(
            application.findViewById<View>(android.R.id.content).rootView,
            "Download Complete.",
            Snackbar.LENGTH_INDEFINITE)
            .setAction("Install") { AppUpdateManagerFactory.create(this).completeUpdate() }
            .setActionTextColor(ContextCompat.getColor(this, R.color.turtle_Green))
            .show()*/
    }
}