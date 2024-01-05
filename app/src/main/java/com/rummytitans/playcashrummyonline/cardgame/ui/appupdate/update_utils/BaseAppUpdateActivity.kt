package com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.update_utils

import com.rummytitans.playcashrummyonline.cardgame.BuildConfig
import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.models.CarouselModel
import com.rummytitans.playcashrummyonline.cardgame.models.VersionModel
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.AppUpdateViewModel
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.CarouselPagerAdapter
import com.rummytitans.playcashrummyonline.cardgame.ui.appupdate.inappupdate.InAppUpdateHelper
import com.rummytitans.playcashrummyonline.cardgame.ui.base.BaseActivity
import com.rummytitans.playcashrummyonline.cardgame.utils.MyConstants
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import javax.inject.Inject
@AndroidEntryPoint
abstract class BaseAppUpdateActivity : BaseActivity(), FileDownloadInterface {
    var PERMISSION_REQUEST_CODE = 11
    var permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var apkFileDirectory: File? = null
    var apkFile: File? = null
    var comeFrom = ""
    var mInAppUpdateHelper: InAppUpdateHelper?=null
    lateinit var mViewModel: AppUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(
            AppUpdateViewModel::class.java
        )
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        val data: String = intent.getStringExtra(MyConstants.INTENT_COME_FROM)?:""
        comeFrom = data ?: ""
        if (BuildConfig.isPlayStoreApk==1){
            mViewModel.let {
                mInAppUpdateHelper=InAppUpdateHelper(this,it.analyticsHelper,it.prefs,it.gson)
            }
        }
    }

    protected fun setAdapter(
        viewPager: ViewPager,
        tabs: TabLayout,
        updatesList: List<CarouselModel>?,
        updateType: Int
    ) {
        viewPager.adapter =
            CarouselPagerAdapter(supportFragmentManager, updatesList?: listOf(), updateType)
        tabs.setupWithViewPager(viewPager)
    }

    private fun checkPermissionStatus(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            hasPermissions()
        } else true //android not required permission to download in app package.
    }

    protected fun onUpdateLaterClick(versionModel: VersionModel) {
        mViewModel.prefs.disableAppUpdateScreen = true
        mViewModel.prefs.disableAppUpdateVersion = versionModel.newAppVersionCode
        onBackPressed()
    }

    protected fun onDownloadPerform() {

        if (checkPermissionStatus()) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) downloadForHigherVersions() else downloadForLegacyVersions()
                apkFile = File(apkFileDirectory, "rummytitan.apk")
                if (apkFile?.exists() == true) apkFile?.delete()
                DownloadFileFromURL(mViewModel.versionResp.DownLoadURl, this,apkFile!!).execute()
            } catch (e: Exception) {
                onDownloadingFailed(e)
            }
        } else requestPermission()
    }

    private fun downloadForHigherVersions(): Boolean {
        apkFileDirectory = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "/RummyTitan")
        return apkFileDirectory?.mkdir()?:false
    }

    private fun downloadForLegacyVersions(): Boolean {
        val stringBuilder = Environment.getExternalStorageDirectory().path + "/RummyTitan"
        apkFileDirectory = File(stringBuilder)
        return apkFileDirectory?.mkdir()?:false
    }

    fun installApk() {
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val apkUri = FileProvider.getUriForFile(this, "$packageName.provider", apkFile!!)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(
                apkUri,
                "application/vnd.android.package-archive"
            )
            val resInfoList: List<ResolveInfo> =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                grantUriPermission(
                    packageName,
                    apkUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun hasPermissions(): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermission() {
        try {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val stringBuilder = StringBuilder()
                stringBuilder.append(Environment.getExternalStorageDirectory().path)
                stringBuilder.append("/RummyTitan")
                apkFileDirectory = File(stringBuilder.toString())
                apkFileDirectory?.mkdir()
                apkFile = File(apkFileDirectory, "RummyTitan.apk")
                if (apkFile?.exists() == true) {
                    apkFile?.delete()
                }
                DownloadFileFromURL(mViewModel.versionResp.DownLoadURl, this,apkFile!!).execute()
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun showError(message: Int) {}

    internal class DownloadFileFromURL(var url: String, var listener: FileDownloadInterface, var apkFile: File) :
        AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            listener.onDownloadingStart()
        }

        override fun doInBackground(vararg f_url: String): String {
            try {
                val url = URL(url)
                val connection = url.openConnection()
                connection.connect()
                val lengthOfFile = connection.contentLength
                val input: InputStream = BufferedInputStream(url.openStream(), 8192)
                val output = FileOutputStream(apkFile)
                val data = ByteArray(1024)
                var total: Long = 0
                while (true) {
                    val read = input.read(data)
                    if (read == -1) {
                        break
                    }
                    output.write(data, 0, read)
                    val j2 = total + read.toLong()
                    if (lengthOfFile > 0) {
                        publishProgress((100 * j2 / lengthOfFile.toLong()).toInt().toString())
                    }
                    total = j2
                }
                output.flush()
                output.close()
                input.close()
                listener.onDownloadingComplete()
            } catch (e: Exception) {
                listener.onDownloadingFailed(e)
            }
            return "0"
        }

        override fun onProgressUpdate(vararg values: String?) {
            values.elementAtOrNull(0)?.let {
                if (!TextUtils.isEmpty(it) && TextUtils.isDigitsOnly(it))
                    listener.onDownloadingProgress(values[0]?.toInt()?:0)
            }
        }

        override fun onPostExecute(file_url: String?) {
            super.onPostExecute(file_url)
        }
    }

    override fun showError(message: String?) {
        if (!TextUtils.isEmpty(message))
            Alerter.create(this).enableVibration(false).setText(message!!).showIcon(false)
                .setBackgroundColorRes(R.color.error_red).show()
    }

    override fun onStop() {
        super.onStop()
        mInAppUpdateHelper?.activityOnStop()
    }

    override fun onResume() {
        super.onResume()
        mInAppUpdateHelper?.activityOnResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (BuildConfig.isPlayStoreApk==1){
            if(mViewModel.versionResp.IsAppUpdate &&
                mViewModel.versionResp.playStoreApkUpdateFrom==mViewModel.versionResp.UPDATE_FROM_IN_APP_UPDATE) {
                mInAppUpdateHelper?.activityOnResult(requestCode, resultCode, data)
            }
        }
        if (requestCode == 101)
            installApk()
    }

}