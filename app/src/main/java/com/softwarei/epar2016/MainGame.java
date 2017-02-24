package com.softwarei.epar2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainGame extends Activity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_game);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.FrameLayout);
        Button jumpButton = (Button)findViewById(R.id.Jump);
        jumpButton.setOnTouchListener(this);
        GameView gameView = new GameView(this);
        frameLayout.addView(gameView);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId()) {
            case R.id.Jump:
                break;
            case R.id.Duck:
                break;
            case R.id.Pause:
                break;
            default:
                break;
        }
        return true;
    }
}

