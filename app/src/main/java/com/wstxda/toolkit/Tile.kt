package com.wstxda.toolkit

import android.service.quicksettings.Tile

fun Tile.update(applyChanges: Tile.() -> Unit) {
    applyChanges()
    updateTile()
}