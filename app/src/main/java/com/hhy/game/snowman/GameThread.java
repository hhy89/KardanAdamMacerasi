package com.hhy.game.snowman;

import com.hhy.game.snowman.game.Game;
import com.hhy.game.snowman.game.Obstacle;

public class GameThread extends Thread {
    private Game game;
    private volatile boolean running = true;
    private final int FRAME_RATE = 1000;

    GameThread(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();

        // Game loop
        while (running) {
            long now = System.currentTimeMillis();
            long elapsed = now - lastTime;

            if (elapsed < FRAME_RATE) {
                game.update(elapsed);
                game.draw();
            }

            Obstacle lastObs = game.getLastObs();
            if (lastObs.getCoordinates().getX() + lastObs.getWidth() <= game.getScreen().width() / 16 * 13) {
                game.generateObstacle();
            }

            lastTime = now;
        }
    }

    void shutdown() {
        running = false;
    }
}
