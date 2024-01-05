package com.rummytitans.playcashrummyonline.cardgame.observer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.collection.ArrayMap
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.pushnotification.fcm.CTFcmMessageHandler
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.rummytitans.playcashrummyonline.cardgame.R
import com.rummytitans.playcashrummyonline.cardgame.data.SharedPreferenceStorage
import com.rummytitans.playcashrummyonline.cardgame.models.MatchModel
import com.rummytitans.playcashrummyonline.cardgame.ui.MainActivity
import com.rummytitans.playcashrummyonline.cardgame.ui.launcher.SplashActivity
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        val prefs = SharedPreferenceStorage(applicationContext)
        prefs.firebaseToken = token
        prefs.firebaseTokenChanged = true
        CTFcmMessageHandler().onNewToken(applicationContext, token)
        Log.e("onNewToken","token  $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        //myteamRummy standlone 170277989622
        val extras = Bundle()
        for ((key, value) in remoteMessage.data.entries) {
            extras.putString(key, value)
        }
        //firebase id
        val info = CleverTapAPI.getNotificationInfo(extras)
        if (info.fromCleverTap) {
            CTFcmMessageHandler().createNotification(applicationContext, remoteMessage)
        }else {
            if (remoteMessage.from != "365484678723") {
                //ignore if received from ONeSignal
                sendNotification(notification, data)
            }
        }
    }

    /**
    check whether notification is timer type or not
     */
    private fun isNotificationTimerType(data: Map<String, String>): Boolean {
        data[SMARTTECH_CUSTOM_PAYLOAD_KEY].let {
            if (TextUtils.isEmpty(it)) {
                return data[IS_NOTIFICATION_TIMER]?.equals("1") ?: false
            } else {
                Gson().fromJson(it, SmartTechCustomPayloadModel::class.java).let { model ->
                    return if (model.isTimer == "1") {
                        (data as? ArrayMap<String, String>)?.apply {
                            put(IS_NOTIFICATION_TIMER, model.isTimer)
                            put(NOTIFICATION_MATCH_ID, model.matchId)
                            put(NOTIFICATION_STARTTIME, model.matchStartTime)
                        }
                        true
                    } else false
                }
            }
        }
    }

    /**
    check is data in proper format
     */
    private fun isNotificationDataProper(
        data: Map<String, String>,
        notification: RemoteMessage.Notification?
    ): Boolean {
        return if (!TextUtils.isEmpty(data[NOTIFICATION_MATCH_ID]) && TextUtils.isDigitsOnly(data[NOTIFICATION_MATCH_ID])) {
            val matchID = data[NOTIFICATION_MATCH_ID]?.toInt() ?: 0
            if (!TextUtils.isEmpty(data[NOTIFICATION_STARTTIME])) {
                notification?.let {
                    (data as? HashMap)?.apply {
                        data.put(NOTIFICATION_TITLE, notification.title ?: getString(R.string.app_name))
                        data.put(NOTIFICATION_BODY, notification.body ?: getString(R.string.app_name))
                    }
                }
                timerNotification(matchID, data[NOTIFICATION_STARTTIME] ?: "", data)
                true
            } else false
        } else false
    }

    /**
    start notificationTimerService
     */
    private fun timerNotification(matchID: Int, startTime: String, data: Map<String, String>) {
        val match = MatchModel()
            .apply {
            MatchId = matchID
            StartDate = startTime
        }

    }

    private fun sendNotification(
        notification: RemoteMessage.Notification?, data: Map<String, String>?
    ) {
        var resultIntent: Intent? = null
        var notificationTypeForBackstack = 0

        if (data != null && isNotificationTimerType(data) && isNotificationDataProper(
                data,
                notification
            )
        )
            return

        if (SharedPreferenceStorage(applicationContext).loginCompleted) {
            if (!data.isNullOrEmpty() && data.containsKey(FCM_PARAM_SEND_TO) && !TextUtils.isEmpty(
                    data.getValue(
                        FCM_PARAM_SEND_TO
                    )
                )
            ) {
/*
                when (data.getValue(FCM_PARAM_SEND_TO)) {
                    "add_cash" -> {
                        notificationTypeForBackstack = 3
                        resultIntent = Intent(this, AddCashActivity::class.java)
                        if (data.containsKey(NOTIFICATION_ADD_CASH_AMOUNT))
                            resultIntent.putExtra(
                                "amount",
                                data.getValue(NOTIFICATION_ADD_CASH_AMOUNT)
                            )
                    }

                    "refer" -> {
                        notificationTypeForBackstack = 4
                        resultIntent = Intent(this, ReferEarnActivity::class.java)
                    }
                    "games" -> {
                        notificationTypeForBackstack = 5
                        resultIntent = Intent(this, MainActivity::class.java)
                            .putExtra("comingForGame", true)
                            .putExtra(MyConstants.INTENT_GAME_DEEPLINK, data.getValue(
                                CLICK_ACTION_DEEPLINK))
                    }

                    "deeplink" -> {
                        notificationTypeForBackstack = 6
                        resultIntent = Intent(this, DeepLinkActivityRummy::class.java)
                            .putExtra("deepLink", data["deeplink"] ?: "")
                    }

                    else -> {
                        resultIntent = Intent(this, MainActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                }
*/
            }
        } else {
            resultIntent = Intent(this, SplashActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(
                resultIntent ?: Intent(applicationContext, SplashActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
            if (notificationTypeForBackstack == 1) {
                editIntentAt(1)?.putExtra("match_id", data?.getValue(NOTIFICATION_MATCH_ID))
                    ?.putExtra("app_type", data?.getValue(NOTIFICATION_APP_TYPE))

            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            } else {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }

        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.app_name))
            .setContentTitle(
                if (TextUtils.isEmpty(data?.get("title"))) notification?.title else data?.get("title")
            )
            .setContentText(
                if (TextUtils.isEmpty(data?.get("content"))) data?.get(NOTIFICATION_BODY) else data?.get(
                    "content"
                )
            )
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(resultPendingIntent)
            .setLights(Color.RED, 1000, 300)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setSmallIcon(R.drawable.ic_titan_notification)



        if (!TextUtils.isEmpty(data?.get("image")))
            try {

                val picture = data?.get("image")
                if (picture != null && "" != picture) {
                    val url = URL(picture)
                    val bigPicture =
                        BitmapFactory.decodeStream(url.openConnection().getInputStream())
                    notificationBuilder.setStyle(
                        NotificationCompat.BigPictureStyle().bigPicture(bigPicture)
                            .setSummaryText(
                                data["content"]
                            )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val isSilent:Boolean =if (!TextUtils.isEmpty(data?.get("isPinpoint"))){
            if (!TextUtils.isEmpty(data?.get("silentPush")))
                data?.get("silentPush")?.equals("1")?:false
            else false
        }else false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.app_name),
                CHANNEL_NAME,
                if (isSilent) NotificationManager.IMPORTANCE_LOW else NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESC
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())

    }

    companion object {
        val FCM_PARAM_SEND_TO = "splash"
        const val IS_NOTIFICATION_TIMER = "IS_TIMER"
        const val NOTIFICATION_STARTTIME = "MATCH_START_TIME"
        const val NOTIFICATION_MATCH_ID = "notification_match_id"
        val NOTIFICATION_CONTEST_ID = "notification_contest_id"
        val NOTIFICATION_ADD_CASH_AMOUNT = "notification_add_cash_amount"
        val NOTIFICATION_APP_TYPE = "notification_app_type"
        val NOTIFICATION_TITLE = "_notoficaiton_title"
        val NOTIFICATION_BODY = "notification_body"
        val CHANNEL_NAME = "RummyTitans"
        val CHANNEL_DESC = "RummyTitans"
        val CLICK_ACTION_DEEPLINK = "clickAction"
        private const val SMARTTECH_CUSTOM_PAYLOAD_KEY = "smtCustomPayload"
    }


    private data class SmartTechCustomPayloadModel(
        @SerializedName(NOTIFICATION_STARTTIME) val matchStartTime: String,
        @SerializedName(NOTIFICATION_MATCH_ID) val matchId: String,
        @SerializedName(IS_NOTIFICATION_TIMER) val isTimer: String
    )
}