package com.hhy.game.snowman.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

internal class Player(
    private var bitmap: Bitmap,
    values: Values,
    var line: Int
) {
    private var animator: Animator? = null
    private val width: Int
    private val height: Int
    private val coordinates = VectorXY(0, 0)
    private val drawingPaint =
        Paint(Paint.ANTI_ALIAS_FLAG)

    private fun changeCoordinates() {
        when (line) {
            LINE_BOTTOM -> coordinates.set(PLAYER_COORDINATES_LINE_BOTTOM)
            LINE_MIDDLE -> coordinates.set(PLAYER_COORDINATES_LINE_MIDDLE)
            LINE_TOP -> coordinates.set(PLAYER_COORDINATES_LINE_TOP)
        }
    }

    fun lineUp() {
        when (line) {
            LINE_BOTTOM -> line = LINE_MIDDLE
            LINE_MIDDLE -> line = LINE_TOP
        }
        changeCoordinates()
    }

    fun lineDown() {
        when (line) {
            LINE_TOP -> line = LINE_MIDDLE
            LINE_MIDDLE -> line = LINE_BOTTOM
        }
        changeCoordinates()
    }

    fun update() {}
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(
            bitmap,
            coordinates.x.toFloat(),
            coordinates.y.toFloat(),
            drawingPaint
        )
    }

    val boundingRectangle: Rect
        get() = Rect(
            coordinates.x + width / 3, coordinates.y,
            coordinates.x + width / 3 * 2, coordinates.y + height
        )

    fun createAnimator(
        bitmapWithFrames: Bitmap?,
        numberOfFramesHorizontally: Int,
        numberOfFramesVertically: Int, updateTimeMillis: Int,
        timeForOneFrameMillis: Double
    ) {
        if (animator == null) animator = Animator(
            this,
            bitmapWithFrames!!,
            numberOfFramesHorizontally,
            numberOfFramesVertically,
            updateTimeMillis,
            timeForOneFrameMillis
        )
    }

    fun destroyAnimator() {
        animator!!.stopAnimation()
        animator = null
    }

    fun startAnimation() {
        if (animator != null && !animator!!.isRunning) animator!!.startAnimation()
    }

    fun stopAnimation() {
        if (animator != null && animator!!.isRunning) animator!!.stopAnimation()
    }

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    companion object {
        private const val LINE_BOTTOM = 0
        private const val LINE_MIDDLE = 1
        private const val LINE_TOP = 2
        private lateinit var PLAYER_COORDINATES_LINE_BOTTOM: VectorXY
        private lateinit var PLAYER_COORDINATES_LINE_MIDDLE: VectorXY
        private lateinit var PLAYER_COORDINATES_LINE_TOP: VectorXY
    }

    init {
        width = bitmap.width / 4
        height = bitmap.height / 3
        PLAYER_COORDINATES_LINE_BOTTOM =
            VectorXY(values.snowmanX, values.lineBottomY - height)
        PLAYER_COORDINATES_LINE_MIDDLE =
            VectorXY(values.snowmanX, values.lineMiddleY - height)
        PLAYER_COORDINATES_LINE_TOP =
            VectorXY(values.snowmanX, values.lineTopY - height)
        changeCoordinates()
    }
}