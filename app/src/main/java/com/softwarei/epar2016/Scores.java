package com.softwarei.epar2016;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Rob on 2/23/2017.
 */

public class Scores extends AppCompatActivity
{
    private highScore scores[];
    private String scoreString[];
    private Context ctx;

    public Scores(Context current)throws IOException
    {
        int i = 0;
        this.ctx = current;
        scores = new highScore[9];
        scoreString = new String[9];
        InputStream is = this.getResources().openRawResource(R.raw.scores);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if (is!=null)
        {
            while ((scoreString[i] = reader.readLine()) != null)
            {
                String parse[] = scoreString[i].split(" ");
                scores[i].name = parse[0];
                scores[i].score = Integer.parseInt(parse[1]);
                System.out.println(scores[i].name + " " + scores[i].score);
                i++;
            }
        }

    }

}
