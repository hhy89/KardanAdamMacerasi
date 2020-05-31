package com.hhy.game.snowman

import com.hhy.game.snowman.game.Game

class GameThread internal constructor(private val game: Game) : Thread() {

    @Volatile
    private var running = true
    private val FRAME_RATE = 1000
    override fun run() {
        var lastTime = System.currentTimeMillis().toInt()

        // Game loop
        while (running) {
            val now = System.currentTimeMillis().toInt()
            val elapsed = now - lastTime
            if (elapsed < FRAME_RATE) {
                game.update(elapsed)
                game.draw()
            }
            val lastObs = game.getLastObs()
            if (lastObs.coordinates.x + lastObs.width <= game.getObstacleDistance()) {
                game.generateObstacle()
            }
            lastTime = now
        }
    }

    fun shutdown() {
        running = false
    }

}