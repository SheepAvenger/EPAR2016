package com.softwarei.epar2016;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
    private int score;
    private boolean jumping, ducking, playing, collision, recovery;
    private Animation animation, animation2;

    private long startTime;
    private int xInitial, xNext, xCurrent, yInitial, yCurrent, width, height;
    private int xInitial2, xNext2, xCurrent2, yInitial2, yCurrent2, width2, height2;
    private int jumpForceInitial, jumpForce, punishLength;
    private Bitmap image1, image2;

    public Sprite(int index, int numberOfFrames, int numberOfFrames2, Context context, int score, boolean recovery, int[] position) {
        punishLength = 70;
        int ground = -20;
        this.score = score;
        this.recovery = recovery;
        Character sprites = new Character(context);
        sprites.setIndex(index);
        image1 = sprites.getCharacter();
        sprites.setIndex(index+1);
        image2 = sprites.getCharacter();

        this.height = image1.getHeight();
        this.width = image1.getWidth()/numberOfFrames;
        xInitial = 300;
        yInitial = GameView.HEIGHT - height + ground;
        if(recovery) {
            xCurrent = position[0];
        }
        else {
            xCurrent = xInitial;
        }
        yCurrent = yInitial;
        Bitmap[] playerImage = new Bitmap[numberOfFrames];
        for(int i = 0; i < playerImage.length; i++) {
            playerImage[i] = Bitmap.createBitmap(image1, i*width, 0, width, height);
        }
        animation = new Animation();
        animation.setFrames(playerImage);
        animation.setDelay(100);

        this.height2 = image2.getHeight()/numberOfFrames2;
        this.width2 = image2.getWidth();
        xInitial2 = xCurrent + width - width2;
        yInitial2 = GameView.HEIGHT - height2 + ground;
        if(recovery) {
            xCurrent2 = position[1];
        }
        else {
            xCurrent2 = xInitial2;
        }
        yCurrent2 = yInitial2;
        Bitmap[] playerImage2 = new Bitmap[numberOfFrames2];
        for(int i = 0; i < playerImage2.length; i++) {
            playerImage2[i] = Bitmap.createBitmap(image2, 0, i*height2, width2, height2);
        }
        animation2 = new Animation();
        animation2.setFrames(playerImage2);
        animation2.setDelay(100);

        startTime = System.nanoTime();
    }

    public void setJumping(long jumpButtonTime) {
        if(!ducking) {
            int jumpForceAddition = (int)(jumpButtonTime / 1000000000);
            //System.out.println("jumpHeightAddition: " + jumpForceAddition);
            jumpForceInitial = 20 + jumpForceAddition * 5;
            jumpForce = jumpForceInitial;
            jumping = true;
        }
    }

    public boolean getJumping() {
        return jumping;
    }

    public void setDucking(boolean ducking) {
        if(!jumping) {
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
        if(elapsed >= 1000) {
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
            yCurrent -= jumpForce;
            jumpForce--;
        }

        if(jumpForce <= -jumpForceInitial && yInitial <= yCurrent) {
            jumping = false;
        }

        if(collision) {
            xCurrent += GameView.MOVESPEED;
            xCurrent2 += GameView.MOVESPEED;
            if(xCurrent2 <= xNext2) {
                xNext2 -= punishLength;
            }
            if(xCurrent <= xNext) {
                xNext -= punishLength;
                collision = false;
            }
        }

        if(recovery) {
            xCurrent -= -1;
            xCurrent2 -= -1;
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
            canvas.drawBitmap(animation.getImage(), xCurrent, yCurrent, null);
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore() {
        score = 0;
    }

    public boolean getPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        if(playing) {
            xNext = xInitial - punishLength;
            xNext2 = xInitial2 - punishLength;
        }
        this.playing = playing;
    }

    public Rect getRectangle() {
        if(ducking){
            return new Rect(xCurrent2, yCurrent2, xCurrent2+width2, yCurrent2+height2);
        }
        else {
            return new Rect(xCurrent, yCurrent, xCurrent+width, yCurrent+height);
        }

    }

    public int[] getPosition() {
        int[] position = new int[2];
        position[0] = xCurrent;
        position[1] = xCurrent2;
        return position;
    }
}
