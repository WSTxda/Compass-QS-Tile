package com.wstxda.toolkit.tiles.coinflip

import android.graphics.drawable.Icon
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Handler
import android.os.Looper
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.wstxda.toolkit.R
import com.wstxda.toolkit.resources.icon.coinflip.CoinFlipIconFactory
import com.wstxda.toolkit.resources.label.coinflip.coinFlipLabel
import com.wstxda.toolkit.utils.Haptics
import com.wstxda.toolkit.utils.update
import kotlin.random.Random

@Suppress("unused")
private const val TAG = "CoinFlipTileService"

class CoinFlipTileService : TileService() {

    private val handler = Handler(Looper.getMainLooper())
    private var isAnimating = false
    private lateinit var haptics: Haptics

    companion object {
        private var headsCount = 0
        private var tailsCount = 0
    }

    override fun onCreate() {
        super.onCreate()
        haptics = Haptics(applicationContext)
    }

    override fun onStartListening() {
    }

    override fun onStopListening() {
        updateTileAsInactive()
    }

    override fun onClick() {
        if (isAnimating) return
        updateTileAsFlipping()
    }

    private fun updateTileAsFlipping() {
        isAnimating = true
        val duration = Random.nextLong(500, 1000)
        val flipResult = Random.nextBoolean()

        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = getString(R.string.coin_flippling_label)
            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                subtitle = getString(R.string.coin_flip_count, headsCount, tailsCount)
            }
            icon = Icon.createWithResource(applicationContext, CoinFlipIconFactory.getFlipIcon())
        }

        handler.postDelayed({
            updateTileWithResult(flipResult)
            isAnimating = false
        }, duration)
    }

    private fun updateTileWithResult(isHeads: Boolean) {
        if (isHeads) {
            headsCount++
        } else {
            tailsCount++
        }

        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = coinFlipLabel(isHeads)
            if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                subtitle = getString(R.string.coin_flip_count, headsCount, tailsCount)
            }
            icon = Icon.createWithResource(
                applicationContext,
                if (isHeads) CoinFlipIconFactory.getHeadsIcon() else CoinFlipIconFactory.getTailsIcon()
            )
        }
        haptics.tick()
    }

    private fun updateTileAsInactive() {
        isAnimating = false
        handler.removeCallbacksAndMessages(null)
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