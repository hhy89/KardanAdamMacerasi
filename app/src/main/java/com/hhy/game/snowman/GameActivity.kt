package com.hhy.game.snowman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager

class GameActivity : Activity() {
    private lateinit var view: GameView
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fullscreen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_game)

        view = findViewById(R.id.gameView)

        // for keeping screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    // geri tuşuna bastığında uygulamadan çık
    override fun onBackPressed() {
        view.pauseGame()
        val intent = Intent(this, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}