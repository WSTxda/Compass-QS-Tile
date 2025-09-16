package com.wstxda.toolkit.resources.label.luxmeter

import com.wstxda.toolkit.R
import com.wstxda.toolkit.tiles.luxmeter.LuxMeterTileService

fun LuxMeterTileService.luxMeterLabel(lux: Int): String {
    return getString(R.string.lux_meter_tile_label_lux, lux)
}