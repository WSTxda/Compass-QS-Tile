package com.wstxda.toolkit.tiles

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.wstxda.toolkit.R
import com.wstxda.toolkit.services.camera.MorseCodeFlasher
import com.wstxda.toolkit.update

class SosTileService : TileService() {

    private lateinit var sosService: MorseCodeFlasher

    override fun onCreate() {
        super.onCreate()
        sosService = MorseCodeFlasher(applicationContext)
    }

    override fun onStartListening() {
        qsTile?.update {
            updateTileState(sosService.isRunning)
        }
    }

    override fun onClick() {
        if (!sosService.isRunning) {
            startSos()
        } else {
            stopSos()
        }
    }

    private fun startSos() {
        sosService.startFlasher()
        qsTile?.update {
            updateTileState(true)
        }
    }

    private fun stopSos() {
        sosService.stopFlasher()
        qsTile?.update {
            updateTileState(false)
        }
    }

    override fun onDestroy() {
        sosService.destroyService()
        super.onDestroy()
    }

    private fun Tile.updateTileState(isRunning: Boolean) {
        state = if (isRunning) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        icon = Icon.createWithResource(applicationContext, R.drawable.ic_sos)
        label = getString(R.string.sos_tile_label)
    }
}