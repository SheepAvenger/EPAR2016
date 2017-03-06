package com.softwarei.epar2016;

/**
 * Created by nathan on 3/2/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class VladThread extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;

    private Canvas canvas;
    private SurfaceHolder ourHolder;

    Context context;

    long fps = 60;

    private int backWidth;
    private int backHeight;
    Bitmap backMap;

    private int vladWidth;
    private int vladHeight;
    Bitmap vladMap;

    private int machineCols = 10;
    private int machineSpriteFrame = 0;
    private int machineWidth;
    private int machineHeight;
    Bitmap machMap;
    Bitmap machineStill;
    Boolean spinMachine;

    int testCount;
    int secondTestCount;

    private int scrollCols = 5;
    private int scrollSpriteFrame = 0;
    private int scrollWidth;
    private int scrollHeight;
    Bitmap scrollMap;
    Boolean scrollOptions;

    private GameOver gameOver;

    VladThread(Context context) {
        super(context);
        this.context = context;
        ourHolder = getHolder();

        backMap = BitmapFactory.decodeResource(getResources(), R.drawable.vlad_background);
        this.backWidth = backMap.getWidth();
        this.backHeight = backMap.getHeight();

        vladMap = BitmapFactory.decodeResource(getResources(), R.drawable.vlad);
        this.vladWidth = vladMap.getWidth();
        this.vladHeight = vladMap.getHeight();

        machMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine);
        machineStill = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine_still);
        this.machineWidth = machMap.getWidth() / machineCols;
        this.machineHeight = machMap.getHeight();
        spinMachine = false;

        scrollMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_scroll);
        this.scrollWidth = scrollMap.getWidth() / scrollCols;
        this.scrollHeight = scrollMap.getHeight();
        scrollOptions = false;

        gameThread = new Thread(this);

        testCount = 0;
        secondTestCount = 0;

        gameOver = new GameOver();
    }

    @Override
    public void run() {

        while (running) {
            long startFrameTime = System.currentTimeMillis();
            if (spinMachine || scrollOptions) {
                update();
            }
            draw();
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }

    private void update() {
        if (running) {
            machineSpriteFrame = ++machineSpriteFrame % machineCols;
            scrollSpriteFrame = ++scrollSpriteFrame % scrollCols;
        }
    }
    private void draw() {

        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            final float scaleFactorX = getWidth() / (GameView.WIDTH * 1.f);
            final float scaleFactorY = getHeight() / (GameView.HEIGHT * 1.f);
            canvas.scale(scaleFactorX, scaleFactorY);

            canvas.drawColor(Color.LTGRAY);

            Rect bsrc = new Rect(0, 0, backWidth, backHeight);
            Rect bdst = new Rect(0, 0, GameView.WIDTH, GameView.HEIGHT);
            canvas.drawBitmap(backMap, bsrc, bdst, null);


            Rect vsrc = new Rect(0, 0, vladWidth, vladHeight);
            Rect vdst = new Rect(500, 0, 910, (int) (GameView.HEIGHT / 1.1));
            canvas.drawBitmap(vladMap, vsrc, vdst, null);

            if (spinMachine) {
                int msrcX = machineSpriteFrame * machineWidth;
                Rect msrc = new Rect(msrcX, 0, msrcX + machineWidth, machineHeight);
                Rect mdst = new Rect(50, 70, 350, 230);
                canvas.drawBitmap(machMap, msrc, mdst, null);
                try {
                    gameThread.sleep(100);
                } catch (InterruptedException e) {}

                if (machineSpriteFrame == 9) {
                    scrollOptions = true;
                    spinMachine = false;
                }
            }
            else if (scrollOptions) {
                canvas.drawBitmap(machineStill, 0, 110, null);

                int ssrcX = scrollSpriteFrame * scrollWidth;
                Rect ssrc = new Rect(ssrcX, 0, ssrcX + scrollWidth, scrollHeight);
                Rect sdst = new Rect(100, 75, 280, 175);

                canvas.drawBitmap(scrollMap, ssrc, sdst, null);
                try {
                    gameThread.sleep(50);
                } catch (InterruptedException e) {}

                secondTestCount++;

                if (secondTestCount == 1000) {
                    scrollOptions = false;
                    secondTestCount = 0;
                }
            }
            else {
                canvas.drawBitmap(machineStill, 0, 110, null);
                testCount++;
                //test how long still image stays on screen
                if (testCount == 100){
                    spinMachine = true;
                    testCount = 0;
                }
            }


            ourHolder.unlockCanvasAndPost(canvas);
        }

    }


    // Clean up our thread if the game is stopped
    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    // Make a new thread and start it
    // Execution moves to our run method
    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
