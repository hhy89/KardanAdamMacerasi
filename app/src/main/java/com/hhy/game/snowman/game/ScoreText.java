package com.hhy.game.snowman.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.hhy.game.snowman.R;

class ScoreText {
    private final Paint drawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint scoresPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint recordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pauseTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static Bitmap recordBitmap;
    private Rect screen;
    private Resources resources;

    ScoreText(Rect screen, Resources resources) {
        this.screen = screen;
        this.resources = resources;
        {
            scoresPaint.setColor(resources.getColor(R.color.colorText));
            scoresPaint.setTextSize(screen.height() / 12);
            scoresPaint.setTypeface(Typeface.SERIF);
        }
        {
            recordPaint.setColor(resources.getColor(R.color.colorText));
            recordPaint.setTextSize(screen.height() / 18);
            recordPaint.setTypeface(Typeface.SERIF);
        }
        {
            pauseTextPaint.setColor(resources.getColor(R.color.colorText));
            pauseTextPaint.setTextSize(screen.height() / 8);
            pauseTextPaint.setTypeface(Typeface.SERIF);
        }
        recordBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.records),
                screen.height() / 18,
                screen.height() / 18, false);
    }

    void drawScore(Canvas canvas, String text) {
        canvas.drawText(text, screen.width() / 100, screen.height() / 100 + scoresPaint.getTextSize(), scoresPaint);
    }
    void drawRecord(Canvas canvas, String text) {
        canvas.drawBitmap(recordBitmap, screen.width() / 100, screen.height() / 9, drawingPaint);
        canvas.drawText(text, screen.width() / 100 + recordBitmap.getWidth(), screen.height() / 9 + recordPaint.getTextSize(), recordPaint);
    }
    void drawPause(Canvas canvas) {
        canvas.drawText(resources.getString(R.string.pause), screen.width() / 10 * 3, screen.height() / 7, pauseTextPaint);
    }
}
