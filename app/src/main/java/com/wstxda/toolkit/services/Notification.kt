package com.wstxda.toolkit.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.provider.Settings
import android.service.quicksettings.TileService
import androidx.core.app.NotificationCompat
import com.wstxda.toolkit.R

private const val CHANNEL_ID = "TOOLKIT_CHANNEL"
const val NOTIFICATION_ID = 1

fun TileService.channel() = NotificationChannel(
    CHANNEL_ID, getString(R.string.channel_name), NotificationManager.IMPORTANCE_LOW
)

fun TileService.notification(): Notification =
    NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(getString(R.string.notification_title))
        .setContentText(getString(R.string.notification_label))
        .setContentIntent(notificationClickIntent()).build()


fun TileService.notificationClickIntent(): PendingIntent {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    return PendingIntent.getActivity(
        this,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
    )
}