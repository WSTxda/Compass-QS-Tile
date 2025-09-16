package com.wstxda.toolkit.tiles

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.wstxda.toolkit.R
import com.wstxda.toolkit.resources.icon.DiceIconFactory
import com.wstxda.toolkit.resources.label.diceLabel
import com.wstxda.toolkit.update
import kotlin.random.Random

class DiceRollTileService : TileService() {

    private val handler = Handler(Looper.getMainLooper())
    private var isAnimating = false

    override fun onStartListening() {
        setInactive()
    }

    override fun onStopListening() {
        setInactive()
    }

    override fun onClick() {
        if (isAnimating) return
        val roll = Random.nextInt(1, 7)
        animateRoll(roll)
    }

    private fun animateRoll(finalRoll: Int) {
        isAnimating = true
        val frames = DiceIconFactory.getAnimationFrames()
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
                    frameIndex++
                    handler.postDelayed(this, 150)
                } else {
                    setFace(finalRoll)
                    isAnimating = false
                }
            }
        }

        handler.post(runnable)
    }

    private fun setFace(roll: Int) {
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

    private fun setInactive() {
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