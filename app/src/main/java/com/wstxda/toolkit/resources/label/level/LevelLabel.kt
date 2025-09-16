package com.wstxda.toolkit.resources.label.level

import com.wstxda.toolkit.R
import com.wstxda.toolkit.tiles.level.LevelTileService

fun LevelTileService.levelLabel(degrees: Int): String {
    return if (degrees == 0) {
        getString(R.string.level_tile_zero_degrees)
    } else {
        getString(R.string.level_tile_label_degrees, degrees)
    }
}