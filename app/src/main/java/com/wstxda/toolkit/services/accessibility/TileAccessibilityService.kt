package com.wstxda.toolkit.services.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.wstxda.toolkit.R

@SuppressLint("AccessibilityPolicy")
class TileAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit

    companion object {
        const val ACTION_KEY = "com.wstxda.toolkit.ACTION_KEY"

        fun isServiceEnabled(context: Context): Boolean {
            val service = ComponentName(context, TileAccessibilityService::class.java)
            val enabled = Settings.Secure.getString(
                context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            ) ?: return false
            return enabled.split(":")
                .any { it.equals(service.flattenToString(), ignoreCase = true) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.getIntExtra(ACTION_KEY, -1) ?: -1
        if (action != -1) {
            val actionSuccessful = performGlobalAction(action)
            if (!actionSuccessful) {
                Toast.makeText(
                    applicationContext, getString(R.string.not_supported), Toast.LENGTH_LONG
                ).show()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}