package com.wstxda.toolkit.tile

import android.service.quicksettings.Tile

fun Tile.update(applyChanges: Tile.() -> Unit) {
    applyChanges()
    updateTile()
}