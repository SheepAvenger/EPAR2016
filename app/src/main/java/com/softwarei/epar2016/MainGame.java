package com.softwarei.epar2016;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.view.WindowManager;

public class MainGame extends Activity implements View.OnTouchListener {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_game);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.FrameLayout);
        Button duckButton = (Button)findViewById(R.id.Duck);
        duckButton.setOnTouchListener(this);
        Button jumpButton = (Button)findViewById(R.id.Jump);
        jumpButton.setOnTouchListener(this);
        Button pauseButton = (Button)findViewById(R.id.Pause);
        pauseButton.setOnTouchListener(this);
        gameView = new GameView(this);
        frameLayout.addView(gameView);
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
                    //Intent intent = new Intent(this, .class);
                    //startActivity(intent);
                }
                break;
            default:
                break;
        }
        return true;
    }

    public MainGame(){};
}

