package com.softwarei.epar2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DealWithVlad extends Activity {

    private VladThread vladThread;
    private GameOver gameOver;

    private int numScandal;
    private int character_index;
    private int score;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vladThread = new VladThread(this);
        setContentView(vladThread);

        Intent intent = getIntent();
        character_index = intent.getIntExtra("character", 0);
        numScandal = intent.getIntExtra("scandal", 0);
        level = intent.getIntExtra("level", 0);
        score = intent.getIntExtra("score", 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //vladThread.pause();
        Intent gameOver = new Intent(DealWithVlad.this, GameOver.class);
        gameOver.putExtra("score",0);
        startActivity(gameOver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vladThread.resume();
    }
}
