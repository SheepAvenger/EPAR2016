package com.softwarei.epar2016;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Monte Cristso on 3/11/2017.
 */

public class Settings extends Activity{



    boolean bold = true;
    TextView title;

    private Handler animate = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(bold)
            {
                title.setTypeface(null, Typeface.NORMAL);

                bold = false;
            }
            else
            {
                title.setTypeface(null,Typeface.BOLD);

                bold = true;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final AudioManager audioManager;
        title = (TextView) findViewById(R.id.settings_title);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        SeekBar volControl = (SeekBar)findViewById(R.id.seekBar);
        volControl.setMax(maxVolume);
        volControl.setProgress(curVolume);
        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
            }
        });

        Button mainM = (Button) findViewById(R.id.settingsMainMenu);
        mainM.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent back = new Intent(Settings.this, MainMenu.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                finish();
            }

        });


        Runnable exec = new Runnable()
        {
            @Override
            public void run()
            {
                while(true) {
                    long future = System.currentTimeMillis() + 500;
                    while (System.currentTimeMillis() < future) {
                        synchronized (this) {
                            try {
                                wait(future - System.currentTimeMillis());
                            } catch (Exception e) {
                                Log.e("error",""+e.getMessage());
                            }
                        }
                    }
                    animate.sendEmptyMessage(0);
                }
            }
        };
        Thread animation = new Thread (exec);
        animation.start();
    }

    @Override
    public void onBackPressed() {
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        Intent music = new Intent(getApplication(), MusicPlayer.class);
        music.putExtra("index", 0);

        Intent MainMenu = new Intent(Settings.this, MainMenu.class);
        MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(MainMenu);
        finish();
    }
}
