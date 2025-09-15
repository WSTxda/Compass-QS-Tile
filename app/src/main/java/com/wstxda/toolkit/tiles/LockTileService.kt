package com.wstxda.toolkit.tiles

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.wstxda.toolkit.update
import com.wstxda.toolkit.services.accessibility.LockAccessibilityService
import com.wstxda.toolkit.activity.AccessibilityPermission

class LockTileService : TileService() {

    companion object {
        const val ACTION_LOCK_SCREEN = "com.wstxda.toolkit.ACTION_LOCK_SCREEN"
    }

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onTileAdded() {
        super.onTileAdded()
        updateTileState()
    }

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        if (!isAccessibilityServiceEnabled()) {
            val intent = Intent(this, AccessibilityPermission::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                val pendingIntent = PendingIntent.getActivity(
                    this, 0, intent, PendingIntent.FLAG_IMMUTABLE
                )
                startActivityAndCollapse(pendingIntent)
            } else {
                @Suppress("DEPRECATION") startActivityAndCollapse(intent)
            }
            return
        }

        val lockServiceIntent =
            Intent(applicationContext, LockAccessibilityService::class.java).apply {
                action = ACTION_LOCK_SCREEN
            }

        applicationContext.startService(lockServiceIntent)
    }

    private fun updateTileState() {
        qsTile?.update {
            state = when {
                Build.VERSION.SDK_INT < Build.VERSION_CODES.P -> Tile.STATE_UNAVAILABLE
                isAccessibilityServiceEnabled() -> Tile.STATE_ACTIVE
                else -> Tile.STATE_INACTIVE
            }
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val service = ComponentName(this, LockAccessibilityService::class.java)
        val enabled = Settings.Secure.getString(
            contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: return false

        return enabled.split(":").any { it.equals(service.flattenToString(), ignoreCase = true) }
    }
}