package com.wstxda.toolkit.tiles.diceroll

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.wstxda.toolkit.R
import com.wstxda.toolkit.resources.icon.diceroll.DiceRollIconFactory
import com.wstxda.toolkit.resources.label.diceroll.diceLabel
import com.wstxda.toolkit.utils.Haptics
import com.wstxda.toolkit.utils.update
import kotlin.random.Random

private const val TAG = "DiceRollTileService"

class DiceRollTileService : TileService() {

    private val handler = Handler(Looper.getMainLooper())
    private var isAnimating = false
    private lateinit var haptics: Haptics

    override fun onCreate() {
        Log.i(TAG, "Create")
        super.onCreate()
        haptics = Haptics(applicationContext)
    }

    override fun onStartListening() {
        Log.i(TAG, "Start listening")
        updateTileAsInactive()
    }

    override fun onStopListening() {
        Log.i(TAG, "Stop listening")
        updateTileAsInactive()
    }

    override fun onClick() {
        Log.i(TAG, "Click")
        if (isAnimating) return
        val roll = Random.nextInt(1, 7)
        updateTileAsRolling(roll)
    }

    private fun updateTileAsRolling(finalRoll: Int) {
        isAnimating = true
        val frames = DiceRollIconFactory.getAnimationFrames()
        var frameIndex = 0

        fun updateTile(frame: Int) {
            val rollNumber = frame % 6 + 1
            qsTile?.update {
                state = Tile.STATE_ACTIVE
                label = diceLabel(rollNumber)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    subtitle = getString(R.string.dice_rolling_label)
                }
                icon = Icon.createWithResource(applicationContext, frames[frame])
            }
        }

        val runnable = object : Runnable {
            override fun run() {
                if (frameIndex < frames.size) {
                    updateTile(frameIndex)
                    haptics.tick()
                    frameIndex++
                    handler.postDelayed(this, 150)
                } else {
                    updateTileWithFace(finalRoll)
                    isAnimating = false
                }
            }
        }

        handler.post(runnable)
    }

    private fun updateTileWithFace(roll: Int) {
        qsTile?.update {
            state = Tile.STATE_ACTIVE
            label = diceLabel(roll)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.dice_roll_label)
            }
            icon = Icon.createWithResource(
                applicationContext, when (roll) {
                    1 -> R.drawable.ic_dice_1
                    2 -> R.drawable.ic_dice_2
                    3 -> R.drawable.ic_dice_3
                    4 -> R.drawable.ic_dice_4
                    5 -> R.drawable.ic_dice_5
                    6 -> R.drawable.ic_dice_6
                    else -> R.drawable.ic_dice_off
                }
            )
        }
    }

    private fun updateTileAsInactive() {
        isAnimating = false
        handler.removeCallbacksAndMessages(null)
        qsTile?.update {
            state = Tile.STATE_INACTIVE
            label = getString(R.string.dice_roll_tile_label)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                subtitle = getString(R.string.dice_roll_label)
            }
            icon = Icon.createWithResource(applicationContext, R.drawable.ic_dice_off)
        }
    }
}