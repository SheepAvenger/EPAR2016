package com.softwarei.epar2016;

/**
 * Created by nathan on 3/2/2017.
 */

import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

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

    private int scrollCols = 5;
    private int scrollSpriteFrame = 0;
    private int scrollWidth;
    private int scrollHeight;
    Bitmap scrollMap;

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
        this.machineWidth = machMap.getWidth() / machineCols;
        this.machineHeight = machMap.getHeight();

        scrollMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_scroll);
        this.scrollWidth = scrollMap.getWidth() / scrollCols;
        this.scrollHeight = scrollMap.getHeight();
    }

    @Override
    public void run() {
        while (running) {
            long startFrameTime = System.currentTimeMillis();

            update();
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

            final float scaleFactorX = getWidth()/(GameView.WIDTH*1.f);
            final float scaleFactorY = getHeight()/(GameView.HEIGHT*1.f);
            canvas.scale(scaleFactorX, scaleFactorY);

            canvas.drawColor(Color.LTGRAY);

            Rect bsrc = new Rect(0, 0, backWidth, backHeight);
            Rect bdst = new Rect(0, 0, GameView.WIDTH, GameView.HEIGHT/2);
            canvas.drawBitmap(backMap, bsrc, bdst, null);

            Rect vsrc = new Rect(0, 0, vladWidth, vladHeight);
            Rect vdst = new Rect(450, 40, 750, 240);
            canvas.drawBitmap(vladMap, vsrc, vdst, null);

            int msrcX = machineSpriteFrame * machineWidth;
            Rect msrc = new Rect(msrcX, 0, msrcX + machineWidth, machineHeight);
            Rect mdst = new Rect(50, 70, 350, 230);
            canvas.drawBitmap(machMap, msrc, mdst, null);

            int ssrcX = scrollSpriteFrame * scrollWidth;
            Rect ssrc = new Rect(ssrcX, 0, ssrcX + scrollWidth, scrollHeight);
            Rect sdst = new Rect(135, 125, 205, 175);
            canvas.drawBitmap(scrollMap, ssrc, sdst, null);

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {}
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
