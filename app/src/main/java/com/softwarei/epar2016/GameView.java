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
import android.graphics.Bitmap;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static int MOVESPEED = -8;
    private Level level;
    private String levelString;
    private CharacterSelection cS;
    private long timing;
    private long jumpButtonTime;
    private GameLoop gameLoop;
    private Background background;
    private Sprite sprite;
    private ArrayList<Obstacle> obstacles;
    private Bitmap obstacle;
    private Bitmap scandal;
    private int scandalCount;
    private Scores scores;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        level = new Level(context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        timing = System.nanoTime();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        gameLoop.setRunning(false);
        while(retry) {
            try {
                gameLoop.join();
                retry = false;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){


        background = new Background(level.getBackground());
        level.setLevel();
        sprite = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.c_abe_run), 2,
                BitmapFactory.decodeResource(getResources(), R.drawable.c_abe_duck2), 2);

        obstacles = new ArrayList<Obstacle>();
        obstacle = BitmapFactory.decodeResource(getResources(), R.drawable.o_donkey);
        scandal = BitmapFactory.decodeResource(getResources(), R.drawable.scandal);

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
        //canvas.drawText("Scandal: " + scandalCount, 10, 30, paint);
        canvas.drawText("Score: " + sprite.getScore(), 300, 30, paint);
        //canvas.drawText("Best: " + best, 600, 30, paint);
        if(!sprite.getPlaying()) {
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(40);
            canvas.drawText("Tap JUMP or DUCK to start", WIDTH / 3, HEIGHT / 2, paint1);

        }
    }

    public void drawScandal(Canvas canvas) {
        for(int i = 0; i < scandalCount; i++) {
            canvas.drawBitmap(scandal, 10 + i * 80, 10, null);
        }
    }

    public void update() {
        if (sprite.getPlaying()){
            background.update();
            sprite.update();

            long timeElapsed = (System.nanoTime()-timing)/1000000;
            if(timeElapsed >(5000 - sprite.getScore()/4)){
                obstacles.add(new Obstacle(obstacle,WIDTH + 10, HEIGHT - obstacle.getHeight() - 15));
                timing = System.nanoTime();
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
            drawScandal(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
