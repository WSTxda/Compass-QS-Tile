package com.wstxda.compass

import android.app.NotificationManager
import android.graphics.drawable.Icon
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.display.DisplayManager
import android.service.quicksettings.Tile
import android.util.Log
import android.view.Display
import android.widget.Toast
import androidx.core.content.getSystemService
import com.wstxda.compass.tile.IconFactory
import com.wstxda.compass.tile.label
import com.wstxda.compass.tile.update

private const val TAG = "TileService"
private const val SENSOR_DELAY = SensorManager.SENSOR_DELAY_UI

class TileService : android.service.quicksettings.TileService(), SensorEventListener {

    private val sensorManager
        get() = getSystemService<SensorManager>()

    private val notificationManager
        get() = getSystemService<NotificationManager>()

    private val displayManager
        get() = getSystemService<DisplayManager>()

    private val sensor by lazy {
        // TYPE_ROTATION_VECTOR is a fusion of gyro, accelerometer and magnetometer, which is more
        // accurate and responsive than just using the magnetometer.
        // TYPE_GEOMAGNETIC_ROTATION_VECTOR is used as a fallback if gyro is not available.
        sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            ?: sensorManager?.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR)
    }

    private val isSupported
        get() = sensor != null

    private val displayRotation
        get() = displayManager?.getDisplay(Display.DEFAULT_DISPLAY)?.rotation

    private lateinit var iconFactory: IconFactory

    override fun onCreate() {
        Log.i(TAG, "Create")
        iconFactory = IconFactory(applicationContext, R.drawable.ic_qs_compass_on)
        notificationManager?.createNotificationChannel(channel())
    }

    override fun onDestroy() {
        Log.i(TAG, "Destroy")
        super.onDestroy()
    }

    override fun onStartListening() {
        when (qsTile?.state) {
            Tile.STATE_ACTIVE -> startCompass()
        }
    }

    override fun onStopListening() {
        when (qsTile?.state) {
            Tile.STATE_ACTIVE -> stopCompass()
        }
    }

    override fun onClick() {
        if (!isSupported) showNotSupported() else toggleTile()
    }

    private fun showNotSupported() {
        Toast.makeText(this, R.string.not_supported, Toast.LENGTH_LONG).show()
    }

    private fun toggleTile() {
        when (qsTile?.state) {
            Tile.STATE_ACTIVE -> setInactive()
            Tile.STATE_INACTIVE -> setActive()
        }
    }

    private fun setActive() {
        qsTile?.update { state = Tile.STATE_ACTIVE }
        startCompass()
    }

    private fun setInactive() {
        qsTile?.update {
            state = Tile.STATE_INACTIVE
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_qs_compass_off)
            label = getString(R.string.tile_label)
        }
        stopCompass()
    }

    private val notificationId = 1

    private fun startCompass() {
        val notification = notification()
        startForeground(notificationId, notification)
        sensorManager?.registerListener(this, sensor, SENSOR_DELAY)
    }

    private fun stopCompass() {
        Log.i(TAG, "Stop")
        sensorManager?.unregisterListener(this)
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent) {
        val degrees = event.getAzimuthDegrees(displayRotation)
        Log.v(TAG, degrees.toString())
        qsTile?.update {
            label = label(degrees)
            icon = iconFactory.build(degrees)
        }
    }
}