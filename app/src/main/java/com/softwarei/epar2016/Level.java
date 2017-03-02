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

    public Level(Context ctx)
    {
        level = 0;
        Background = new Bitmap[10];
        Background[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level0);
        Background[1] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level1);
        Background[2] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level2);
        Background[3] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level3);
        Background[4] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level4);
        Background[5] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level5);
        Background[6] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level6);
        Background[7] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level7);
        Background[8] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level8);
        Background[9] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.level9);

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
