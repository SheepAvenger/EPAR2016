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
    int index;
    RelativeLayout running, paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        index = intent.getIntExtra("run", 0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.FrameLayout);
        ImageButton duckButton = (ImageButton) findViewById(R.id.Duck);
        duckButton.setOnTouchListener(this);
        ImageButton jumpButton = (ImageButton) findViewById(R.id.Jump);
        jumpButton.setOnTouchListener(this);
        ImageButton pauseButton = (ImageButton)findViewById(R.id.Pause);
        pauseButton.setOnTouchListener(this);
        ImageButton resumeButton = (ImageButton)findViewById(R.id.Resume);
        resumeButton.setOnTouchListener(this);
        gameView = new GameView(this, index);
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
                }
                break;
            case R.id.Resume:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    paused.setVisibility(View.INVISIBLE);
                    running.setVisibility(View.VISIBLE);
                    gameView.resumeButtonUp();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public MainGame(){};
}

