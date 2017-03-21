package com.softwarei.epar2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

//here
public class DealWithVlad extends Activity {

    private VladThread vladThread;

    Button   mButton;
    EditText mEdit;
    boolean click = false;
    private int numScandal;
    private int character_index;
    public int score;
    private int level;
    private int index;
    private int speed;
    private int delay;
    private int[] position;
    private boolean recovery;
    protected boolean secondAttempt;
    private int vlad;
    MusicPlayer mp;
    RelativeLayout tryingView;
    private long pauseTime, levelTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mp = new MusicPlayer();
        character_index = intent.getIntExtra("character", 0);
        numScandal = intent.getIntExtra("scandal", 0);
        level = intent.getIntExtra("level", 0);
        score = intent.getIntExtra("score", 0);
        index = intent.getIntExtra("index", 0);
        speed = intent.getIntExtra("speed",-6);
        position = intent.getIntArrayExtra("position");
        delay = intent.getIntExtra("delay",100);
        vlad = intent.getIntExtra("vlad", 0);
        pauseTime = intent.getLongExtra("pauseTime", System.nanoTime());
        levelTime = intent.getLongExtra("levelTime", System.nanoTime());
        secondAttempt = (vlad == 1)? true : false;
        recovery = intent.getBooleanExtra("recovery",true);
        //redundency
        if (secondAttempt) {
            Intent gameOver = new Intent(DealWithVlad.this, GameOver.class);
            gameOver.putExtra("score",score);
            gameOver.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(gameOver);
            finish();
        }

        setContentView(R.layout.deal_with_vlad);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.VladFrameLayout);
        vladThread = new VladThread(this,character_index, numScandal, level, score, index, speed, position, delay, recovery, vlad, pauseTime, levelTime);
        frameLayout.addView(vladThread);

        mEdit = (EditText) findViewById(R.id.userGuess);
        mButton = (Button)findViewById(R.id.user_continue);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                click = true;
                String value = mEdit.getText().toString();
                vladThread.whatDidTheUserGuess = Integer.parseInt(value);
            }

        });
        //tryingView = (RelativeLayout)findViewById(R.id.deal_with_vlad);

    }

    @Override
    protected void onPause() {
        super.onPause();
        vladThread.pause();
        if(!click)
            mp.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        vladThread.resume();
        startService(new Intent(DealWithVlad.this, MusicPlayer.class));
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
