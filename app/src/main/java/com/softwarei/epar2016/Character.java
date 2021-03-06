package com.softwarei.epar2016;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * Created by Rob on 2/23/2017.
 */

public class Character extends AppCompatActivity
{
    private int index;
    private Bitmap character[];


    public Character(Context ctx)
    {
        index = 0;
        character = new Bitmap[16];
        character[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_washington_run);
        character[1] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_washington_duck);
        character[2] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_trump_run);
        character[3] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_trump_duck);
        character[4] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_obama_run);
        character[5] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_obama_duck);
        character[6] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_kennedy_run);
        character[7] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_kennedy_duck);
        character[8] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_fdr_run);
        character[9] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_fdr_duck);
        character[10] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_clinton_run);
        character[11] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_clinton_duck);
        character[12] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_abe_run);
        character[13] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.c_abe_duck);
        character[14] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.slot_machine);
        character[15] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.slot_scroll);
    }

    public void setIndex(int i)
    {
        index = i;
    }

    public int getIndex()
    {
        return index;
    }

    public Bitmap getCharacter()
    {
        return character[index];
    }
}
