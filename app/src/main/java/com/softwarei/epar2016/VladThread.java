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
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class VladThread extends SurfaceView implements Runnable {

    private volatile boolean running;
    private Thread gameThread = null;
    private Canvas canvas;
    private RelativeLayout layout = (RelativeLayout) findViewById(R.id.deal_with_vlad);
    private SurfaceHolder ourHolder;
    private DealWithVlad dealWithVlad;

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
    protected int speechSpriteFrame = 0;
    private int speechWidth;
    private int speechHeight;
    Bitmap speechMap;

    private int guessedRightCols = 3;
    private int guessedRightFrame = 0;
    private int guessedRightWidth;
    private int guessedRightHeight;
    Bitmap userGuessedRightMap;
    Boolean guessedRight;

    private int guessedWrongCols = 3;
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
    boolean runSpeech;
    EditText getUserGuess;

    private int numScandal;
    private int character_index;
    public int score;
    private int level;
    private int index;
    private int speed;
    private int delay;
    private int[] position;
    private boolean recovery;
    private boolean newGame = false;

    int whatDidTheUserGuess;
    private boolean right;
    private boolean wrong;

    File path;
    File unlockable;
    FileOutputStream outputStreamWriter;

    FileInputStream is;
    BufferedReader reader;

    private String kennedy = "0";
    private String washington = "0";

    VladThread(Context context, int character_index, int numScandal, int level, int score, int index, int speed, int[] position, int delay, boolean recovery) {
        super(context);
        this.context = context;
        ourHolder = getHolder();

        this.character_index = character_index;
        this.numScandal = numScandal;
        this.level = level;
        this.score = score;
        this.index = index;
        this.speed = speed;
        this.position = position;
        this.delay = delay;
        this.recovery = recovery;

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
        runSpeech = false;

        try {
            path = this.context.getFilesDir();
            unlockable = new File(path, "characters.txt");
            outputStreamWriter = new FileOutputStream(unlockable);
            is = new FileInputStream(unlockable);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        } catch (FileNotFoundException e) {}

        getUserGuess = (EditText) findViewById(R.id.userGuess);

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
        try {
            gameThread.sleep(3500);
        }
        catch (InterruptedException e) {}
        if(!newGame)
        {
            context.stopService(new Intent(context.getApplicationContext(), MusicPlayer.class));
            Intent music = new Intent(context.getApplicationContext(), MusicPlayer.class);
            music.putExtra("index", 2);
            context.startService(music);

            Intent gameOver = new Intent(context, GameOver.class);
            gameOver.putExtra("score",score);
            context.startActivity(gameOver);
        }
        else
        {
            context.stopService(new Intent(context.getApplicationContext(), MusicPlayer.class));
            Intent music = new Intent(context.getApplicationContext(), MusicPlayer.class);
            music.putExtra("index", 3);
            context.startService(music);


            Intent Main = new Intent(context, MainGame.class);
            Main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            Main.putExtra("character",character_index);
            Main.putExtra("scandal",numScandal--);
            Main.putExtra("level",level);
            Main.putExtra("score",score);
            Main.putExtra("speed",speed);
            Main.putExtra("recovery",recovery);
            Main.putExtra("position",position);
            Main.putExtra("delay",delay);
            Main.putExtra("vlad", 1);
            context.startActivity(Main);
        }

    }

    private void update() {
        //updates dialog for Vlad
        if (speech < 5 && runSpeech) {
            if (speech <= 3)
                try {
                    //change to higher number
                    gameThread.sleep(3500);
                    speechSpriteFrame = ++speechSpriteFrame % speechCols;
                }
                catch (InterruptedException e) {}
            else if (guessedRight) {
                try {
                    gameThread.sleep(3500);
                    guessedRightFrame = ++guessedRightFrame % guessedRightCols;
                    speechSpriteFrame = 4;
                }
                catch (InterruptedException e) {}

            }
            else if (guessedWrong) {
                try {
                    gameThread.sleep(3500);
                    guessedWrongFrame = ++guessedWrongFrame % guessedWrongCols;
                    speechSpriteFrame = 4;
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

            //test how long still image stays on screen

                int spsrcX = speechSpriteFrame * speechWidth;
                Rect spsrc = new Rect(spsrcX, 0, spsrcX + speechWidth, speechHeight);
                Rect spdst = new Rect(400, 50, 600, 200);
                canvas.drawBitmap(speechMap, spsrc, spdst, null);
            if (speech < 5)
                speech++;

            if (speechSpriteFrame == 3) {
                try {
                    gameThread.sleep(3500);
                    if (right == false && wrong == false && right != true && wrong != true) {
                        vladGuess = new Random().nextInt(10) + 1;
                        System.out.println("Vlad " + vladGuess + "\n");
                        System.out.println(whatDidTheUserGuess);
                        if (whatDidTheUserGuess == vladGuess) {
                            right = true;
                            spinMachine = true;
                            guessedRight = true;
                        } else {
                            wrong = true;
                            spinMachine = true;
                            guessedWrong = true;
                        }
                    }
                } catch (InterruptedException e) {}
            }

            if (speechSpriteFrame == 4) {
                Rect new_spdst = new Rect(400, 50, 600, 200);
                if (guessedRight) {
                    int new_spsrcX = guessedRightFrame * guessedRightWidth;
                    Rect new_spsrc = new Rect(new_spsrcX, 0, new_spsrcX + guessedRightWidth, guessedRightHeight);
                    canvas.drawBitmap(userGuessedRightMap, new_spsrc, new_spdst, null);
                }
                else {
                    int new_spsrcX = guessedWrongFrame * guessedWrongWidth;
                    Rect new_spsrc = new Rect(new_spsrcX, 0, new_spsrcX + guessedWrongWidth, guessedWrongHeight);
                    canvas.drawBitmap(userGuessedWrongMap, new_spsrc, new_spdst, null);
                }
            }


            if (spinMachine) {
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
                Rect msrc = new Rect(0, 0, machineWidth, machineHeight);
                Rect mdst = new Rect(0, 210, 250, 440);
                canvas.drawBitmap(machineStill, msrc, mdst, null);

                //cut the scroll sprite again
                int ssrcX = scrollSpriteFrame * scrollWidth;
                Rect ssrc = new Rect(ssrcX, 0, ssrcX + scrollWidth, scrollHeight);
                Rect sdst = new Rect(65, 290, 150, 365);

                Rect new_ssrc = new Rect(0, 0, scrollWidth, scrollHeight);
                Rect new_sdst = new Rect(65, 290, 150, 365);
                canvas.drawBitmap(scrollMap, ssrc, sdst, null);
                try {
                    gameThread.sleep(50);
                } catch (InterruptedException e) {
                    Log.e("error",""+e.getMessage());}

                secondTestCount++;
                //change secondTestCount to what ever number you think runs long enough for vlad screen;
                if (secondTestCount == 75) {
                    int winnings = new Random().nextInt(101);
                    if (right) {
                        if (winnings <= 16){
                            //give them gameover
                            canvas.drawBitmap(gameOverMap, new_ssrc, new_sdst, null);
                        }
                        else if (winnings > 16 && winnings <= 44) {
                            //give them score multiplier
                            canvas.drawBitmap(scoreMultiplierMap, new_ssrc, new_sdst, null);
                            this.score = (int) Math.ceil((double) this.score * 1.3);
                        }
                        else if (winnings > 44 && winnings <= 72) {
                            //unlock character
                            canvas.drawBitmap(unlockCharacterMap, new_ssrc, new_sdst, null);
                            int characterUnlock = new Random().nextInt(2);
                            File path = context.getApplicationContext().getFilesDir();
                            File unlockable = new File(path, "characters.txt");
                            if(is!= null) {
                                try {
                                FileInputStream is = new FileInputStream(unlockable);
                                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                                    kennedy = reader.readLine();
                                    washington = reader.readLine();
                                    is.close();
                                } catch (IOException e) {Log.e("error",""+e.getMessage());}
                            }
                            if (characterUnlock == 0 && washington == "0" && kennedy != "1") {
                                try {
                                    FileOutputStream outputStreamWriter = new FileOutputStream(unlockable);
                                    outputStreamWriter.write("0".getBytes()); //kennedy
                                    outputStreamWriter.write("1".getBytes()); //washington
                                    outputStreamWriter.close();
                                } catch (IOException e) { }
                            }
                            else if (characterUnlock == 0 && washington != "1" && kennedy == "0"){
                                try {
                                    FileOutputStream outputStreamWriter = new FileOutputStream(unlockable);
                                    outputStreamWriter.write("1".getBytes()); //kennedy
                                    outputStreamWriter.write("0".getBytes()); //washington
                                    outputStreamWriter.close();
                                } catch (IOException e) { }
                            }
                        }
                        else {
                            //go back to main game
                            canvas.drawBitmap(goToMainGameMap, new_ssrc, new_sdst, null);
                            //dealWithVlad.secondAttempt = true;
                            try {
                                gameThread.sleep(5000);
                            }
                            catch (InterruptedException e) {}
                            running = false;
                            newGame = true;

                        }
                        try {
                            gameThread.sleep(5000);
                        }
                        catch (InterruptedException e) {}
                        scrollOptions = false;
                        secondTestCount = 0;
                        running = false;
                    }
                    else if (wrong) {
                        if (winnings <= 40){
                            //give them gameover
                            canvas.drawBitmap(gameOverMap, new_ssrc, new_sdst, null);
                        }
                        else if (winnings > 40 && winnings<=60) {
                            //give them score multiplier
                            canvas.drawBitmap(scoreMultiplierMap, new_ssrc, new_sdst, null);
                            this.score = (int) Math.ceil((double) this.score * 1.3);
                        }
                        else if (winnings > 60 && winnings <= 80) {
                            //unlock character
                            canvas.drawBitmap(unlockCharacterMap, new_ssrc, new_sdst, null);
                            int characterUnlock = new Random().nextInt(9);
                             File path = context.getApplicationContext().getFilesDir();
                            File unlockable = new File(path, "character.txt");
                            long length = unlockable.length();
                             if(is!= null) {
                                try {
                                FileInputStream is = new FileInputStream(unlockable);
                                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                                    kennedy = reader.readLine();
                                    washington = reader.readLine();
                                    is.close();
                                } catch (IOException e) {Log.e("error",""+e.getMessage());}
                            }
                            if (characterUnlock > 5 && washington.equals("0") && !kennedy.equals("1")) {
                                try {
                                    FileOutputStream outputStreamWriter = new FileOutputStream(unlockable);
                                    outputStreamWriter.write("0".getBytes()); //kennedy
                                    outputStreamWriter.write("1".getBytes()); //washington
                                    outputStreamWriter.close();
                                } catch (IOException e) { }
                            }
                            else if (characterUnlock > 5 && !washington.equals("1") && kennedy.equals("0")){
                                try {
                                    FileOutputStream outputStreamWriter = new FileOutputStream(unlockable);
                                    outputStreamWriter.write("1".getBytes()); //kennedy
                                    outputStreamWriter.write("0".getBytes()); //washington
                                    outputStreamWriter.close();
                                } catch (IOException e) { }
                            }
                        }
                        else {
                            //go back to main game
                            canvas.drawBitmap(goToMainGameMap, new_ssrc, new_sdst, null);
                            //dealWithVlad.secondAttempt = true;
                            try {
                                gameThread.sleep(5000);
                            }
                            catch (InterruptedException e) {}
                            running = false;
                            newGame = true;
                        }
                        scrollOptions = false;
                        secondTestCount = 0;
                        running = false;
                    }
                }
            }
            else {
                Rect msrc = new Rect(0, 0, machineWidth, machineHeight);
                Rect mdst = new Rect(0, 210, 250, 440);
                canvas.drawBitmap(machineStill, msrc, mdst, null);
                testCount++;
            }


            ourHolder.unlockCanvasAndPost(canvas);
            runSpeech = true;

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
