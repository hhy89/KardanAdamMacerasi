package com.hhy.game.snowman.game;

public class Vector2 {
    private int x;
    private int y;

    Vector2(int x, int y) {
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

    void set(Vector2 src) {
        this.x = src.getX();
        this.y = src.getY();
    }
}