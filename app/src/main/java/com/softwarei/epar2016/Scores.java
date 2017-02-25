package com.softwarei.epar2016;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.String;

/**
 * Created by Rob on 2/23/2017.
 */

public class Scores extends AppCompatActivity
{
    private HighScore scores[];
    private String scoreString[];
    private static Context ctx;
    String FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Scores s = new Scores(0);
        //ctx = ((Module) this.getApplication()).getContext();
    }

    public Scores(){};

    public Scores(Context ctx)
    {
        int i = 0;
        FILE = "scores.txt";
        scores = new HighScore[11];
        scoreString = new String[10];
        //InputStream is = ctx.getResources().openRawResource(R.raw.scores);
          try
            {
                FileInputStream is = ctx.openFileInput(FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                if(is != null)
                {
                    while (i != 10)
                    {
                        scoreString[i] = reader.readLine();
                        HighScore s = new HighScore();
                        String parse[] = scoreString[i].split(" ");
                        s.name = parse[0];
                        s.score = Integer.parseInt(parse[1]);
                        scores[i] = s;
                        i++;
                    }
                }

            }
            catch(IOException e)
            {

            }

    }

    public void addScore(int score, String name)
    {
        String newString = "";
        boolean newHighScore = false;
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(FILE, ctx.MODE_PRIVATE));
            for(int i = 0; i < 10; i++)
            {
                if(score > scores[i].score)
                {
                    for(int j = 9; j > i; j--)
                    {
                        scores[j].score = scores[j-1].score;
                        scores[j].name = scores[j-1].name;
                    }
                    scores[i].score = score;
                    scores[i].name = name;
                    newHighScore = true;
                    break;
                }
            }
            if(newHighScore)
            {
                for(int i = 0; i < 10; i++)
                {
                    newString = scores[i].name + " " + scores[i].score + "\n";
                    scoreString[i] = newString;
                    outputStreamWriter.write(scoreString[i]);
                }
            }
            outputStreamWriter.close();
        }
        catch(IOException e) {

        }
    }

    public String[] getScore()
    {
        return scoreString;
    }

}
