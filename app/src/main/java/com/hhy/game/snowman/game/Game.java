package com.hhy.game.snowman.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.hhy.game.snowman.GameOverActivity;
import com.hhy.game.snowman.R;
import java.util.ArrayList;
import static android.content.Context.MODE_PRIVATE;
import static android.os.SystemClock.sleep;

public class Game {
    private Context context;
    private SurfaceHolder holder;
    private Rect screen;
    private Resources resources;
    private ScrollableBackground background;
    private Player player;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private Sprite pauseButton;
    private ScoreText scoreText;
    private BitmapFactory.Options options;

    private boolean gameRunning;
    private int currentScore = 0;
    private int recordScore = 0;
    private float touchYup;
    private float touchYdown;

    public Game(Context context, Rect screen, SurfaceHolder holder, Resources resources) {
        this.context = context;
        this.screen = screen;
        this.holder = holder;
        this.resources = resources;

        options = new BitmapFactory.Options();
        options.inScaled = false;
        restartGame();
    }

    public void onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchYdown = event.getY();

            if (new Rect(screen.width() / 10 * 9, screen.height() / 50,
                    screen.width() / 50 * 49, screen.height() / 450 * 59)
                    .contains(Math.round(event.getX()), Math.round(event.getY()))) {
                if (gameRunning) {
                    gameRunning = false;
                    player.stopAnimation();
                } else {
                    gameRunning = true;
                    player.startAnimation();
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            touchYup = event.getY();

            if ((touchYdown - touchYup > screen.height() / 80 * 7) && (touchYdown > screen.height() / 10 * 3)) {
                player.lineUp();
            }

            if ((touchYdown - touchYup < -screen.height() / 80 * 7) && (touchYup > screen.height() / 10 * 3)) {
                player.lineDown();
            }
            touchYdown = 0;
            touchYup = 0;
        }

    }

    public void update(Long elapsed) {
        if (gameRunning) {

            currentScore += elapsed / 15;
            int gameSpeed = currentScore / 200;

            if (gameSpeed < 6) gameSpeed = 6;

            background.update(gameSpeed);
            player.update();

            for (Obstacle o : obstacles) {
                o.update(gameSpeed);

                if (player.getLine() == o.getLine() && (player.getBoundingRectangle().intersect(o.getBoundingRectangle()))) {
                    finishGame();
                }
            }
        }
    }

    public void draw() {
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            drawGame(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawGame(Canvas canvas) {
        background.draw(canvas);

        for (Obstacle o : obstacles)
            o.draw(canvas);

        player.draw(canvas);

        pauseButton.draw(canvas);
        scoreText.drawRecord(canvas, String.valueOf(recordScore));
        scoreText.drawScore(canvas, String.valueOf(currentScore));
        if (!gameRunning) scoreText.drawPause(canvas);
    }

    private void restartGame() {
        obstacles.clear();
        gameRunning = true;
        loadRecord();

        Bitmap snowmanBitmap = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.snowman, options),
                screen.width() / 22 * 9, screen.height() / 4 * 3, false);
        player = new Player(snowmanBitmap, screen, 1);
        player.createAnimator(snowmanBitmap, 4, 3, 30, 30, false);
        player.startAnimation();

        background = new ScrollableBackground( Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.background, options),
                screen.width(), screen.height(), false),
                new Rect( 0, 0, screen.width(), screen.height()));

        pauseButton = new Sprite(BitmapFactory.decodeResource(resources, R.drawable.pause, options),
                new Rect(screen.width() / 10 * 9, screen.height() / 50,
                        screen.width() / 50 * 49, screen.height() / 450 * 59));

        scoreText = new ScoreText(screen, resources);

        generateObstacle();
    }

    public void generateObstacle() {
        Obstacle obstacle = new Obstacle(screen, resources);
        obstacles.add(obstacle);

        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getScreenOff())
                obstacles.remove(i);
        }
    }
    public Obstacle getLastObs() {
        return obstacles.get(obstacles.size() - 1);
    }
    public Rect getScreen() {
        return screen;
    }

    private void finishGame() {

        int score = currentScore;
        int record = recordScore;

        saveRecord();

        Bitmap snowmanBitmapDie = Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.snowman_die, options),
                screen.width() / 22 * 9, screen.height() / 4 * 3, false);
        player = new Player(snowmanBitmapDie, screen, player.getLine());

        player.createAnimator(snowmanBitmapDie, 4, 3, 30, 30, false);
        player.startAnimation();

        sleep(240);

        gameRunning = false;

        Intent intent = new Intent(context, GameOverActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("record", record);
        context.startActivity(intent);

    }

    private void saveRecord() {
        if (currentScore > recordScore) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("record", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("record_key", currentScore);
            editor.apply();
        }
    }

    private void loadRecord() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("record", MODE_PRIVATE);
        recordScore = sharedPreferences.getInt("record_key", 0);
    }

    public void pauseGame() {
        gameRunning = false;
        player.stopAnimation();
    }
}
