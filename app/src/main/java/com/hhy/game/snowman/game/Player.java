package com.hhy.game.snowman.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

class Player {
    private Animator animator;
    private int line;
    private Bitmap bitmap;
    private int width;
    private int height;
    private VectorXY coordinates = new VectorXY(0, 0);
    private final Paint drawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final int LINE_BOTTOM = 0;
    private static final int LINE_MIDDLE = 1;
    private static final int LINE_TOP = 2;
    private static VectorXY PLAYER_COORDINATES_LINE_BOTTOM;
    private static VectorXY PLAYER_COORDINATES_LINE_MIDDLE;
    private static VectorXY PLAYER_COORDINATES_LINE_TOP;

    Player(Bitmap image, Values values, int line) {
        bitmap = image;
        this.line = line;
        width = bitmap.getWidth() / 4;
        height = bitmap.getHeight() / 3;

        PLAYER_COORDINATES_LINE_BOTTOM = new VectorXY(values.getSnowmanX(), values.getLineBottomY() - height);
        PLAYER_COORDINATES_LINE_MIDDLE = new VectorXY(values.getSnowmanX(), values.getLineMiddleY() - height);
        PLAYER_COORDINATES_LINE_TOP = new VectorXY(values.getSnowmanX(), values.getLineTopY() - height);

        changeCoordinates();
    }

    private void changeCoordinates() {
        switch (line) {
            case LINE_BOTTOM:
                coordinates.set(PLAYER_COORDINATES_LINE_BOTTOM);
                break;
            case LINE_MIDDLE:
                coordinates.set(PLAYER_COORDINATES_LINE_MIDDLE);
                break;
            case LINE_TOP:
                coordinates.set(PLAYER_COORDINATES_LINE_TOP);
                break;
        }
    }
    int getLine() {
        return line;
    }

    void lineUp() {
        switch (line) {
            case LINE_BOTTOM:
                line = LINE_MIDDLE;
                break;
            case LINE_MIDDLE:
                line = LINE_TOP;
                break;
        }
        changeCoordinates();
    }

    void lineDown() {
        switch (line) {
            case LINE_TOP:
                line = LINE_MIDDLE;
                break;
            case LINE_MIDDLE:
                line = LINE_BOTTOM;
                break;
        }
        changeCoordinates();
    }

    void update() {

    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, coordinates.getX(), coordinates.getY(), drawingPaint);
    }

    Rect getBoundingRectangle() {
        return new Rect(coordinates.getX() + width / 3, coordinates.getY(),
                coordinates.getX() + width / 3 * 2, coordinates.getY() + height);
    }

    void createAnimator(Bitmap bitmapWithFrames,
                               int numberOfFramesHorizontally,
                               int numberOfFramesVertically, int updateTimeMillis,
                               double timeForOneFrameMillis) {
        if (animator == null)
            animator = new Animator(this, bitmapWithFrames, numberOfFramesHorizontally, numberOfFramesVertically, updateTimeMillis, timeForOneFrameMillis);
    }

    void destroyAnimator() {
        animator.stopAnimation();
        animator = null;
    }

    void startAnimation() {
        if ((animator != null) && (!animator.isRunning()))
            animator.startAnimation();
    }

    void stopAnimation() {
        if ((animator != null) && (animator.isRunning()))
            animator.stopAnimation();
    }

    void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
