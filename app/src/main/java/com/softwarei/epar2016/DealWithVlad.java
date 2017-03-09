package com.softwarei.epar2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//here
public class DealWithVlad extends Activity {

    private VladThread vladThread;
    private GameOver gameOver;

    private int numScandal;
    private int character_index;
    public int score;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        character_index = intent.getIntExtra("character", 0);
        numScandal = intent.getIntExtra("scandal", 0);
        level = intent.getIntExtra("level", 0);
        score = intent.getIntExtra("score", 0);
        vladThread = new VladThread(this,character_index, numScandal, level, score);
        setContentView(vladThread);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //vladThread.pause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        vladThread.resume();
    }

    @Override
    public void onBackPressed() {
        vladThread.pause();
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        Intent music = new Intent(getApplication(), MusicPlayer.class);
        music.putExtra("index", 0);
        Intent MainMenu = new Intent(DealWithVlad.this, MainMenu.class);
        MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(MainMenu);
        finish();
    }

}
