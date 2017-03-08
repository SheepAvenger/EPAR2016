package com.softwarei.epar2016;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.String;

/**
 * Created by Rob on 2/23/2017.
 */

public class Scores extends AppCompatActivity
{
    //here
    private HighScore scores[];
    private String scoreString[];

    public Scores(){};

    public Scores(Context ctx)
    {
        int i = 0;
        scores = new HighScore[10];
        scoreString = new String[10];
        File path = ctx.getFilesDir();
        File scoreList = new File(path, "score.txt");

        try
        {

            if(scoreList.createNewFile())
            {
                FileOutputStream outputStreamWriter = new FileOutputStream(scoreList);
                outputStreamWriter.write("a 10\n".getBytes());
                outputStreamWriter.write("b 10\n".getBytes());
                outputStreamWriter.write("c 10\n".getBytes());
                outputStreamWriter.write("d 10\n".getBytes());
                outputStreamWriter.write("e 10\n".getBytes());
                outputStreamWriter.write("f 10\n".getBytes());
                outputStreamWriter.write("g 10\n".getBytes());
                outputStreamWriter.write("h 10\n".getBytes());
                outputStreamWriter.write("i 10\n".getBytes());
                outputStreamWriter.write("j 10\n".getBytes());
                outputStreamWriter.close();

            }

        }
        catch(IOException e){};

          try
            {
                FileInputStream is = new FileInputStream(scoreList);
                int length = (int) scoreList.length();

                //byte[] bytes = new byte[length];
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                if(is != null)
                {
                    while (i != 10)
                    {
                        //is.read(bytes);
                        //scoreString[i] = new String(bytes);
                        scoreString[i] = reader.readLine();
                        HighScore s = new HighScore();
                        String parse[] = scoreString[i].split(" ");
                        s.name = parse[0];
                        s.score = Integer.parseInt(parse[1]);
                        scores[i] = s;
                        i++;
                    }

                    is.close();
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
        File path = ctx.getFilesDir();
        File scoreList = new File(path, "score.txt");
        try
        {
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
                FileOutputStream outputStreamWriter = new FileOutputStream(scoreList);
                for(int i = 0; i < 10; i++)
                {
                    newString = scores[i].name + " " + scores[i].score + "\n";
                    scoreString[i] = newString;
                    outputStreamWriter.write(scoreString[i].getBytes());
                }
                outputStreamWriter.close();
            }

        }
        catch(IOException e) {

        }
    }

    public String[] getScore()
    {
        return scoreString;
    }

}
