package com.softwarei.epar2016;

import android.app.Activity;
import android.os.Bundle;

public class DealWithVlad extends Activity {

    private VladThread vladThread;
    private GameOver gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vladThread = new VladThread(this);
        setContentView(vladThread);
    }

    @Override
    protected void onPause() {
        super.onPause();
        vladThread.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vladThread.resume();
    }
}
