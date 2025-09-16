package com.wstxda.toolkit.tiles.coinflip

import android.graphics.drawable.Icon
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Handler
import android.os.Looper
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.wstxda.toolkit.R
import com.wstxda.toolkit.resources.icon.coinflip.CoinFlipIconFactory
import com.wstxda.toolkit.resources.label.coinflip.coinFlipLabel
import com.wstxda.toolkit.utils.Haptics
import com.wstxda.toolkit.utils.update
import kotlin.random.Random

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
        Log.i(TAG, "Create service")
        super.onCreate()
        haptics = Haptics(applicationContext)
    }

    override fun onStartListening() {
        Log.i(TAG, "Start listening")
    }

    override fun onStopListening() {
        Log.i(TAG, "Stop listening")
        updateTileAsInactive()
    }

    override fun onClick() {
        Log.i(TAG, "Click")
        if (isAnimating) return
        val flipResult = Random.nextBoolean()
        updateTileAsFlipping(flipResult)
    }

    private fun updateTileAsFlipping(flipResult: Boolean) {
        isAnimating = true
        val frames = listOf(
            CoinFlipIconFactory.getHeadsIcon(), CoinFlipIconFactory.getTailsIcon()
        )
        var frameIndex = 0
        val maxFrames = Random.nextInt(10, 15)

        fun updateTile(isHeads: Boolean) {
            qsTile?.update {
                state = Tile.STATE_ACTIVE
                label = getString(R.string.coin_flippling_label)
                icon = Icon.createWithResource(
                    applicationContext, if (isHeads) frames[0] else frames[1]
                )
            }
        }

        val runnable = object : Runnable {
            override fun run() {
                if (frameIndex < maxFrames) {
                    updateTile(frameIndex % 2 == 0)
                    haptics.tick()
                    frameIndex++
                    handler.postDelayed(this, 150)
                } else {
                    updateTileWithResult(flipResult)
                    isAnimating = false
                }
            }
        }
        handler.post(runnable)
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
                subtitle = getString(R.string.coin_flip_label)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_coin_off)
        }
    }
}