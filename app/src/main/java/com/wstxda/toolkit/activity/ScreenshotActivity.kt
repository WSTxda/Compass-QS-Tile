package com.wstxda.toolkit.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.wstxda.toolkit.services.accessibility.TileAccessibilityAction
import com.wstxda.toolkit.services.accessibility.TileAccessibilityService

class ScreenshotActivity : Activity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, TileAccessibilityService::class.java).apply {
                putExtra(
                    TileAccessibilityService.ACTION_KEY, TileAccessibilityAction.TAKE_SCREENSHOT
                )
            }
            startService(intent)
        }, 400)
        finish()
    }
}