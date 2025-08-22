package com.wstxda.compass.tile

import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import androidx.core.content.getSystemService

class Haptics(private val context: Context) {
    private val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService<VibratorManager>()!!
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION") context.getSystemService<Vibrator>()!!
    }

    private val audioManager by lazy { context.getSystemService<AudioManager>()!! }
    private val notificationManager by lazy { context.getSystemService<NotificationManager>()!! }

    private val areHapticsEnabled: Boolean
        get() {
            // Haptic feedback disabled globally
            @Suppress("DEPRECATION") if (Settings.System.getInt(
                    context.contentResolver, Settings.System.HAPTIC_FEEDBACK_ENABLED, 0
                ) == 0
            ) {
                return false
            }

            // Ringer is silent
            if (audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT) {
                return false
            }

            // DND is enabled and configured to suppress vibrations
            if (notificationManager.currentInterruptionFilter > NotificationManager.INTERRUPTION_FILTER_PRIORITY) {
                return false
            }

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
}