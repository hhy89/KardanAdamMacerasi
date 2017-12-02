package com.hhy.game.snowman.game;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class Animator {
    private Bitmap bitmapWithFrames;
    private int updateTimeMillis;
    private ArrayList<Rect> frames = new ArrayList<>();
    private int currentFrame;
    private double timeForCurrentFrameMillis;
    private double timeForOneFrameMillis;
    private AnimatorThread animatorThread;
    private volatile boolean isRunning;
    private int frameWidth;
    private int frameHeight;
    private IAnimatable animatable;

    Animator(IAnimatable animatable, Bitmap bitmapWithFrames, int numberOfFramesHorizontally,
                    int numberOfFramesVertically, int updateTimeMillis,
                    double timeForOneFrameMillis, boolean isDisposable) {
        this.animatable = animatable;
        this.bitmapWithFrames = bitmapWithFrames;
        this.updateTimeMillis = updateTimeMillis;
        this.timeForOneFrameMillis = timeForOneFrameMillis;
        splitBitmapIntoFrames(bitmapWithFrames, numberOfFramesHorizontally, numberOfFramesVertically, frames);

        if (isDisposable) {
            Timer inspectorTimer = new Timer(true);
            InspectorTimerTask inspectorTimerTask = new InspectorTimerTask();
            inspectorTimer.schedule(inspectorTimerTask, 0, Math.round(updateTimeMillis));
        }
    }

    void startAnimation() {
        isRunning = true;
        animatorThread = new AnimatorThread();
        animatorThread.setDaemon(true);
        animatorThread.start();
    }

    void stopAnimation() {
        isRunning = false;
        boolean flag = true;

        while (flag) {
            try {
                animatorThread.join();
                flag = false;
            } catch (InterruptedException ignored) {}
        }
    }

    boolean isRunning() {
        return isRunning;
    }

    private void splitBitmapIntoFrames(Bitmap source, int numberOfFramesHorizontally,
                                       int numberOfFramesVertically, ArrayList<Rect> listDestination) {
        frameWidth = source.getWidth() / numberOfFramesHorizontally;
        frameHeight = source.getHeight() / numberOfFramesVertically;

        for (int i = 0; i < numberOfFramesVertically; i++)
            for (int j = 0; j < numberOfFramesHorizontally; j++) {
                listDestination.add(new Rect(j * frameWidth, i * frameHeight,
                        j * frameWidth + frameWidth, i * frameHeight + frameHeight));
            }
    }

    private class InspectorTimerTask extends TimerTask {
        private int elapsedTime;

        @Override
        public void run() {
            elapsedTime += updateTimeMillis;

            if (elapsedTime >= updateTimeMillis * 12) {
                stopAnimation();
                this.cancel();
            }
        }
    }

    private class AnimatorThread extends Thread {
        private Bitmap newBitmap;

        @Override
        public void run() {
            while (isRunning) {
                timeForCurrentFrameMillis += updateTimeMillis;
                if (timeForCurrentFrameMillis >= timeForOneFrameMillis) {
                    currentFrame = (currentFrame + 1) % frames.size();
                    timeForCurrentFrameMillis = timeForCurrentFrameMillis - timeForOneFrameMillis;
                }

                newBitmap = Bitmap.createBitmap(bitmapWithFrames, frames.get(currentFrame).left,
                        frames.get(currentFrame).top, frameWidth,
                        frameHeight);

                animatable.setBitmap(newBitmap);

                try {
                    sleep(updateTimeMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
