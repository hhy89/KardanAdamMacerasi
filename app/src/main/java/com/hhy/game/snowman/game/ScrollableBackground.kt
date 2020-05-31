package com.hhy.game.snowman.game

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

internal class ScrollableBackground(image: Bitmap, hitbox: Rect) {
    private val sprites = arrayOf(Sprite(image, hitbox), Sprite(image, hitbox))

    init {
        sprites[0].x = 0
        sprites[1].x = sprites[0].getRight()
    }

    fun update(speed: Int) {
        for (s in sprites) {
            s.x -= speed

            // Move sprite to right of other one if it's past the left side of screen
            if (s.getRight() < 0) {
                s.x = if (s === sprites[0])
                    sprites[1].getRight() - speed
                else
                    sprites[0].getRight() - speed
            }
        }
    }

    fun draw(canvas: Canvas) {
        for (s in sprites) {
            s.draw(canvas)
        }
    }

    // animation thread class
    private inner class Sprite(private val image: Bitmap, private val hitbox: Rect) {
        private var width: Int = hitbox.width()
        private var height: Int = hitbox.height()
        var x: Int = hitbox.left
        var y: Int = hitbox.top

        fun draw(canvas: Canvas) {
            hitbox.set(x, y, x + width, y + height)
            canvas.drawBitmap(image, null, hitbox, null)
        }

        fun getRight(): Int {return x + width}
    }

}