package com.hhy.game.snowman.game;

public class VectorXY {
    private int x;
    private int y;

    VectorXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void set(VectorXY src) {
        this.x = src.getX();
        this.y = src.getY();
    }
}