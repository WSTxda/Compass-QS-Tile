package com.wstxda.toolkit.utils

import android.service.quicksettings.Tile

fun Tile.update(applyChanges: Tile.() -> Unit) {
    applyChanges()
    updateTile()
}