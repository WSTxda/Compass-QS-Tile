package com.wstxda.toolkit.tiles.counter

import android.content.ComponentName
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.wstxda.toolkit.R
import com.wstxda.toolkit.utils.update

private const val TAG = "CounterRemoveTileService"

class CounterRemoveTileService : TileService() {

    override fun onStartListening() {
        Log.i(TAG, "Start listening")
        updateTileAsInactive()
    }

    override fun onClick() {
        Log.i(TAG, "Click")
        Counter.remove(applicationContext)
        updateTileAsActive()
        requestListeningState(this, ComponentName(this, CounterAddTileService::class.java))
    }

    private fun updateTileAsActive() {
        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = Counter.getValue(applicationContext).toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.counter_remove_tile_label)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_counter_remove)
        }
    }

    private fun updateTileAsInactive() {
        qsTile?.update {
            state = Tile.STATE_INACTIVE
            label = getString(R.string.counter_remove_tile_label)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.counter_tile_label)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_counter_remove)
        }
    }
}