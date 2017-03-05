package com.softwarei.epar2016;

import android.app.Activity;
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
    //comment
    private EditText initials;
    private String playerInit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        this.playerInit="";

        initials = (EditText) findViewById(R.id.getInitials);

        Button button1;
        button1 = (Button) findViewById(R.id.hScores);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameOver.this.setInitials(initials.getText().toString());
                if(playerInit.length() >0 && playerInit.length() <= 8) {
                    Intent viewHScores = new Intent(GameOver.this, MainMenu.class);
                    startActivity(viewHScores);
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
        initials.setText("Enter your Name");
    }
}
