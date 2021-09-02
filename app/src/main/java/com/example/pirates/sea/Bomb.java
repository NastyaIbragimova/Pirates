package com.example.pirates.sea;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Bomb {

    private SeaView seaView;
    private Bitmap bitmap;
    private int x, y, speed, width, height;
    private Random random;

    public Bomb(SeaView seaView, Bitmap bitmap, int y) {
        this.seaView = seaView;
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        random = new Random();
        speed = 30;
        x = 150;
        this.y = y;
    }

    private void update() {
        x = x + speed;
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap, x, y, null);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setX(int x) {
        this.x = x;
    }
}
