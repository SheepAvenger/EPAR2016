package com.softwarei.epar2016;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;

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
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        backGround = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.b_flag));
        sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.c_abe_run), 2,
                BitmapFactory.decodeResource(getResources(), R.drawable.c_abe_duck2), 2);
        gameLoop = new GameLoop(getHolder(), this);

        gameLoop.setRunning(true);
        gameLoop.start();
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

    public void update() {
        if (sprite.getPlaying()){
            backGround.update();
            sprite.update();
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null){
            backGround.draw(canvas);
            sprite.draw(canvas);
        }
    }
}
