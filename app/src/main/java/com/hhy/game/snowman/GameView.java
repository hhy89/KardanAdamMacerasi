package com.hhy.game.snowman;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hhy.game.snowman.game.Game;
import com.hhy.game.snowman.game.Values;

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
        Values values = new Values(getWidth(), getHeight());
        game = new Game(getContext(), values, holder, getResources());
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
