package com.softwarei.epar2016;

import java.util.ArrayList;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.util.List;

import static android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY;
import static com.softwarei.epar2016.R.raw.music_main_menu;

/**
 * Created by Rob on 2/25/2017.
 */

public class MusicPlayer extends Service {

    private static final String TAG = null;
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        int[] songs = {R.raw.music_main_menu};
        player = MediaPlayer.create(this,songs[0]);
        player.setLooping(true); // Set looping

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.start();
            }
        });

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return START_NOT_STICKY;
    }



    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }
}
