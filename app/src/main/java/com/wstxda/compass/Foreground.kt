package com.wstxda.compass

import android.app.Notification
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.core.app.ServiceCompat

fun TileService.startForegroundCompat(notificationId: Int, notification: Notification) {
    if (VERSION.SDK_INT >= VERSION_CODES.Q) {
        ServiceCompat.startForeground(
            this, notificationId, notification, FOREGROUND_SERVICE_TYPE_MANIFEST
        )
    } else {
        startForeground(notificationId, notification)
    }
}