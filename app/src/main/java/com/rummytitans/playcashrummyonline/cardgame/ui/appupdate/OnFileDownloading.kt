package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate

internal interface OnFileDownloading {
    fun onDownloadingStart()
    fun onDownloadingProgress(progress: Int)
    fun onDownloadingComplete()
    fun onDownloadingFailed(e: Exception?)
}