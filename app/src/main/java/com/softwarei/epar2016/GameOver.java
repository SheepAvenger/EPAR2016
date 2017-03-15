package com.softwarei.epar2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Stu on 3/4/2017.
 */

public class GameOver extends AppCompatActivity {
    //here
    private EditText initials;
    private String playerInit;
    private int score;
    private MusicPlayer mp;
    private boolean click = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        this.playerInit="";

        mp = new MusicPlayer();
        initials = (EditText) findViewById(R.id.getInitials);
        final Scores s = new  Scores(getApplicationContext());
        Button button1;
        button1 = (Button) findViewById(R.id.hScores);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = true;
                GameOver.this.setInitials(initials.getText().toString());
                if(playerInit.length() >0 && playerInit.length() <= 8) {
                    s.addScore(score,playerInit, getApplicationContext());

                    Intent viewHScores = new Intent(getApplicationContext(), HighScores.class);
                    viewHScores.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    viewHScores.putExtra("index", 2);
                    startActivity(viewHScores);
                    finish();
                }
                else
                {
                    GameOver.this.chastiseScrub();
                }
            }

        });
    }


    public void setInitials(String name)
    {
         playerInit = name;
    }

    public String getInit()
    {
        return this.playerInit;
    }

    public void chastiseScrub()
    {
        Toast.makeText(this, "I SAID ENTER YOUR INITIALS NOT THE MAGNA CARTA", Toast.LENGTH_LONG).show();
        setInitials("");
        initials.setText("Stu");
    }

    @Override
    public void onBackPressed() {
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        Intent music = new Intent(getApplication(), MusicPlayer.class);
        music.putExtra("index", 0);

        Intent MainMenu = new Intent(GameOver.this, MainMenu.class);
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
        startService(new Intent(GameOver.this, MusicPlayer.class));
    }
}
