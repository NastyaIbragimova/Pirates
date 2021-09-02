package com.example.pirates.sea;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class SeaEnemy {

    private SeaView seaView;
    private Bitmap bitmap;
    private int x, y, xSpeed, width, height;
    private Random random;

    public SeaEnemy(SeaView seaView, Bitmap bitmap) {
        this.seaView = seaView;
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        random = new Random();
        xSpeed = 10;
        x = seaView.getScreenWidthSea();
        y = random.nextInt(seaView.getScreenHeightSea() - height);
    }

    private void update() {
        x = x - xSpeed;
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

    public boolean isCollision(float x2, float y2) {
        int myY2 = y + height;
     //   Log.e("MyLog", "Y2   " + myY2);
        return x2 >= x && x2 <= x + width && y2 > y && y2 < myY2;
        // return x2 > getX() && x2 < getX() + width && y2 > getY() && y2 < getY() + height;
    }
}
