package com.hhy.game.snowman.game

import android.content.res.Resources
import android.graphics.*
import com.hhy.game.snowman.R
import kotlin.random.Random

class Obstacle internal constructor(private val values: Values, private val resources: Resources) {
    private val drawingPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val coordinates = VectorXY(0, 0)
    private lateinit var bitmap: Bitmap
    val width: Int
    private val height: Int
    private val obstacleType: Int
    val line: Int

    private fun initializeBitmap() {
        when (obstacleType) {
            OBSTACLE_TYPE_STUMP -> bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.stump),
                values.stumpWidth, values.stumpHeight, false
            )
            OBSTACLE_TYPE_STONES -> bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.stones),
                values.stoneWidth, values.stoneHeight, false
            )
            OBSTACLE_TYPE_LOG -> bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.log),
                values.logWidth, values.logHeight, false
            )
            OBSTACLE_TYPE_BONFIRE -> bitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.bonfire),
                values.bonfireWidth, values.bonfireHeight, false
            )
        }
    }

    private fun initializeCoordinates() {
        when (line) {
            LINE_BOTTOM -> coordinates[values.width] = values.lineBottomY - height
            LINE_MIDDLE -> coordinates[values.width] = values.lineMiddleY - height
            LINE_TOP -> coordinates[values.width] = values.lineTopY - height
        }
    }

    fun update(speed: Int) {
        coordinates[coordinates.x - speed] = coordinates.y
    }

    val screenOff: Boolean
        get() = coordinates.x < -500

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, coordinates.x.toFloat(), coordinates.y.toFloat(), drawingPaint)
    }

    val boundingRectangle: Rect
        get() = Rect(coordinates.x, coordinates.y, coordinates.x + width, coordinates.y + height
        )

    companion object {
        private const val OBSTACLE_TYPE_STUMP = 0
        private const val OBSTACLE_TYPE_STONES = 1
        private const val OBSTACLE_TYPE_LOG = 2
        private const val OBSTACLE_TYPE_BONFIRE = 3
        private const val LINE_BOTTOM = 0
        private const val LINE_MIDDLE = 1
        private const val LINE_TOP = 2
    }

    init {
        obstacleType = Random.nextInt(4)
        line = Random.nextInt(3)
        initializeBitmap()
        width = bitmap.width
        height = bitmap.height
        initializeCoordinates()
    }
}