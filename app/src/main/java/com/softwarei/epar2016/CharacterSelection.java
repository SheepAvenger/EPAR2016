package com.softwarei.epar2016;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Rob on 2/25/2017.
 */

public class CharacterSelection extends AppCompatActivity
{
    private Bitmap character[];
    private MusicPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_selection);
        ImageButton Abe = (ImageButton)findViewById(R.id.Abe);
        ImageButton Clinton = (ImageButton)findViewById(R.id.Clinton);
        ImageButton FDR = (ImageButton)findViewById(R.id.FDR);
        ImageButton Obama = (ImageButton)findViewById(R.id.Obama);
        ImageButton Trump = (ImageButton)findViewById(R.id.Trump);
        ImageButton Sanders = (ImageButton)findViewById(R.id.Sanders);
        ImageButton Washington = (ImageButton)findViewById(R.id.Washington);
        ImageButton Kennedy = (ImageButton)findViewById(R.id.Kennedy);
        Washington.setEnabled(false);
        Kennedy.setEnabled(false);
        final Context ctx = getApplicationContext();

        final CharacterSelection sprite = new CharacterSelection();

        Abe.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 12);
                mp = new MusicPlayer();
                mp.play(ctx);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });

        Clinton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 2);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });

        FDR.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 8);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });

        Obama.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 4);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });

        Trump.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 2);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });
        Sanders.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 10);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });
        Washington.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 0);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });
        Kennedy.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sprite.pickCharacter(ctx, 6);
                Intent Menu = new Intent(CharacterSelection.this, MainGame.class);
                startActivity(Menu);
            }
        });
    }

    public CharacterSelection()
    {
        character = new Bitmap[2];
    }
    public void pickCharacter(Context ctx, int index)
    {
        Character sprites = new Character(ctx);
        sprites.setIndex(index);
        character[0] = sprites.getCharacter();
        sprites.setIndex(index+1);
        character[1] = sprites.getCharacter();

    }
}
