package com.hhy.game.snowman.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.hhy.game.snowman.R;
import java.util.Random;

public class Obstacle {
    private Values values;
    private Resources resources;
    private Paint drawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int OBSTACLE_TYPE_STUMP = 0;
    private static final int OBSTACLE_TYPE_STONES = 1;
    private static final int OBSTACLE_TYPE_LOG = 2;
    private static final int OBSTACLE_TYPE_BONFIRE = 3;
    private static final int LINE_BOTTOM = 0;
    private static final int LINE_MIDDLE = 1;
    private static final int LINE_TOP = 2;
    private Vector2 coordinates = new Vector2(0, 0);
    private Bitmap bitmap;
    private int width;
    private int height;
    private int obstacleType;
    private int line;

    Obstacle(Values values, Resources resources) {
        this.values = values;
        this.resources = resources;

        Random random = new Random();
        obstacleType = random.nextInt(4);
        line = random.nextInt(3);

        initializeBitmap();

        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();

        initializeCoordinates();
    }

    private void initializeBitmap() {
        switch (obstacleType) {
            case OBSTACLE_TYPE_STUMP:
                this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.stump),
                        values.getStumpWidth(), values.getStumpHeight(), false);
                break;
            case OBSTACLE_TYPE_STONES:
                this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.stones),
                        values.getStoneWidth(), values.getStoneHeight(), false);
                break;
            case OBSTACLE_TYPE_LOG:
                this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.log),
                        values.getLogWidth(), values.getLogHeight(), false);
                break;
            case OBSTACLE_TYPE_BONFIRE:
                this.bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.bonfire),
                        values.getBonfireWidth(), values.getBonfireHeight(), false);
                break;
        }
    }

    private void initializeCoordinates() {
        switch (line) {
            case LINE_BOTTOM:
                this.coordinates.set(values.getWidth(), values.getLineBottomY() - height);
                break;
            case LINE_MIDDLE:
                this.coordinates.set(values.getWidth(), values.getLineMiddleY() - height);
                break;
            case LINE_TOP:
                this.coordinates.set(values.getWidth(), values.getLineTopY() - height);
                break;
        }
    }

    int getLine() {
        return line;
    }

    public int getWidth() {
        return this.width;
    }

    public Vector2 getCoordinates() {
        return this.coordinates;
    }

    void update(int speed) {
        coordinates.set(coordinates.getX() - speed, coordinates.getY());
    }

    boolean getScreenOff() {
        return coordinates.getX() < -500;
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, coordinates.getX(), coordinates.getY(), drawingPaint);
    }

    Rect getBoundingRectangle() {
        return new Rect(coordinates.getX(), coordinates.getY(), coordinates.getX() + width, coordinates.getY() + height);
    }
}
