package com.softwarei.epar2016;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
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
    String FILE;

    public Scores(){};

    public Scores(Context ctx)
    {
        int i = 0;
        FILE = "scores.txt";
        scores = new HighScore[10];
        scoreString = new String[10];

        String File = "data/data/com.softwarei.epar2016/scores.txt";

        try
        {
            File scoreList = new File(File);
            if(scoreList.createNewFile())
            {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("scores.txt", this.MODE_PRIVATE));
                outputStreamWriter.write("a 500");
                outputStreamWriter.write("b 450");
                outputStreamWriter.write("c 400");
                outputStreamWriter.write("d 350");
                outputStreamWriter.write("e 300");
                outputStreamWriter.write("f 250");
                outputStreamWriter.write("g 200");
                outputStreamWriter.write("h 150");
                outputStreamWriter.write("i 100");
                outputStreamWriter.write("j 50");
                outputStreamWriter.close();

            }

        }
        catch(IOException e){};

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

    public void addScore(int score, String name, Context ctx)
    {
        String newString = "";
        boolean newHighScore = false;
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(ctx.openFileOutput(FILE, ctx.MODE_PRIVATE));
            for(int i = 0; i < 10; i++)
            {
                if(score > scores[i].score)
                {
                    for(int j = 8; j > i; j--)
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
