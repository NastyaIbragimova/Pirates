package com.example.pirates.sea;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Ship {

    private SeaView seaView;
    private Bitmap bitmap;
    private int x, y, width, height;
    private Random random;
    private boolean drag, isAlive;

    public Ship(SeaView seaView, Bitmap bitmap) {
        this.seaView = seaView;
        this.bitmap = bitmap;
        random = new Random();
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        isAlive = true;
        x = 0;
        y = random.nextInt(seaView.getScreenHeightSea()/3 *2 - height)+seaView.getScreenHeightSea()/3;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCollision(float x2, float y2) {
        int myY2 = y + height;
        return x2 >= x && x2 <= (x + width) && y2 > y && y2 < myY2;
    }

    public int getHeight() {
        return height;
    }

    public void onDraw(Canvas c) {
        c.drawBitmap(bitmap, x, y, null);
    }

    public boolean isDrag() {
        return drag;
    }

    public void setDrag(boolean drag) {
        this.drag = drag;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
