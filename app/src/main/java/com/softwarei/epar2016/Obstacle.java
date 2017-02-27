package com.softwarei.epar2016;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Obstacle {
    private Bitmap image;
    private int x, y, width, height;

    public Obstacle(Bitmap img, int x, int y){
        this.x = x;
        this.y = y;
        image = img;
        height = image.getHeight();
        width = image.getWidth();
    }

    public void update(){
        x += GameView.MOVESPEED;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);
    }

    public int getX(){
        return x;
    }

    public Rect getRectangle(){
        return new Rect(x, y, x + width, y + height);
    }
}
