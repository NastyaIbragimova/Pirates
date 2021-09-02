package com.example.pirates;

import android.widget.ImageView;

public class Cell {

    public int x;
    public int y;
    public String type;
    public ImageView imageView;
    public boolean isOpen;

    public Cell(int x, int y, String type, ImageView imageView) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.imageView = imageView;
    }
}
