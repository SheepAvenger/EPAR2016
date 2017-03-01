package com.softwarei.epar2016;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x;
    private int screenWidth, screenHeight;
    
    public Background(Bitmap image, int screenWidth, int screenHeight) {
        this.image = image;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void update() {
        x += GameView.MOVESPEED;
        if(x < -screenWidth){
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, 0, null);
        if(x < 0) {
            canvas.drawBitmap(image, x + screenWidth, 0, null);
        }
    }
}
