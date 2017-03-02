package com.softwarei.epar2016;

import java.io.IOException;
import java.util.ArrayList;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;

import java.util.List;

import static android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY;
import static com.softwarei.epar2016.R.raw.music_main_menu;

/**
 * Created by Rob on 2/25/2017.
 */

public class MusicPlayer extends Service implements MediaPlayer.OnCompletionListener {

    private static final String TAG = null;
    MediaPlayer player;
    int[] songs = {R.raw.music_main_menu, R.raw.careless_whisper, R.raw.never_gonna_give_you_up, R.raw.pursite};
    private int current_index;

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        play(this);
    }


    public void mainmenu()
    {

    }

    public void play(Context ctx)
    {
            if(current_index == 3) // change to 10 when other songs added
            {
                current_index = 1;
            }
            AssetFileDescriptor afd = (ctx.getResources().openRawResourceFd(songs[current_index]));
            try
            {
                player.reset();
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                player.prepare();
                player.start();
                current_index++;
            }
            catch(IllegalArgumentException e)
            {

            }
            catch (IllegalStateException e)
            {
            }
            catch (IOException e)
            {
            }
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        current_index = intent.getIntExtra("index", 0);
        player = MediaPlayer.create(getApplicationContext(),songs[current_index]);
        if(current_index == 0)
            player.setLooping(true);
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
        //here

    }
    public void onPause() {


    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }
}
