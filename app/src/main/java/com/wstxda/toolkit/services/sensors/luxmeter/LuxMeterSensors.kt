package com.wstxda.toolkit.services.sensors.luxmeter

import android.hardware.SensorEvent

fun SensorEvent.getLux(): Int {
    return values[0].toInt()
}