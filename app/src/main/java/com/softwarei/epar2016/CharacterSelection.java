package com.softwarei.epar2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Rob on 2/25/2017.
 */

public class CharacterSelection extends AppCompatActivity
{
    MusicPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp = new MusicPlayer();
        String kennedy = "0";
        String washington = "0";
        setContentView(R.layout.character_selection);
        ImageButton Abe = (ImageButton)findViewById(R.id.Abe);
        ImageButton Clinton = (ImageButton)findViewById(R.id.Clinton);
        ImageButton FDR = (ImageButton)findViewById(R.id.FDR);
        ImageButton Obama = (ImageButton)findViewById(R.id.Obama);
        ImageButton Trump = (ImageButton)findViewById(R.id.Trump);
        ImageButton Sanders = (ImageButton)findViewById(R.id.Sanders);
        ImageButton Washington = (ImageButton)findViewById(R.id.Washington);
        ImageButton Kennedy = (ImageButton)findViewById(R.id.Kennedy);

        File path = getApplicationContext().getFilesDir();
        File unlockable = new File(path, "character.txt");

        try // creates the text file characters.txt if it does not exists
        {
            if(unlockable.createNewFile())
            {
                FileOutputStream outputStreamWriter = new FileOutputStream(unlockable);
                outputStreamWriter.write("0\n".getBytes());
                outputStreamWriter.write("0\n".getBytes());
                outputStreamWriter.close();

            }

        }
        catch(IOException e){Log.e("error",""+e.getMessage());}

        try
        {
            FileInputStream is = new FileInputStream(unlockable);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if(is!= null) {
                kennedy = reader.readLine();
                washington = reader.readLine();
                is.close();
            }
        }
        catch(IOException e)
        {
            Log.e("error",""+e.getMessage());
        }
        if(kennedy.equals("1"))
           Kennedy.setEnabled(true);
        else
            Kennedy.setEnabled(false);
        if(washington.equals("1"))
            Washington.setEnabled(true);
        else
            Washington.setEnabled(false);

        Random rand = new Random();
        int index = rand.nextInt(6) + 4;

       final Intent music = new Intent(getApplication(), MusicPlayer.class);
        music.putExtra("index", index);

       final Intent Main_Game = new Intent(CharacterSelection.this, MainGame.class);

        Abe.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",12);
                startActivity(Main_Game);
                finish();
            }
        });

        Clinton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",2);
                startActivity(Main_Game);
                finish();
            }
        });

        FDR.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",8);
                startActivity(Main_Game);
                finish();
            }
        });

        Obama.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",4);
                startActivity(Main_Game);
                finish();
            }
        });

        Trump.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",2);
                startActivity(Main_Game);
                finish();
            }
        });
        Sanders.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",10);
                startActivity(Main_Game);
                finish();
            }
        });
        Washington.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",0);
                startActivity(Main_Game);
                finish();
            }
        });
        Kennedy.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                stopService(new Intent(CharacterSelection.this, MusicPlayer.class));
                startService(music);

                Main_Game.putExtra("character",6);
                startActivity(Main_Game);
                finish();
            }
        });
    }

    public CharacterSelection()
    {

    }

    @Override
    public void onBackPressed() {
        Intent MainMenu = new Intent(CharacterSelection.this, MainMenu.class);
        MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(MainMenu);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(CharacterSelection.this, MusicPlayer.class));
    }

}
