package com.softwarei.epar2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Intent music = new Intent(getApplication(), MusicPlayer.class);
        music.putExtra("index", 0);
        startService(music);


        ImageButton button;
        button=(ImageButton)findViewById(R.id.HighScoresMenu);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //Intent hs = new Intent(MainMenu.this, HighScores.class);
                //startActivity(hs);
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
                Intent characterSelection = new Intent(MainMenu.this, CharacterSelection.class);
                startActivity(characterSelection);
            }

        });

        Button vlad;
        vlad=(Button)findViewById(R.id.vlad);
        vlad.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent dealWithVlad = new Intent(MainMenu.this, DealWithVlad.class);
                startActivity(dealWithVlad);

            }
        });
    }

}
