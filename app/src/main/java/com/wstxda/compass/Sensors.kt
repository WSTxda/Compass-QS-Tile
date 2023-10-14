package com.wstxda.compass

import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.view.Surface

fun SensorEvent.getAzimuthDegrees(displayRotation: Int?): Float {
    val rotationMatrix = FloatArray(9).also {
        SensorManager.getRotationMatrixFromVector(it, this.values)
    }
    val orientation = FloatArray(3).also {
        SensorManager.getOrientation(rotationMatrix, it)
    }
    val azimuthRadians = orientation.getOrElse(0) { 0f }
    val azimuthDegrees = Math.toDegrees(azimuthRadians.toDouble())
    val displayAdjustedDegrees = when (displayRotation) {
        Surface.ROTATION_0 -> azimuthDegrees
        Surface.ROTATION_90 -> azimuthDegrees + 90
        Surface.ROTATION_180 -> azimuthDegrees + 180
        Surface.ROTATION_270 -> azimuthDegrees + 270
        else -> azimuthDegrees
    }
    return displayAdjustedDegrees.normalizeDegrees()
}

private fun Double.normalizeDegrees() = ((this + 360) % 360).toFloat()
