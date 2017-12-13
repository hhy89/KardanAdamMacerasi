package com.hhy.game.snowman.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.hhy.game.snowman.R;

class ScoreText {
    private final Paint drawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint scoresPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint recordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pauseTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static Bitmap recordBitmap;
    private Values values;
    private Resources resources;

    ScoreText(Values values, Resources resources) {
        this.values = values;
        this.resources = resources;

        scoresPaint.setColor(resources.getColor(R.color.colorText));
        scoresPaint.setTextSize(values.getScoreTextSize());
        scoresPaint.setTypeface(Typeface.SERIF);
        recordPaint.setColor(resources.getColor(R.color.colorText));
        recordPaint.setTextSize(values.getRecordTextSize());
        recordPaint.setTypeface(Typeface.SERIF);
        pauseTextPaint.setColor(resources.getColor(R.color.colorText));
        pauseTextPaint.setTextSize(values.getPausedTextSize());
        pauseTextPaint.setTypeface(Typeface.SERIF);

        recordBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.records),
                values.getRecordImgWidth(),
                values.getRecordImgHeight(), false);
    }

    void drawScore(Canvas canvas, String text) {
        canvas.drawText(text, values.getScoreTextX(), values.getScoreTextY() + scoresPaint.getTextSize(), scoresPaint);
    }
    void drawRecord(Canvas canvas, String text) {
        canvas.drawBitmap(recordBitmap, values.getRecordX(), values.getRecordY(), drawingPaint);
        canvas.drawText(text, values.getRecordX() + recordBitmap.getWidth(),
                values.getRecordY() + recordPaint.getTextSize(), recordPaint);
    }
    void drawPause(Canvas canvas) {
        canvas.drawText(resources.getString(R.string.pause), values.getPausedTextX(), values.getPausedTextY(), pauseTextPaint);
    }
}
