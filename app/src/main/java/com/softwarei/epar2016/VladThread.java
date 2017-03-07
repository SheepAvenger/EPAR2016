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
import android.widget.EditText;

import java.util.Random;

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


    private int speechCols = 4;
    private int speechSpriteFrame = 0;
    private int speechWidth;
    private int speechHeight;
    Bitmap speechMap;

    private int scrollCols = 5;
    private int scrollSpriteFrame = 0;
    private int scrollWidth;
    private int scrollHeight;
    Bitmap scrollMap;

    private GameOver gameOver;

    //testing variables
    int testCount;
    int secondTestCount;
    Boolean spinMachine;
    Boolean scrollOptions;
    int openUserGuessInput;
    private final EditText userGuess;
    int vladGuess;
    Boolean speech;
    Boolean leaveGame;
    DealWithVlad dealWithVlad;

    VladThread(Context context) {
        super(context);
        this.context = context;
        ourHolder = getHolder();

        //get the bitmap for the background
        backMap = BitmapFactory.decodeResource(getResources(), R.drawable.vlad_background);
        this.backWidth = backMap.getWidth();
        this.backHeight = backMap.getHeight();

        //get the bitmap for Vlad
        vladMap = BitmapFactory.decodeResource(getResources(), R.drawable.vlad);
        this.vladWidth = vladMap.getWidth();
        this.vladHeight = vladMap.getHeight();

        //get the bitmap for the slot machine
        machMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine);
        machineStill = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine_still);
        this.machineWidth = machMap.getWidth() / machineCols;
        this.machineHeight = machMap.getHeight();


        //get the bit map for the different options scrolling animation
        scrollMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_scroll);
        this.scrollWidth = scrollMap.getWidth() / scrollCols;
        this.scrollHeight = scrollMap.getHeight();


        //get the bitmap for what vlad is saying
        speechMap = BitmapFactory.decodeResource(getResources(), R.drawable.first_speech_bubble);
        this.speechWidth = speechMap.getWidth()/speechCols;
        this.speechHeight = speechMap.getHeight();

        //create thread to slow down slot machine and options animation
        gameThread = new Thread(this);

        //testing variables
        spinMachine = false;
        scrollOptions = false;
        testCount = 0;
        secondTestCount = 0;
        openUserGuessInput = 0;
        userGuess = null;
        vladGuess = new Random().nextInt(10) + 1;
        speech = false;
        leaveGame = true;
        dealWithVlad = new DealWithVlad();
        //need to figure out a way to end thread and pass to gameOver.
        gameOver = new GameOver();
    }

    @Override
    public void run() {

        //loop to continue drawing images
        while (running) {
            long startFrameTime = System.currentTimeMillis();
            draw();
            update();
            long timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
            if (leaveGame) {
                dealWithVlad.onPause();
            }
        }
    }

    private void update() {

        //updates the animation frames
        if (spinMachine || scrollOptions) {
            machineSpriteFrame = ++machineSpriteFrame % machineCols;
            scrollSpriteFrame = ++scrollSpriteFrame % scrollCols;
        }
        //updates dialog for Vlad
        if (speechSpriteFrame != 3 && speech) {
            speechSpriteFrame = ++speechSpriteFrame % speechCols;
        }

    }
    private void draw() {

        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            final float scaleFactorX = getWidth() / (GameView.WIDTH * 1.f);
            final float scaleFactorY = getHeight() / (GameView.HEIGHT * 1.f);
            canvas.scale(scaleFactorX, scaleFactorY);

            canvas.drawColor(Color.LTGRAY);

            //draw the background
            Rect bsrc = new Rect(0, 0, backWidth, backHeight);
            Rect bdst = new Rect(0, 0, GameView.WIDTH, GameView.HEIGHT);
            canvas.drawBitmap(backMap, bsrc, bdst, null);

            //draw vlad
            Rect vsrc = new Rect(0, 0, vladWidth, vladHeight);
            Rect vdst = new Rect(500, 0, 910, (int) (GameView.HEIGHT / 1.1));
            canvas.drawBitmap(vladMap, vsrc, vdst, null);

            //only draw the speech before any animation
            if (spinMachine == false && scrollOptions == false) {
                int spsrcX = speechSpriteFrame * speechWidth;
                Rect spsrc = new Rect(spsrcX, 0, spsrcX + speechWidth, speechHeight);
                Rect spdst = new Rect(400, 50, 600, 200);
                canvas.drawBitmap(speechMap, spsrc, spdst, null);
                try {
                    gameThread.sleep(100);
                } catch (InterruptedException e) {}
                speech = true;
            }

            if (spinMachine) {
                int msrcX = machineSpriteFrame * machineWidth;
                Rect msrc = new Rect(msrcX, 0, msrcX + machineWidth, machineHeight);
                Rect mdst = new Rect(0, 210, 250, 440);
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
                Rect msrc = new Rect(0, 0, machineWidth, machineHeight);
                Rect mdst = new Rect(0, 210, 250, 440);
                canvas.drawBitmap(machineStill, msrc, mdst, null);

                int ssrcX = scrollSpriteFrame * scrollWidth;
                Rect ssrc = new Rect(ssrcX, 0, ssrcX + scrollWidth, scrollHeight);
                Rect sdst = new Rect(60, 295, 145, 367);
                canvas.drawBitmap(scrollMap, ssrc, sdst, null);
                try {
                    gameThread.sleep(50);
                } catch (InterruptedException e) {}

                secondTestCount++;

                if (secondTestCount == 500) {
                    scrollOptions = false;
                    secondTestCount = 0;
                    leaveGame = true;
                }
            }

            else {
                Rect msrc = new Rect(0, 0, machineWidth, machineHeight);
                Rect mdst = new Rect(0, 210, 250, 440);
                canvas.drawBitmap(machineStill, msrc, mdst, null);
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
