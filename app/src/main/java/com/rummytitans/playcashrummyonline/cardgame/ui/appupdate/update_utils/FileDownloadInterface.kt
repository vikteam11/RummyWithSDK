package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.update_utils

internal interface FileDownloadInterface {
    fun onDownloadingStart()
    fun onDownloadingProgress(progress: Int)
    fun onDownloadingComplete()
    fun onDownloadingFailed(e: Exception?)
}