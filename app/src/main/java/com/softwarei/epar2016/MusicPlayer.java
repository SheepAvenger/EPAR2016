package com.softwarei.epar2016;

import java.io.IOException;
import java.util.ArrayList;
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
    MediaPlayer levels;
    int[] songs = {R.raw.music_main_menu, R.raw.careless_whisper, R.raw.never_gonna_give_you_up, R.raw.pursite};
    private int current_index = 1;

    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mainmenu();
        //player.setLooping(true); // Set looping

    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        play(this);
    }

    public void mainmenu()
    {
        player = MediaPlayer.create(getApplicationContext(),R.raw.bird);
        player.setLooping(true);

    }

    public void play(Context ctx)
    {
        //player.stop();
        levels = MediaPlayer.create(getApplicationContext(),songs[current_index]);
        levels.setOnCompletionListener(this);
        if(current_index == 3) // change to 10 when other songs added
        {
            current_index = 0;
        }
        current_index++;
        AssetFileDescriptor afd = ctx.getResources().openRawResourceFd(songs[current_index]);
        try
        {
            player.reset();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            player.prepare();
            player.start();
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
