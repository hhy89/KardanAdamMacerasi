package com.hhy.game.snowman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class GameOverActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for fullscreen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_gameover)

        // get score and record value
        val score: Int = intent.getIntExtra("score", 0)
        val record: Int = intent.getIntExtra("record", 0)

        // gameover or highscore image
        val gameOver = findViewById<ImageView>(R.id.gameOver)
        if (score > record) gameOver.setImageResource(R.drawable.highscore)
        else gameOver.setImageResource(R.drawable.gameover)

        // score
        val recordText = findViewById<TextView>(R.id.recordText)
        recordText.text = score.toString()

        // play game button
        findViewById<ImageButton>(R.id.playGame).setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        // for keeping screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    // on back pressed
    override fun onBackPressed() {
        val intent = Intent(this, MenuActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
