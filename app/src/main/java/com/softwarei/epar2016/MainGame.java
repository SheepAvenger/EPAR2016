package com.softwarei.epar2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MainGame extends Activity implements View.OnTouchListener {

    GameView gameView;
    MusicPlayer mp;
    boolean recovery;
    int[] position;
    RelativeLayout running, paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game);

        ImageButton duckButton = (ImageButton) findViewById(R.id.Duck);
        duckButton.setOnTouchListener(this);
        ImageButton jumpButton = (ImageButton) findViewById(R.id.Jump);
        jumpButton.setOnTouchListener(this);
        ImageButton pauseButton = (ImageButton)findViewById(R.id.Pause);
        pauseButton.setOnTouchListener(this);
        ImageButton resumeButton = (ImageButton)findViewById(R.id.Resume);
        resumeButton.setOnTouchListener(this);
        ImageButton quitButton = (ImageButton)findViewById(R.id.Quit);
        quitButton.setOnTouchListener(this);

        mp = new MusicPlayer();
        int index, level_index, score, scandal, speed, delay;


        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.FrameLayout);

        Intent intent = getIntent();
        index = intent.getIntExtra("character", 0);
        level_index = intent.getIntExtra("level",0);
        score = intent.getIntExtra("score",0);
        scandal = intent.getIntExtra("scandal",0);
        speed = intent.getIntExtra("speed",-6);
        recovery = intent.getBooleanExtra("recovery",false);
        position = intent.getIntArrayExtra("position");
        delay = intent.getIntExtra("delay",100);

        gameView = new GameView(this, index, level_index, scandal, score, speed, recovery, position, delay);
        frameLayout.addView(gameView);

        running = (RelativeLayout)findViewById(R.id.gameRunning);
        paused = (RelativeLayout)findViewById(R.id.gamePaused);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId()) {
            case R.id.Jump:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameView.jumpButtonDown();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gameView.jumpButtonUp();
                }
                break;
            case R.id.Duck:
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameView.duckButtonDown();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    gameView.duckButtonUp();
                }
                break;
            case R.id.Pause:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    running.setVisibility(View.INVISIBLE);
                    paused.setVisibility(View.VISIBLE);
                    gameView.pauseButtonUp();
                    mp.onPause();
                    //pauseService(new Intent(MainGame.this, MusicPlayer.class));
                }
                break;
            case R.id.Resume:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    paused.setVisibility(View.INVISIBLE);
                    running.setVisibility(View.VISIBLE);
                    gameView.resumeButtonUp();
                    mp.onResume();
                }
                break;
            case R.id.Quit:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopService(new Intent(MainGame.this, MusicPlayer.class));
                    final Intent music = new Intent(getApplication(), MusicPlayer.class);
                    music.putExtra("index", 0);
                    startService(music);

                    Intent MainMenu = new Intent(MainGame.this, MainMenu.class);
                    MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(MainMenu);
                    finish();
                }
            default:
                break;
        }
        return true;
    }

    public MainGame(){}

    @Override
    public void onBackPressed() {
        Intent MainMenu = new Intent(MainGame.this, MainMenu.class);
        MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(MainMenu);
        finish();
    }
}

