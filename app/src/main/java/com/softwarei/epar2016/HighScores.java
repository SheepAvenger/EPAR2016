package com.softwarei.epar2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.id;

/**
 * Created by Rob on 2/24/2017.
 */

public class HighScores extends AppCompatActivity
{
    private String scores[];
    private TextView textView;
    private Button mainMenu;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);
        textView = (TextView) this.findViewById(R.id.textView);
        ctx = getApplicationContext();
        HighScores h = new HighScores(0);
    }
    public HighScores()
    {
        //to load
    }
    public HighScores(int ii)
    {
        Context c = ctx;
        Intent in = new Intent(HighScores.this, Scores.class);
        startActivity(in);
        Scores s = new Scores();
        scores = s.getScore();
        for(int i = 0; i < 10; i++)
        {
            textView.setText(scores[i] + "\n");
        }

    }
}
