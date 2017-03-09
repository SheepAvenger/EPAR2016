package com.softwarei.epar2016;

/**
 * Created by nathan on 3/2/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
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

    Bitmap goToMainGameMap;
    Bitmap scoreMultiplierMap;
    Bitmap unlockCharacterMap;
    Bitmap gameOverMap;

    private int backWidth;
    private int backHeight;
    Bitmap backMap;

    private int vladWidth;
    private int vladHeight;
    Bitmap vladMap;

    private int speechCols = 4;
    private int speechSpriteFrame = 0;
    private int speechWidth;
    private int speechHeight;
    Bitmap speechMap;

    private int guessedRightCols = 2;
    private int guessedRightFrame = 0;
    private int guessedRightWidth;
    private int guessedRightHeight;
    Bitmap userGuessedRightMap;
    Boolean guessedRight;

    private int guessedWrongCols = 2;
    private int guessedWrongFrame = 0;
    private int guessedWrongWidth;
    private int guessedWrongHeight;
    Bitmap userGuessedWrongMap;
    Boolean guessedWrong;

    private int machineCols = 10;
    private int machineSpriteFrame = 0;
    private int machineWidth;
    private int machineHeight;
    Bitmap machMap;
    Bitmap machineStill;

    private int scrollCols = 5;
    private int scrollSpriteFrame = 0;
    private int scrollWidth;
    private int scrollHeight;
    Bitmap scrollMap;

    //testing variables
    int testCount;
    int secondTestCount;
    Boolean spinMachine;
    Boolean scrollOptions;
    int openUserGuessInput;
    private final EditText userGuess;
    private int vladGuess;
    private int speech;
    Boolean goToGameOver;
    private int score;
    private int character_index;
    private int numScandal;
    private int level;
    private int whatDidTheUserGuess;
    private Boolean right;
    private Boolean wrong;

    VladThread(Context context, int character_index, int numScandal, int level, int score) {
        super(context);
        this.context = context;
        ourHolder = getHolder();
        this.score = score;
        this.character_index = character_index;
        this.numScandal = numScandal;
        this.level = level;
        //create thread to slow down slot machine and options animation
        gameThread = new Thread(this);

        gameOverMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_lose);
        scoreMultiplierMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_money_win);
        unlockCharacterMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_trophy_win);
        goToMainGameMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_glory_win);

        //get the bitmap for the background
        backMap = BitmapFactory.decodeResource(getResources(), R.drawable.vlad_background);
        this.backWidth = backMap.getWidth();
        this.backHeight = backMap.getHeight();

        //get the bitmap for Vlad
        vladMap = BitmapFactory.decodeResource(getResources(), R.drawable.vlad);
        this.vladWidth = vladMap.getWidth();
        this.vladHeight = vladMap.getHeight();

        //get the resources for what vlad's first speech
        speechMap = BitmapFactory.decodeResource(getResources(), R.drawable.first_speech_bubble);
        this.speechWidth = speechMap.getWidth()/speechCols;
        this.speechHeight = speechMap.getHeight();

        //get resources for the speech about the user guessing right
        userGuessedRightMap = BitmapFactory.decodeResource(getResources(), R.drawable.guess_right_speech_bubble);
        this.guessedRightWidth = userGuessedRightMap.getWidth() / guessedRightCols;
        this.guessedRightHeight = userGuessedRightMap.getHeight();
        guessedRight = false;

        //get resources for the speech about the user guessing wrong
        userGuessedWrongMap = BitmapFactory.decodeResource(getResources(), R.drawable.guess_wrong_speech_bubble);
        this.guessedWrongWidth = userGuessedWrongMap.getWidth() / guessedRightCols;
        this.guessedWrongHeight = userGuessedWrongMap.getHeight();
        guessedWrong = false;

        //get the bitmap for the slot machine
        machMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine);
        machineStill = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine_still);
        this.machineWidth = machMap.getWidth() / machineCols;
        this.machineHeight = machMap.getHeight();

        //get the bit map for the different options scrolling animation
        scrollMap = BitmapFactory.decodeResource(getResources(), R.drawable.slot_scroll);
        this.scrollWidth = scrollMap.getWidth() / scrollCols;
        this.scrollHeight = scrollMap.getHeight();

        //testing variables
        spinMachine = false;
        scrollOptions = false;
        testCount = 0;
        secondTestCount = 0;
        openUserGuessInput = 0;
        userGuess = null;
        speech = 0;
        goToGameOver = false;
        right = false;
        wrong = false;
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
        }
        Intent gameOver = new Intent(context, GameOver.class);
        gameOver.putExtra("score",score);
        context.startActivity(gameOver);
    }

    private void update() {
        //updates dialog for Vlad
        if (speech < 5) {
            if (speech < 3)
                try {
                    gameThread.sleep(500);
                    speechSpriteFrame = ++speechSpriteFrame % speechCols;
                }
                catch (InterruptedException e) {}
            else if (guessedRight) {
                try {
                    gameThread.sleep(500);
                    guessedRightFrame = ++guessedRightFrame % guessedRightCols;
                }
                catch (InterruptedException e) {}

            }
            else if (guessedWrong) {
                try {
                    gameThread.sleep(500);
                    guessedWrongFrame = ++guessedWrongFrame % guessedWrongCols;
                }
                catch (InterruptedException e) {}
            }
        }
        //updates the animation frames
        else if (spinMachine || scrollOptions) {
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

            //draw the background
            Rect bsrc = new Rect(0, 0, backWidth, backHeight);
            Rect bdst = new Rect(0, 0, GameView.WIDTH, GameView.HEIGHT);
            canvas.drawBitmap(backMap, bsrc, bdst, null);

            //draw vlad
            Rect vsrc = new Rect(0, 0, vladWidth, vladHeight);
            Rect vdst = new Rect(500, 0, 910, (int) (GameView.HEIGHT / 1.1));
            canvas.drawBitmap(vladMap, vsrc, vdst, null);

            Rect msrc = new Rect(0, 0, machineWidth, machineHeight);
            Rect mdst = new Rect(0, 210, 250, 440);
            canvas.drawBitmap(machineStill, msrc, mdst, null);
            testCount++;
            //test how long still image stays on screen
            if (right || wrong)
                spinMachine = true;


            //only draw the speech before any animation
            else if (spinMachine == false && scrollOptions == false) {
                int spsrcX = speechSpriteFrame * speechWidth;
                Rect spsrc = new Rect(spsrcX, 0, spsrcX + speechWidth, speechHeight);
                Rect spdst = new Rect(400, 50, 600, 200);
                canvas.drawBitmap(speechMap, spsrc, spdst, null);
                if (speechSpriteFrame == 3) {
                    if (right == false && wrong == false)
                        vladGuess = new Random().nextInt(10) + 1;
                    if (whatDidTheUserGuess == vladGuess)
                        right = true;
                    else
                        wrong = true;
                }
            }

            else if (spinMachine) {
                int msrcX = machineSpriteFrame * machineWidth;
                Rect mNewSrc = new Rect(msrcX, 0, msrcX + machineWidth, machineHeight);
                Rect mNewDst = new Rect(0, 210, 250, 440);
                canvas.drawBitmap(machMap, mNewSrc, mNewDst, null);
                try {
                    gameThread.sleep(100);
                } catch (InterruptedException e) {Log.e("error",""+e.getMessage());}

                if (machineSpriteFrame == 9) {
                    scrollOptions = true;
                    spinMachine = false;
                }
            }

            else if (scrollOptions) {
                canvas.drawBitmap(machineStill, msrc, mdst, null);

                //cut the scroll sprite again
                int ssrcX = scrollSpriteFrame * scrollWidth;
                Rect ssrc = new Rect(ssrcX, 0, ssrcX + scrollWidth, scrollHeight);
                Rect sdst = new Rect(60, 295, 145, 367);
                canvas.drawBitmap(scrollMap, ssrc, sdst, null);
                try {
                    gameThread.sleep(50);
                } catch (InterruptedException e) {
                    Log.e("error",""+e.getMessage());}

                secondTestCount++;

                //change secondTestCount to what ever number you think runs long enough for vlad screen;
                if (secondTestCount == 150) {
                    int winnings = new Random().nextInt(101);
                    if (right) {
                        if (winnings <= 16){
                            //give them gameover
                            canvas.drawBitmap(gameOverMap, ssrc, sdst, null);
                        }
                        else if (winnings > 16 && winnings <= 44) {
                            //give them score multiplier
                            canvas.drawBitmap(scoreMultiplierMap, ssrc, sdst, null);
                            this.score = (int) Math.ceil((double) this.score * 1.3);
                        }
                        else if (winnings > 44 && winnings <= 72) {
                            //unlock character
                            canvas.drawBitmap(unlockCharacterMap, ssrc, sdst, null);
                        }
                        else {
                            //go back to main game
                            canvas.drawBitmap(goToMainGameMap, ssrc, sdst, null);
                        }
                        scrollOptions = false;
                        secondTestCount = 0;
                        running = false;
                    }
                    else if (wrong) {
                        if (winnings <= 40){
                            //give them gameover
                            canvas.drawBitmap(gameOverMap, ssrc, sdst, null);
                        }
                        else if (winnings > 40 && winnings<=60) {
                            //give them score multiplier
                            canvas.drawBitmap(scoreMultiplierMap, ssrc, sdst, null);
                            this.score = (int) Math.ceil((double) this.score * 1.3);
                        }
                        else if (winnings > 60 && winnings <= 80) {
                            //unlock character
                            canvas.drawBitmap(unlockCharacterMap, ssrc, sdst, null);
                        }
                        else {
                            //go back to main game
                            canvas.drawBitmap(goToMainGameMap, ssrc, sdst, null);
                        }
                        try {
                            gameThread.sleep(200);
                        }
                        catch (InterruptedException e) {}
                        scrollOptions = false;
                        secondTestCount = 0;
                        running = false;
                    }
                }
            }
            /*else {
                Rect msrc = new Rect(0, 0, machineWidth, machineHeight);
                Rect mdst = new Rect(0, 210, 250, 440);
                canvas.drawBitmap(machineStill, msrc, mdst, null);
                testCount++;
                //test how long still image stays on screen
                if (testCount == 100){
                    spinMachine = true;
                    testCount = 0;
                }
            }*/


            ourHolder.unlockCanvasAndPost(canvas);
            speech++;

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
