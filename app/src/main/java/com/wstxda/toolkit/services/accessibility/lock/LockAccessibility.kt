package com.wstxda.toolkit.services.accessibility.lock

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.wstxda.toolkit.R

@SuppressLint("AccessibilityPolicy")
class LockAccessibility : AccessibilityService() {

    companion object {
        const val ACTION_LOCK_SCREEN = "com.wstxda.toolkit.ACTION_LOCK_SCREEN"
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_LOCK_SCREEN) {
            val isLockSuccessful = performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)

            if (!isLockSuccessful) {
                Toast.makeText(
                    applicationContext, getString(R.string.not_supported), Toast.LENGTH_LONG
                ).show()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}