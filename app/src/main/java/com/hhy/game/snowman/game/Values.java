package com.hhy.game.snowman.game;

import android.graphics.Rect;

public class Values {
    private int width;
    private int height;

    public Values(int width, int height) {
        this.width = width;
        this.height = height;
    }

    int getWidth() {
        return width;
    }
    int getHeight() {
        return height;
    }
    Rect getBackgroundRect() {
        return new Rect( 0, 0, width, height);
    }
    Rect getPauseButtonRect() {
        int left = width / 10 * 9;
        int top = height / 50;
        int right = left + getPausedButtonWidth();
        int bottom = top + getPausedButtonHeight();

        return new Rect(left, top, right, bottom);
    }
    int getPausedButtonWidth() {
        return width / 11;
    }
    int getPausedButtonHeight() {
        return height / 11;
    }
    int getSwipeValue() {
        return height / 80 * 7;
    }
    int getSwipeValue2() {
        return height / 10 * 3;
    }
    int getSnowmanWidth() {
        return width / 22 * 9;
    }
    int getSnowmanHeight() {
        return height / 4 * 3;
    }
    int getSnowmanX() {
        return width / 40;
    }
    int getLineBottomY() {
        return height / 40 * 39;
    }
    int getLineMiddleY() {
        return height / 40 * 33;
    }
    int getLineTopY() {
        return height / 40 * 27;
    }
    int getScoreTextSize() {
        return height / 12;
    }
    int getScoreTextX() {
        return width / 100;
    }
    int getScoreTextY() {
        return height / 100;
    }
    int getRecordImgWidth() {
        return width / 32;
    }
    int getRecordImgHeight() {
        return height / 18;
    }
    int getRecordTextSize() {
        return height / 18;
    }
    int getRecordX() {
        return width / 100;
    }
    int getRecordY() {
        return height / 9;
    }
    int getPausedTextSize() {
        return height / 8;
    }
    int getPausedTextX() {
        return width / 10 * 3;
    }
    int getPausedTextY() {
        return height / 7;
    }
    int getStumpWidth() {
        return width / 11;
    }
    int getStumpHeight() {
        return height / 100 * 7;
    }
    int getStoneWidth() {
        return width / 20 * 3;
    }
    int getStoneHeight() {
        return height / 10;
    }
    int getLogWidth() {
        return width / 4;
    }
    int getLogHeight() {
        return height / 5;
    }
    int getBonfireWidth() {
        return width / 10;
    }
    int getBonfireHeight() {
        return height / 5;
    }
    int getObstacleDistance() {
        return width / 16 * 13;
    }
}
