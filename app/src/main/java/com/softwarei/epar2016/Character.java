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
        character = new Bitmap[15];
        int  x = R.drawable.ctrumpstand;
        for(int i = 0; i < 15; i++)
        {
            x -=i;
            character[i] = BitmapFactory.decodeResource(ctx.getResources(), x);
        }
        /**This will place c_TrumpStand at index 0, with c_TrumpRun at 1, and c_TrumpDuck at 2. AbeDuck will be at 14.
         *  All Stand images will be at an index divisabel by 3, from there it is +1 to run imgage or +2 to duck image.
         */
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
