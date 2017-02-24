package com.softwarei.epar2016;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rob on 2/23/2017.
 */

public class Level extends AppCompatActivity
{
    private int level;
    private Bitmap Background[];
    private Context ctx;

    public Level(Context current)
    {
        level = 0;
        Background = new Bitmap[10];
        this.ctx = current;
        int  x = R.drawable.level0;
        for(int i = 0; i < 10; i++)
        {
            x +=i;
            Background[i] = BitmapFactory.decodeResource(ctx.getResources(), x);
        }
    }

    public void setLevel()
    {
        level += 1;
    }

    public int getLevel()
    {
        return level;
    }

    public Bitmap getBackground()
    {
        return Background[level];
    }

}
