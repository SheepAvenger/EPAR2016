package com.softwarei.epar2016;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {

    private Bitmap image;
    private int x;

    public Background(Bitmap image) {
        this.image = image;
    }

    public void update() {
        x += GameView.MOVESPEED;
        if(x < -GameView.WIDTH){
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, 0, null);
        if(x < 0) {
            canvas.drawBitmap(image, x + GameView.HEIGHT, 0, null);
        }
    }
}
