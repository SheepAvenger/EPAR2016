package com.softwarei.epar2016;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Rob on 2/24/2017.
 */

public class HighScores extends AppCompatActivity
{
    private int index;
    private MusicPlayer mp;
    private boolean click = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);
        Context ctx;
        mp = new MusicPlayer();
        ctx = getApplicationContext();
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
        //here
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        HighScores h = new HighScores( ctx, texts);
        Button mainMenu;
        mainMenu=(Button)findViewById(R.id.highscoreMainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                click = true;
                Intent Menu = new Intent(HighScores.this, MainMenu.class);
                Menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                Menu.putExtra("index", index);
                startActivity(Menu);
                finish();
            }
        });
    }
    public HighScores()
    {
        //to load
    }
    public HighScores(Context ctxs, TextView[] texts)
    {
        String scores[];
        Scores s = new Scores(ctxs);
        scores = s.getScore();
        for(int i = 0; i < 10; i++)
        {
            texts[i].setText(scores[i]);
        }
    }
    @Override
    public void onBackPressed() {
        Intent MainMenu = new Intent(HighScores.this, MainMenu.class);
        MainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(MainMenu);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!click)
            mp.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(HighScores.this, MusicPlayer.class));
    }
}
