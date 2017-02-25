package com.softwarei.epar2016;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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
