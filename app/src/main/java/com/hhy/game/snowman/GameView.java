package com.hhy.game.snowman;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hhy.game.snowman.game.Game;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;
    GameThread gameThread;
    Game game;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        game = new Game(getContext(), new Rect(0, 0, getWidth(), getHeight()), holder, getResources());
        gameThread = new GameThread(game);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (gameThread != null) {
            gameThread.shutdown();

            while (gameThread != null) {
                try {
                    gameThread.join();
                    gameThread = null;
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.onTouchEvent(event);
        performClick();
        return true;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    public void pauseGame() {
        game.pauseGame();
    }
}
