package com.hhy.game.snowman

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.hhy.game.snowman.game.Game
import com.hhy.game.snowman.game.Values

class GameView(context: Context, attrs: AttributeSet): SurfaceView(context, attrs), SurfaceHolder.Callback {
    private lateinit var gameThread: GameThread
    private lateinit var game: Game

    override fun surfaceCreated(holder: SurfaceHolder) {
        val values = Values(width, height)
        game = Game(context, values, holder, resources)
        gameThread = GameThread(game)
        gameThread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameThread.run {
            shutdown()
            join()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        game.onTouchEvent(event)
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    fun pauseGame() {
        game.pauseGame()
    }

    init {
        holder.addCallback(this)
    }
}