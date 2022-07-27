package com.wstxda.compass

import android.hardware.SensorEvent
import android.hardware.SensorManager

fun SensorEvent.getAzimuthDegrees(): Float {
    val rotationMatrix = FloatArray(9)
    SensorManager.getRotationMatrixFromVector(rotationMatrix, this.values)
    val orientation = FloatArray(3)
    SensorManager.getOrientation(rotationMatrix, orientation)
    val azimuthRadians = orientation.getOrElse(0) { 0f }
    return Math.toDegrees(azimuthRadians.toDouble()).normalizeDegrees()
}

private fun Double.normalizeDegrees() = ((this + 360) % 360).toFloat()
