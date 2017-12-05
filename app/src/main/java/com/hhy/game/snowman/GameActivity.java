package com.hhy.game.snowman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends Activity {

    private GameView view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);
        view = findViewById(R.id.gameView);
    }

    @Override
    // geri tuşuna bastığında uygulamadan çık
    public void onBackPressed() {
        view.pauseGame();

        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}