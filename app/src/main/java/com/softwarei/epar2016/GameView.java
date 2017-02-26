package com.softwarei.epar2016;


import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;

    private long jumpButtonTime;
    private GameLoop gameLoop;
    private Background backGround;
    private Sprite sprite;

    public GameView(Context context) {
        super(context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

    }

    public void jumpButtonDown() {
        jumpButtonTime = System.nanoTime();
        if(!sprite.getPlaying()) {
            sprite.setPlaying(true);
        }
    }

    public void jumpButtonUp() {
        if(!sprite.getJumping() && !sprite.getFalling()) {
            sprite.setJumping(System.nanoTime()-jumpButtonTime);
        }
    }

    public void duckButtonDown() {
        if(!sprite.getPlaying())
        {
            sprite.setPlaying(true);
        }
        if(!sprite.getJumping() && !sprite.getFalling()) {
            sprite.setDucking(true);
        }
    }

    public void duckButtonUp() {
        sprite.setDucking(false);
    }
}
