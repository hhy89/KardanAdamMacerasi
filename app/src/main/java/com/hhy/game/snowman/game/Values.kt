package com.hhy.game.snowman.game

import android.graphics.Rect

class Values(val width: Int, val height: Int) {

    val backgroundRect: Rect
        get() = Rect(0, 0, width, height)

    val pauseButtonRect: Rect
        get() {
            val left = width / 10 * 9
            val top = height / 50
            return Rect(
                left,
                top,
                left + pausedButtonWidth,
                top + pausedButtonHeight
            )
        }

    val pausedButtonWidth: Int
        get() = width / 11

    val pausedButtonHeight: Int
        get() = height / 11

    val swipeValue: Int
        get() = height / 80 * 7

    val swipeValue2: Int
        get() = height / 10 * 3

    val snowmanWidth: Int
        get() = width / 22 * 9

    val snowmanHeight: Int
        get() = height / 4 * 3

    val snowmanX: Int
        get() = width / 40

    val lineBottomY: Int
        get() = height / 40 * 39

    val lineMiddleY: Int
        get() = height / 40 * 33

    val lineTopY: Int
        get() = height / 40 * 27

    val scoreTextSize: Int
        get() = height / 12

    val scoreTextX: Int
        get() = width / 100

    val scoreTextY: Int
        get() = height / 100

    val recordImgWidth: Int
        get() = width / 32

    val recordImgHeight: Int
        get() = height / 18

    val recordTextSize: Int
        get() = height / 18

    val recordX: Int
        get() = width / 100

    val recordY: Int
        get() = height / 9

    val pausedTextSize: Int
        get() = height / 8

    val pausedTextX: Int
        get() = width / 10 * 3

    val pausedTextY: Int
        get() = height / 7

    val stumpWidth: Int
        get() = width / 11

    val stumpHeight: Int
        get() = height / 100 * 7

    val stoneWidth: Int
        get() = width / 20 * 3

    val stoneHeight: Int
        get() = height / 10

    val logWidth: Int
        get() = width / 4

    val logHeight: Int
        get() = height / 5

    val bonfireWidth: Int
        get() = width / 10

    val bonfireHeight: Int
        get() = height / 5

    val obstacleDistance: Int
        get() = width / 16 * 13

}