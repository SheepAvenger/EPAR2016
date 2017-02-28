package com.softwarei.epar2016;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int MOVESPEED = -5;

    private long jumpButtonTime;
    private GameLoop gameLoop;
    private Background background;
    private Sprite sprite;
    private int scandalCount;

    private int screenWidth;
    private int screenHeight;
    
    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.level0));
        sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.c_washington_run), 2,
                BitmapFactory.decodeResource(getResources(), R.drawable.c_washington_duck), 2,
                screenWidth, screenHeight);
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

    public boolean collision(Obstacle o, Sprite s) {
        if(Rect.intersects(o.getRectangle(), s.getRectangle())) {
            return true;
        }
        return false;
    }

    public void update() {
        if (sprite.getPlaying()){
            background.update();
            sprite.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        
        if (canvas != null) {
            final int savedState = canvas.save();
            
            background.draw(canvas);
            sprite.draw(canvas);

            canvas.restoreToCount(savedState);
        }
    }
}
