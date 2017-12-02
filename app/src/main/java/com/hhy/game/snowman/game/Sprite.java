package com.hhy.game.snowman.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Sprite {
    private Bitmap image;
    private Rect hitbox;
    private int width;
    private int height;
    private double x;
    private double y;
    private Paint borderPaint = new Paint();

    Sprite(Bitmap image, Rect hitbox) {
        this.image = image;
        this.hitbox = hitbox;

        this.width = hitbox.width();
        this.height = hitbox.height();
        this.x = hitbox.left;
        this.y = hitbox.top;

        Paint noAliasPaint = new Paint();
        noAliasPaint.setAntiAlias(false);
        noAliasPaint.setFilterBitmap(false);
        noAliasPaint.setDither(false);
        noAliasPaint.setColor(Color.GREEN);

        borderPaint.setStrokeWidth(10);
        borderPaint.setStyle(Paint.Style.STROKE);

        Paint vectorPaint = new Paint();
        vectorPaint.setStyle(Paint.Style.STROKE);
        vectorPaint.setStrokeWidth(8);
        vectorPaint.setColor(Color.GREEN);
    }

    void draw(Canvas canvas) {
        if (image != null) {
            setY(y);
            canvas.drawBitmap(image, null, hitbox, null);
        } else {
            drawHitbox(canvas, Color.MAGENTA);
        }
    }

    private void drawHitbox(Canvas canvas, int color) {
        borderPaint.setColor(color);
        setY(y);
        canvas.drawRect(hitbox, borderPaint);
    }

    double getX() {return this.x;}

    double getRight() {return x + width;}

    void setX(double x) {
        this.x = x;
        hitbox.set(
                (int) x,
                (int) y,
                (int) x + width,
                (int) y + height
        );
    }

    private void setY(double y) {
        this.y = y;
        hitbox.set(
                (int) x,
                (int) y,
                (int) x + width,
                (int) y + height
        );
    }
}
