package com.softwarei.epar2016;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;

/**
 * Created by Michael on 2/26/17.
 */

public class DealWithVlad extends AppCompatActivity{
    private Bitmap slotMachine;
    private Bitmap slotScroll;
    private Bitmap slotTrophy;
    private Bitmap slotMoney;
    private Bitmap slotGlory;
    private Bitmap slotGameOver;
    private Bitmap vlad[]; //need to add speech bubbles to the sprite vlad
    private Sprite sprite;
    public static Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int score;
    private int FPS = 60;




    //character names from a txt file

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_with_vlad);

        canvas = new Canvas();

        sprite = new Sprite(14, 10, 5, this);


       // slotMachine = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine);
        //slotScroll = BitmapFactory.decodeResource(getResources(), R.drawable.slot_scroll);
        //slotTrophy = BitmapFactory.decodeResource(getResources(), R.drawable.slot_trophy_win);
        //slotMoney = BitmapFactory.decodeResource(getResources(), R.drawable.slot_money_win);
        //slotGlory = BitmapFactory.decodeResource(getResources(), R.drawable.slot_glory_win);
        //slotGameOver = BitmapFactory.decodeResource(getResources(), R.drawable.slot_lose);

        //store new vlad images

        //score = Sprite.getScore();
    }

    @Override
    protected void onStart() {
        super.onStart();

        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(true) {
            this.sprite.update();
            this.sprite.draw(canvas);
        }
    }

    /*public void Power() {

    }

    public void Wealth() {

    }
    public void Glory() {

    }*/
}
