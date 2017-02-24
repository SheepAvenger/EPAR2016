package com.softwarei.epar2016;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.String;

/**
 * Created by Rob on 2/23/2017.
 */

public class Scores extends AppCompatActivity
{
    private highScore scores[];
    private String scoreString[];
    private Context ctx;

    public Scores(Context current)
    {
        int i = 0;
        this.ctx = current;
        scores = new highScore[11];
        scoreString = new String[10];
        InputStream is = ctx.getResources().openRawResource(R.raw.scores);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        if (is!=null)
        {
            try
            {
                while (i != 10)
                {
                    scoreString[i] = reader.readLine();
                    highScore s = new highScore();
                    String parse[] = scoreString[i].split(" ");
                    s.name = parse[0];
                    s.score = Integer.parseInt(parse[1]);
                    scores[i] = s;
                    i++;
                }
            }
            catch(IOException e)
            {

            }

        }

    }

}
