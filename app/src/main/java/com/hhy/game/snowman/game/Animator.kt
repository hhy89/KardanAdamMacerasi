package com.hhy.game.snowman.game

import android.graphics.Bitmap
import android.graphics.Rect
import java.util.*

internal class Animator(private val player: Player, private val bitmapWithFrames: Bitmap, numberOfFramesHorizontally: Int,
    numberOfFramesVertically: Int, private val updateTimeMillis: Int, private val timeForOneFrameMillis: Double) {
    private val frames = ArrayList<Rect>()
    private var currentFrame = 0
    private var timeForCurrentFrameMillis = 0.0
    private lateinit var animatorThread: AnimatorThread

    @Volatile
    var isRunning = false
        private set
    private var frameWidth = 0
    private var frameHeight = 0

    fun startAnimation() {
        isRunning = true
        animatorThread = AnimatorThread()
        animatorThread.isDaemon = true
        animatorThread.start()
    }

    fun stopAnimation() {
        isRunning = false
        var flag = true
        while (flag) {
            try {
                animatorThread.join()
                flag = false
            } catch (ignored: InterruptedException) {
            }
        }
    }

    private fun splitBitmapIntoFrames(source: Bitmap, numberOfFramesHorizontally: Int, numberOfFramesVertically: Int,
                                      listDestination: ArrayList<Rect>) {
        frameWidth = source.width / numberOfFramesHorizontally
        frameHeight = source.height / numberOfFramesVertically
        for (i in 0 until numberOfFramesVertically) for (j in 0 until numberOfFramesHorizontally) {
            listDestination.add(
                Rect(
                    j * frameWidth, i * frameHeight,
                    j * frameWidth + frameWidth, i * frameHeight + frameHeight
                )
            )
        }
    }

    private inner class AnimatorThread : Thread() {
        private lateinit var newBitmap: Bitmap
        override fun run() {
            while (isRunning) {
                timeForCurrentFrameMillis += updateTimeMillis.toDouble()
                if (timeForCurrentFrameMillis >= timeForOneFrameMillis) {
                    currentFrame = (currentFrame + 1) % frames.size
                    timeForCurrentFrameMillis -= timeForOneFrameMillis
                }
                newBitmap = Bitmap.createBitmap(
                    bitmapWithFrames, frames[currentFrame].left,
                    frames[currentFrame].top, frameWidth,
                    frameHeight
                )
                player.setBitmap(newBitmap)

                sleep(updateTimeMillis.toLong())
            }
        }
    }

    init {
        splitBitmapIntoFrames(bitmapWithFrames, numberOfFramesHorizontally, numberOfFramesVertically, frames)
    }
}