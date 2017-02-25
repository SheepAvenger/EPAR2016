package com.softwarei.epar2016;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Level level = new Level(getApplicationContext());
        Character c = new Character(getApplicationContext());

        Button button;
        button=(Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener()
        {


            public void onClick(View v)
            {
                Intent hs = new Intent(MainMenu.this, HighScores.class);
                startActivity(hs);
                //HighScores h = new HighScores(0);
                //here

             }

        });
}
}
