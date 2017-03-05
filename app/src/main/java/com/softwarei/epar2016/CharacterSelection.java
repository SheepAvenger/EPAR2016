package com.softwarei.epar2016;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

/**
 * Created by Rob on 2/25/2017.
 */

public class CharacterSelection extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int kennedy = 0;
        int washington = 0;
        setContentView(R.layout.character_selection);
        ImageButton Abe = (ImageButton)findViewById(R.id.Abe);
        ImageButton Clinton = (ImageButton)findViewById(R.id.Clinton);
        ImageButton FDR = (ImageButton)findViewById(R.id.FDR);
        ImageButton Obama = (ImageButton)findViewById(R.id.Obama);
        ImageButton Trump = (ImageButton)findViewById(R.id.Trump);
        ImageButton Sanders = (ImageButton)findViewById(R.id.Sanders);
        ImageButton Washington = (ImageButton)findViewById(R.id.Washington);
        ImageButton Kennedy = (ImageButton)findViewById(R.id.Kennedy);
        String FILE = "data/data/com.softwarei.epar2016/characters.txt";

        try // creates the text file characters.txt if it does not exists
        {
            File unlockable = new File(FILE);
            if(!unlockable.createNewFile())
            {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("characters.txt", this.MODE_PRIVATE));
                outputStreamWriter.write(1);
                outputStreamWriter.write(0);
                outputStreamWriter.close();

            }

        }
        catch(IOException e){};

        try
        {
           FileInputStream is = this.openFileInput("characters.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if(is!= null) {
                kennedy = (int)reader.read();
                System.out.println(kennedy);
                washington = (int)reader.read();
            }
        }
        catch(IOException e)
        {

        }
        if(kennedy == 1)
           Kennedy.setEnabled(true);
        else
            Kennedy.setEnabled(false);
        if(washington == 1)
            Washington.setEnabled(true);
        else
            Washington.setEnabled(false);

        Random rand = new Random();
        int index = rand.nextInt(6) + 2;

       final Intent music = new Intent(getApplication(), MusicPlayer.class);
        music.putExtra("index", index);

        Abe.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",12);
                startActivity(Menu);
            }
        });

        Clinton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",2);
                startActivity(Menu);
            }
        });

        FDR.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",8);
                startActivity(Menu);
            }
        });

        Obama.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",4);
                startActivity(Menu);
            }
        });

        Trump.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",2);
                startActivity(Menu);
            }
        });
        Sanders.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",10);
                startActivity(Menu);
            }
        });
        Washington.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",0);
                startActivity(Menu);
            }
        });
        Kennedy.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                Menu.putExtra("run",6);
                startActivity(Menu);
            }
        });
    }

    public CharacterSelection()
    {

    }

}
