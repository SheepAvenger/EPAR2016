package com.softwarei.epar2016;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static int MOVESPEED = -5;
    private int level;
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
        sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.c_abe_run), 2,
                BitmapFactory.decodeResource(getResources(), R.drawable.c_abe_duck2), 2);
        obstacles = new ArrayList<Obstacle>();
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

    public void drawText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Scandal: " + scandalCount, 10, 30, paint);
        canvas.drawText("Score: " + sprite.getScore(), 300, 30, paint);
        //canvas.drawText("Best: " + best, 600, 10, paint);
        if(!sprite.getPlaying()) {
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(40);
            canvas.drawText("Tap JUMP or DUCK to start", WIDTH / 3, HEIGHT / 2, paint1);

        }
    }

    public void update() {
        if (sprite.getPlaying()){
            background.update();
            sprite.update();

            long timeElapsed = (System.nanoTime()-gameStartTime)/1000000;
            if(timeElapsed >(5000 - sprite.getScore()/4)){
                obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(),R.drawable.o_donkey),WIDTH + 10, HEIGHT - HEIGHT/4));
                gameStartTime = System.nanoTime();
            }

            for(int i = 0; i< obstacles.size(); i++) {
                obstacles.get(i).update();

                if(collision(obstacles.get(i),sprite)) {
                    sprite.setCollision();

                    obstacles.remove(i);
                    scandalCount++;



                }
                if(scandalCount >= 3 && !sprite.getCollision()) {
                    sprite.setPlaying(false);
                    scandalCount = 0;
                    sprite.setRecovery();
                }

                if(obstacles.get(i).getX()<-100) {
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
            for(Obstacle o: obstacles) {
                o.draw(canvas);
            }
            drawText(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
