package com.wstxda.toolkit.tiles

import android.graphics.drawable.Icon
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.wstxda.toolkit.R
import com.wstxda.toolkit.update
import kotlin.random.Random

class CoinFlipTileService : TileService() {

    companion object {
        private var headsCount = 0
        private var tailsCount = 0
    }

    override fun onStartListening() {
    }

    override fun onStopListening() {
        setInactive()
    }

    override fun onClick() {
        if (Random.nextBoolean()) {
            setHeads()
        } else {
            setTails()
        }
    }

    private fun setHeads() {
        headsCount++
        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = getString(R.string.coin_heads_label)
            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                subtitle = getString(R.string.coin_flip_count, headsCount, tailsCount)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_coin_heads)
        }
    }

    private fun setTails() {
        tailsCount++
        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = getString(R.string.coin_tails_label)
            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                subtitle = getString(R.string.coin_flip_count, headsCount, tailsCount)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_coin_tails)
        }
    }

    private fun setInactive() {
        qsTile?.update {
            headsCount = 0
            tailsCount = 0
            state = Tile.STATE_INACTIVE
            label = getString(R.string.coin_flip_tile_label)
            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                subtitle = getString(R.string.tile_label_off)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_coin_off)
        }
    }
}