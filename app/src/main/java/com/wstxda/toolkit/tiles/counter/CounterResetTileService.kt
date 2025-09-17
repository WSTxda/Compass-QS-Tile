package com.wstxda.toolkit.tiles.counter

import android.content.ComponentName
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.wstxda.toolkit.R
import com.wstxda.toolkit.utils.counter.CounterValue
import com.wstxda.toolkit.utils.update

private const val TAG = "CounterResetTileService"

class CounterResetTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        Log.i(TAG, "Start listening")
        updateTile()
    }

    override fun onClick() {
        super.onClick()
        Log.i(TAG, "Click")
        CounterValue.reset(applicationContext)
        requestListeningState(
            applicationContext, ComponentName(applicationContext, CounterAddTileService::class.java)
        )
        requestListeningState(
            applicationContext,
            ComponentName(applicationContext, CounterRemoveTileService::class.java)
        )

        updateTile()
    }

    private fun updateTile() {
        qsTile?.update {
            state = Tile.STATE_INACTIVE
            label = getString(R.string.counter_reset_tile_label)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.counter_tile_label)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_counter_reset)
        }
    }
}