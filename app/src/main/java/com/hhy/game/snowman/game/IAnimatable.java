package com.hhy.game.snowman.game;

import android.graphics.Bitmap;

public interface IAnimatable {
    void createAnimator(Bitmap bitmapWithFrames,
                        int numberOfFramesHorizontally,
                        int numberOfFramesVertically, int updateTimeMillis,
                        double timeForOneFrameMillis, boolean isDisposable);
    void destroyAnimator();
    void startAnimation();
    void stopAnimation();
    void setBitmap(Bitmap bitmap);
}