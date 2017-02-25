package com.softwarei.epar2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import static android.R.attr.id;

/**
 * Created by Rob on 2/24/2017.
 */

public class HighScores extends AppCompatActivity
{
    private String scores[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);
       ScrollView scroll = (ScrollView) this.findViewById(R.id.scrollView0);
       LinearLayout linear = (LinearLayout) this.findViewById(R.id.linear0);
       TextView textView0 = (TextView) this.findViewById(R.id.textView0);
        TextView textView1 = (TextView) this.findViewById(R.id.textView1);
        TextView textView2 = (TextView) this.findViewById(R.id.textView2);
        TextView textView3 = (TextView) this.findViewById(R.id.textView3);
        TextView textView4 = (TextView) this.findViewById(R.id.textView4);
        TextView textView5 = (TextView) this.findViewById(R.id.textView5);
        TextView textView6 = (TextView) this.findViewById(R.id.textView6);
        TextView textView7 = (TextView) this.findViewById(R.id.textView7);
        TextView textView8 = (TextView) this.findViewById(R.id.textView8);
        TextView textView9 = (TextView) this.findViewById(R.id.textView9);
        TextView[] texts = {textView0, textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9};
        //ctx = getApplicationContext();
        HighScores h = new HighScores(scroll, linear, this, texts);
        Button mainMenu;
        mainMenu=(Button)findViewById(R.id.highscoreMainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent Menu = new Intent(HighScores.this, MainMenu.class);
                startActivity(Menu);

            }
        });
    }
    public HighScores()
    {
        //to load
    }
    public HighScores(ScrollView scroll, LinearLayout linear,Context ctx, TextView[] texts)
    {
        Scores s = new Scores(ctx);
        scores = s.getScore();
        for(int i = 0; i < 10; i++)
        {
            texts[i].setText(scores[i]);
        }
    }
}
