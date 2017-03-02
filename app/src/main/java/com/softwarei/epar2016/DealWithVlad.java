package com.softwarei.epar2016;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    private int score;


    //character names from a txt file

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deal_with_vlad);

        slotMachine = BitmapFactory.decodeResource(getResources(), R.drawable.slot_machine);
        slotScroll = BitmapFactory.decodeResource(getResources(), R.drawable.slot_scroll);

        sprite = new Sprite(14, 10, 5, this);

        slotTrophy = BitmapFactory.decodeResource(getResources(), R.drawable.slot_trophy_win);
        slotMoney = BitmapFactory.decodeResource(getResources(), R.drawable.slot_money_win);
        slotGlory = BitmapFactory.decodeResource(getResources(), R.drawable.slot_glory_win);
        slotGameOver = BitmapFactory.decodeResource(getResources(), R.drawable.slot_lose);

        //store new vlad images

        //score = Sprite.getScore();
    }

    public void Spin () {
        //Sprite.Sprite(slotMachine);
    }

    public void Power() {

    }

    public void Wealth() {

    }
    public void Glory() {

    }
}
