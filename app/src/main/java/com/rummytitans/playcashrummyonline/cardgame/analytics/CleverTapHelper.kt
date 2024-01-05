package com.rummytitans.playcashrummyonline.cardgame.analytics

import android.app.NotificationManager
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import com.clevertap.android.sdk.CleverTapAPI

class CleverTapHelper(val context: Context, val cleverTap: CleverTapAPI) {

    companion object {
        fun getInstance(context: Context, cleverTap: CleverTapAPI) = CleverTapHelper(context, cleverTap)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createChannels(
                "1234", "silent-max", "5", "myteam11-group-max", NotificationManager.IMPORTANCE_MAX
            )
            createChannels(
                "1245", "silent-low", "6", "myteam11-group-low", NotificationManager.IMPORTANCE_LOW
            )
            createChannels(
                "silent",
                "silent-max",
                "7",
                "myteam11-group-low",
                NotificationManager.IMPORTANCE_MAX
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannels(
        channelId: String, channelName: String, groupId: String, groupName: String, importance: Int
    ) {
        CleverTapAPI.createNotificationChannel(context,channelId,channelName,"MyTeam11 Notification channel",importance,groupId,true,"silent_notification_sound")
        CleverTapAPI.createNotificationChannelGroup(context, groupId, groupName)
    }
}
