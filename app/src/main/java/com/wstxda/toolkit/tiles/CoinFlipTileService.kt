package com.wstxda.toolkit.tiles

import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.wstxda.toolkit.R
import com.wstxda.toolkit.update
import kotlin.random.Random

class CoinFlipTileService : TileService() {

    override fun onStartListening() {
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
        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = getString(R.string.coin_heads_label)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.coin_flip_tile_label)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_coin_heads)
        }
    }

    private fun setTails() {
        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = getString(R.string.coin_tails_label)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.coin_flip_tile_label)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_coin_tails)
        }
    }

    private fun setInactive() {
        qsTile?.update {
            state = Tile.STATE_INACTIVE
            label = getString(R.string.coin_flip_tile_label)
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_coin_off)
        }
    }
}