package com.softwarei.epar2016;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static int MOVESPEED = -6;
    private Level level;
    private long obstacleTime, jumpButtonTime, levelTime, pauseTime;
    private GameLoop gameLoop;
    private Background background;
    private Sprite sprite;
    private ArrayList<Obstacle> obstacles;
    private Bitmap obstacle, obstacle2, scandal, noScandal;
    private int scandalCount, index, score, hitCount, level_index;
    private Scores scores;
    private static Context ctx;
    private Random rand;

    public GameView(Context context, int index, int levels, int scandal, int score) {
        super(context);
        ctx = context;
        getHolder().addCallback(this);
        setFocusable(true);
        this.index = index;
        scandalCount = scandal;
        this.score = score;
        level_index = levels;

        level = new Level(context, levels);
        rand = new Random();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        obstacleTime = System.nanoTime();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        /*
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
        */
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        background = new Background(level.getBackground());
        sprite = new Sprite(index, 2, 2, ctx,score);

        obstacles = new ArrayList<Obstacle>();
        obstacle = BitmapFactory.decodeResource(getResources(), R.drawable.o_donkey);
        obstacle2 = BitmapFactory.decodeResource(getResources(), R.drawable.o_elephant);
        
        scandal = BitmapFactory.decodeResource(getResources(), R.drawable.scandal);
        noScandal = BitmapFactory.decodeResource(getResources(), R.drawable.no_scandal);

        gameLoop = new GameLoop(getHolder(), this);
        gameLoop.setRunning(true);
        gameLoop.start();
        //sprite.setPlaying(true);
        //levelTime = System.nanoTime();
    }

    public void jumpButtonDown() {
        jumpButtonTime = System.nanoTime();
        if(!sprite.getPlaying()) {
            sprite.setPlaying(true);
            levelTime = System.nanoTime();
        }
    }

    public void jumpButtonUp() {
        if(!sprite.getJumping()) {
            sprite.setJumping(System.nanoTime()-jumpButtonTime);
        }
    }

    public void duckButtonDown() {
        if(!sprite.getPlaying()) {
            sprite.setPlaying(true);
            levelTime = System.nanoTime();
        }
        if(!sprite.getJumping()) {
            sprite.setDucking(true);
        }
    }

    public void duckButtonUp() {
        sprite.setDucking(false);
    }
    
    public void pauseButtonUp() {
        gameLoop.setRunning(false);
        pauseTime = System.nanoTime();
    }

    public void resumeButtonUp() {
        gameLoop.setRunning(true);
        gameLoop.start();
        levelTime += System.nanoTime() - pauseTime;
    }


    public boolean collision(Obstacle o, Sprite s) {
        return Rect.intersects(o.getRectangle(), s.getRectangle());
    }

    public void drawText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        //canvas.drawText("Scandal: " + scandalCount, 10, 30, paint);
        canvas.drawText("Score: " + sprite.getScore(), 350, 30, paint);
        canvas.drawText("Level: " + level.getLevel(), 350, 478, paint);
        if(!sprite.getPlaying()) {
            Paint paint1 = new Paint();
            paint1.setColor(Color.WHITE);
            paint1.setTextSize(40);
            canvas.drawText("Tap JUMP or DUCK to start", WIDTH / 3, HEIGHT / 2, paint1);

        }
    }

    public void drawScandal(Canvas canvas) {
        for(int i = 3; i > scandalCount; i--) {
            canvas.drawBitmap(noScandal, i * 80 - 70, 10, null);
        }
        for(int i = 0; i < scandalCount; i++) {
            canvas.drawBitmap(scandal, i * 80 + 10, 10, null);
        }
    }

    public void update() {
        if (sprite.getPlaying()){
            if((int)((System.nanoTime() - levelTime) / 1000000000) > 2 * (level.getLevel() + 1) && level.getLevel() < 9) {
                MOVESPEED--;
                level.setLevel();
                background = new Background(level.getBackground());
                level_index = level.getLevel();
            }

            background.update();
            sprite.update();
            score = sprite.getScore();

            long timeElapsed = (System.nanoTime() - obstacleTime)/1000000;
            if(timeElapsed > (3000 - sprite.getScore())){
                if(timeElapsed % 2 == 0) {
                    obstacles.add(new Obstacle(obstacle, WIDTH + 10, HEIGHT - obstacle.getHeight() - 15));
                }
                else {
                    int max = HEIGHT - obstacle2.getHeight() - 15;
                    int min = obstacle2.getHeight();
                    int posY = rand.nextInt((max - min) + 1) + min;
                    obstacles.add(new Obstacle(obstacle2, WIDTH + 10, posY));
                    //obstacles.add(new Obstacle(obstacle2, WIDTH + 10, HEIGHT - obstacle2.getHeight() - 160));
                }
                obstacleTime = System.nanoTime();
            }

            for(int i = 0; i < obstacles.size(); i++) {
                obstacles.get(i).update();

                if(collision(obstacles.get(i),sprite)) {
                    sprite.setCollision();
                    obstacles.remove(i);
                    hitCount++;
                    break;
                }

                if(obstacles.get(i).getX() < -100) {
                    obstacles.remove(i);
                    break;
                }
            }

            if(hitCount >= 3 && !sprite.getCollision()) {
                gameLoop.setRunning(false);
                scandalCount++;
                Intent scandal = new Intent(GameView.ctx, Scandal.class);
                scandal.putExtra("character",index);
                scandal.putExtra("scandal",scandalCount);
                scandal.putExtra("level",level_index);
                scandal.putExtra("score",score);
                ctx.startActivity(scandal);
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
