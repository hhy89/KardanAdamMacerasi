package com.hhy.game.snowman.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

class ScrollableBackground {
    private Sprite[] sprites;

    ScrollableBackground(Bitmap image, Rect hitbox) {
        sprites = new Sprite[2];
        sprites[0] = new Sprite(image, hitbox);
        sprites[1] = new Sprite(image, hitbox);
        sprites[0].setX(0);
        sprites[1].setX(sprites[0].getRight());
    }

    void update(int speed) {
        for (Sprite s : sprites) {
            s.setX(s.getX() - speed);

            // Move sprite to right of other one if it's past the left side of screen
            if (s.getRight() < 0) s.setX(
                    (s == sprites[0]) ? sprites[1].getRight() - speed: sprites[0].getRight() - speed);
        }
    }

    void draw(Canvas canvas) {
        for (Sprite s : sprites) {
            s.draw(canvas);
        }
    }
}
