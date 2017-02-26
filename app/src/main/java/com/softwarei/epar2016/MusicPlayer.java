package com.softwarei.epar2016;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import static com.softwarei.epar2016.R.raw.music_main_menu;

/**
 * Created by Rob on 2/25/2017.
 */

public class MusicPlayer extends Service {

    public static MediaPlayer mp;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        mp = MediaPlayer.create(this, music_main_menu);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
