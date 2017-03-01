package com.softwarei.epar2016;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    // branch_test
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static int MOVESPEED = -5;
    int level;
    private long gameStartTime;
    private long jumpButtonTime;
    private GameLoop gameLoop;
    private Background background;
    private Sprite sprite;
    private ArrayList<Obstacle> obstacles;
    private int scandalCount;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        gameStartTime = System.nanoTime();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000) {
            counter++;
            try{gameLoop.setRunning(false);
                gameLoop.join();
                retry = false;
                gameLoop = null;

            }catch(InterruptedException e){e.printStackTrace();}

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.level0));
        sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.c_washington_run), 2,
                BitmapFactory.decodeResource(getResources(), R.drawable.c_washington_duck), 2);
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

            long timeElapsed = (System.nanoTime()-gameStartTime)/1000000;
            if(timeElapsed >(2000 - sprite.getScore()/4)){
                obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.o_donkey),WIDTH + 10, HEIGHT/2));
                gameStartTime = System.nanoTime();
            }

            //loop through every missile and check collision and remove
            for(int i = 0; i< obstacles.size(); i++)
            {
                //update missile
                obstacles.get(i).update();

                if(collision(obstacles.get(i),sprite))
                {
                    sprite.setCollision();

                    obstacles.remove(i);
                    scandalCount++;



//                    break;
                }
                if(scandalCount >= 3 && !sprite.getCollision()) {
                    sprite.setPlaying(false);
                    scandalCount = 0;
                    sprite.setRecovery();
                }
                //remove missile if it is way off the screen
                if(obstacles.get(i).getX()<-100)
                {
                    obstacles.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            sprite.draw(canvas);

            canvas.restoreToCount(savedState);
        }
    }
}
