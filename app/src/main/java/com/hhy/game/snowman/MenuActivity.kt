package com.hhy.game.snowman

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView

class MenuActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // for fullscreen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_menu)

        // record text
        val recordText = findViewById<TextView>(R.id.recordText)
        val sharedPreferences = getSharedPreferences("snowman", Context.MODE_PRIVATE)
        val recordScore: Int = sharedPreferences.getInt("record_key", 0)
        recordText.text = recordScore.toString()

        // play button
        findViewById<ImageButton>(R.id.playGame).setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        // how to play button
        findViewById<ImageButton>(R.id.howTo).setOnClickListener {
            val intent = Intent(this, HowToPlayActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        // for keeping screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    // on back pressed
    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
