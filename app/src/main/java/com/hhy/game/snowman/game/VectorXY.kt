package com.hhy.game.snowman.game

class VectorXY internal constructor(var x: Int, var y: Int) {

    operator fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun set(src: VectorXY) {
        x = src.x
        y = src.y
    }

}