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
import android.widget.ImageButton;

import java.util.List;

import static android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY;
import static com.softwarei.epar2016.R.raw.music_main_menu;

/**
 * Created by Rob on 2/25/2017.
 */

public class MusicPlayer extends Service implements MediaPlayer.OnCompletionListener {

    private static final String TAG = null;
    private static MediaPlayer player;
    int[] songs = {R.raw.music_main_menu,R.raw.deal_vlad, R.raw.careless_whisper, R.raw.never_gonna_give_you_up, R.raw.we_built_this_city, R.raw.chop_suey, R.raw.pursite, R.raw.before_i_forget, R.raw.guiles_theme};
    private int current_index;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(getApplicationContext(),songs[current_index]);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
    public void mainmenu()
    {

    }

    public void play()
    {
            if(current_index == 7) // change to 10 when other songs added
            {
                current_index = 2;
            }
            AssetFileDescriptor afd = (this.getResources().openRawResourceFd(songs[current_index]));
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

        if(current_index == 0)
        {
            player.setLooping(true);
            player.start();

        }
        else
        {
            player = MediaPlayer.create(getApplicationContext(),songs[current_index]);
            play();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    play();
                };
            });
        }
        return START_NOT_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        // TO DO Auto-generated method
        return null;
    }

    public void onStop() {
        //here

    }
    public void onPause() {
        player.pause();

    }

    public void onResume()
    {
        player.start();
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }
}
