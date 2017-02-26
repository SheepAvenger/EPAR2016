package com.softwarei.epar2016;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.app.Service.START_STICKY;
import static com.softwarei.epar2016.R.raw.music_main_menu;


public class MainMenu extends AppCompatActivity {
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mp = MediaPlayer.create(this, music_main_menu);
        if(!mp.isPlaying())
        {
            mp.setLooping(true);
            mp.start();
        }

       /* mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {

                mp.start();
            }
        });*/

        ImageButton button;
        button=(ImageButton)findViewById(R.id.HighScoresMenu);
        button.setOnClickListener(new View.OnClickListener()
        {


            public void onClick(View v)
            {
                Intent hs = new Intent(MainMenu.this, HighScores.class);
                startActivity(hs);

            }

        });

        Button play;
        play=(Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener()
        {


            public void onClick(View v)
            {
                Intent mainGame = new Intent(MainMenu.this, MainGame.class);
                startActivity(mainGame);

            }

        });
}

}
