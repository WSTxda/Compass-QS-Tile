package com.wstxda.toolkit.utils

import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.os.Build
import android.os.PowerManager
import android.os.VibrationAttributes
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.util.Log
import androidx.core.content.getSystemService

class Haptics(private val context: Context) {

    companion object {
        private const val TAG = "MorseCodeFlasher"
    }

    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService<VibratorManager>()!!
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION") context.getSystemService<Vibrator>()!!
    }

    private val audioManager by lazy { context.getSystemService<AudioManager>()!! }
    private val notificationManager by lazy { context.getSystemService<NotificationManager>()!! }
    private val powerManager by lazy { context.getSystemService<PowerManager>()!! }

    private val areHapticsEnabled: Boolean
        get() {
            @Suppress("DEPRECATION") if (Settings.System.getInt(
                    context.contentResolver, Settings.System.HAPTIC_FEEDBACK_ENABLED, 0
                ) == 0
            ) {
                return false
            }

            if (audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT) {
                return false
            }

            if (notificationManager.currentInterruptionFilter > NotificationManager.INTERRUPTION_FILTER_PRIORITY) {
                return false
            }

            if (powerManager.isPowerSaveMode) return false

            return true
        }

    fun tick() {
        if (!areHapticsEnabled) return

        val effect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
        } else {
            @Suppress("DEPRECATION") VibrationEffect.createOneShot(
                10, VibrationEffect.DEFAULT_AMPLITUDE
            )
        }
        vibrator.vibrate(effect)
    }

    fun morse(duration: Long) {
        try {
            val vibrationEffect =
                VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE)
            val audioAttributes =
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM).build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val vibrationAttributes =
                    VibrationAttributes.createForUsage(VibrationAttributes.USAGE_ALARM)
                vibrator.vibrate(vibrationEffect, vibrationAttributes)
            } else {
                @Suppress("DEPRECATION") vibrator.vibrate(vibrationEffect, audioAttributes)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to vibrate", e)
        }
    }

    fun cancel() {
        vibrator.cancel()
    }
}