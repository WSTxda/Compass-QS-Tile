package com.wstxda.toolkit.resources.label

import android.content.Context
import com.wstxda.toolkit.R

fun Context.luxMeterLabel(lux: Int): String {
    return getString(R.string.lux_meter_tile_label_lux, lux)
}