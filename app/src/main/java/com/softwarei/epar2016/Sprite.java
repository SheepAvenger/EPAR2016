package com.softwarei.epar2016;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private int score;
    private boolean jumping, falling, ducking, playing, collision, recovery;
    private Animation animation = new Animation();
    private Animation animation2 = new Animation();

    private long startTime;
    private int xInitial, xNext, xCurrent, yInitial, yCurrent, width, height, xInitial2, xNext2, xCurrent2, yInitial2, yCurrent2, width2, height2;

    public Sprite(Bitmap image, int numberOfFrames, Bitmap image2, int numberOfFrames2) {

        int ground = -30;
        score = 0;

        this.height = image.getHeight();
        this.width = image.getWidth()/numberOfFrames;
        xInitial = 300;
        xCurrent = xInitial;
        yInitial = GameView.HEIGHT - height + ground;
        yCurrent = yInitial;
        Bitmap[] playerImage = new Bitmap[numberOfFrames];
        for(int i = 0; i < playerImage.length; i++) {
            playerImage[i] = Bitmap.createBitmap(image, i*width, 0, width, height);
        }
        animation.setFrames(playerImage);
        animation.setDelay(10);

        this.height2 = image2.getHeight()/numberOfFrames2;
        this.width2 = image2.getWidth();
        xInitial2 = xCurrent + width - width2;
        xCurrent2 = xInitial2;
        yInitial2 = GameView.HEIGHT - height2 + ground;
        yCurrent2 = yInitial2;
        Bitmap[] playerImage2 = new Bitmap[numberOfFrames2];
        for(int i = 0; i < playerImage2.length; i++) {
            playerImage2[i] = Bitmap.createBitmap(image2, 0, i*height2, width2, height2);
        }
        animation2.setFrames(playerImage2);
        animation2.setDelay(10);

        startTime = System.nanoTime();
    }

    public void setJumping(long jumpButtonTime) {
        if(!ducking) {
            System.out.println("jumpButtonTime: " + jumpButtonTime / 1000000);
            jumping = true;
        }
    }

    public boolean getJumping() {
        return jumping;
    }

    public boolean getFalling() {
        return falling;
    }

    public void setDucking(boolean ducking) {
        if(!jumping || !falling) {
            this.ducking = ducking;
        }
    }

    public void setCollision() {
        collision = true;
    }

    public boolean getCollision() {
        return collision;
    }

    public void setRecovery() {
        recovery = true;
    }

    public void update() {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }

        if(ducking) {
            animation2.update();
        }
        else {
            animation.update();
        }

        if(jumping) {
            yCurrent -= 10;
        }
        else if(yCurrent < yInitial) {
            yCurrent += 10;
        }
        else {
            falling = false;
        }

        if(yCurrent < 100) {
            jumping = false;
            falling = true;
        }

        if(collision) {
            xCurrent += GameView.MOVESPEED;
            xCurrent2 += GameView.MOVESPEED;
            if(xCurrent <= xNext) {
                xNext -= 50;
            }
            if(xCurrent2 <= xNext2) {
                xNext2 -= 50;
                collision = false;
            }
        }

        if(recovery) {
            xCurrent -= GameView.MOVESPEED;
            xCurrent2 -= GameView.MOVESPEED;
            if(xCurrent >= xInitial) {
                recovery = false;
            }
        }
    }

    public void draw(Canvas canvas) {
        if(ducking){
            canvas.drawBitmap(animation2.getImage(), xCurrent2, yCurrent2, null);
        }
        else {
            canvas.drawBitmap(animation.getImage(), xCurrent, y, null);
        }
    }

    public int getScore() {
        return (this.score * 3);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getPlaying() {
        return this.playing;
    }

    public void setPlaying(boolean playing) {
        xCurrent = xInitial;
        xNext = xInitial - 50;
        xCurrent2 = xInitial2;
        xNext2 = xInitial2 - 50;
        this.playing = playing;
    }

    public void resetScore() {
        this.score = 0;
    }

    public Rect getRectangle() {
        if(ducking){
            return new Rect(xCurrent2, yCurrent2, xCurrent2+width2, yCurrent2+height2);
        }
        else {
            return new Rect(xCurrent, yCurrent, xCurrent+width, yCurrent+height);
        }

    }
}
