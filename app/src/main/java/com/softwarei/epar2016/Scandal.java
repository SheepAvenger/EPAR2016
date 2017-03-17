package com.softwarei.epar2016;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;


/**
 * Created by Stu on 3/5/2017.
 */

public class Scandal extends AppCompatActivity{
    private int numScandal, tinyZebra, index, character_index, score, level, speed, delay, vlad;
    boolean recovery;
    int[] position;
    private MusicPlayer mp;
    private boolean click = false;
    boolean bold = true;
    long pauseTime;
    private String[] candidates={"Dishonest Abe!","Clinton!","FDR!","Obama!",
                                "Trump", "Washington", "Kennedy" };
    private String scandalousCand= "";
    private String[] abeScandal =
                    {"Caught browsing unseemly pictographs of women's ankles!!!",
                    "Caught owning Slaves! Details uncovered in ledger entitled:\"Hide these people from THE PEOPLE\" ",
                    "Caught making illegal arms deals with THE REBS"};
    private String[] clintonScandal=
            {"Frightens aides! Seemingly smashes cell phones out of sheer compulsion!!",
            "Discovered communicating with interdimensional beings through spirit cooking!!",
            "Wins debate with plank! CNN calls it: \"The most exciting/presidential thing we've ever seen!\""};
    private String[] fdrScandal=
            {"Quote:  \"Term limits? Not in my Empire!\"",
            "Quote: \"They see me rolling, they hatin\"",
            "Quote: \"I think people will find our Interment Camps are quite nice!\""};
    private String[] obamaScandal=
                {"Dreams of owning a private collection of military drones!!",
                        "Cheats at Monopoly!!",
                "Hates Chipotle?!?!?!?"};
    private String[] trumpScandal={
            "Quote: \"People tell me I have the best personality. Really its so great! Bahlieve Me!\"",
            "Quote: \"I beat China all the time. All the time.\"",
            "Quote: \"Vaccinations?? Who needs 'em?\""
    };
    private String[] bernScandal=
            {"Admits to be part Ninja Turtle!?!?",
            "Found in Walmart parking lot holds carboard sign reads: \"Will work for Super Delegates\"",
            "Quote: \"Can I live Eight more years? Let's find out together!!\""};
    //private String[] washScandal={};
    //private String[] kenScandal={};
    //here


    private TextView scandal, candidate , scandalousScandal;

    private Handler animate = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(bold)
            {
                scandal.setTypeface(null,Typeface.NORMAL);
                candidate.setTypeface(null, Typeface.NORMAL);
                scandalousScandal.setTypeface(null, Typeface.NORMAL);
                bold = false;
            }
            else
            {
                scandal.setTypeface(null,Typeface.BOLD);
                candidate.setTypeface(null, Typeface.BOLD);
                scandalousScandal.setTypeface(null, Typeface.BOLD);
                bold = true;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scandal);

        mp = new MusicPlayer();
        Intent intent = getIntent();
        character_index = intent.getIntExtra("character", 0);
        numScandal = intent.getIntExtra("scandal", 0);
        tinyZebra = numScandal;
        numScandal--;
        level = intent.getIntExtra("level", 0);
        score = intent.getIntExtra("score", 0);
        speed = intent.getIntExtra("speed",-6);
        recovery = intent.getBooleanExtra("recovery",true);
        position = intent.getIntArrayExtra("position");
        delay = intent.getIntExtra("delay",100);
        vlad = intent.getIntExtra("vlad",0);
        pauseTime = intent.getLongExtra("pauseTime", System.nanoTime());
        scandal = (TextView) findViewById(R.id.scandalous);
        candidate= (TextView) findViewById(R.id.scan_candidate);
        scandalousScandal= (TextView) findViewById(R.id.scandalous_scandal);
        this.setScandalousCand();
        candidate.setText(this.getScandalousCand());
        scandalousScandal.setText(this.getScandal());
        Runnable exec = new Runnable()
        {
            @Override
            public void run()
            {
                while(true) {
                    long future = System.currentTimeMillis() + 500;
                    while (System.currentTimeMillis() < future) {
                        synchronized (this) {
                            try {
                                wait(future - System.currentTimeMillis());
                            } catch (Exception e) {
                                Log.e("error",""+e.getMessage());
                            }
                        }
                    }
                    animate.sendEmptyMessage(0);
                }
            }
        };
        Thread animation = new Thread (exec);
        animation.start();



        Button button1 = (Button) findViewById(R.id.mg);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                click = true;
                if(tinyZebra == 3)
                {
                    if(vlad == 0)
                    {
                        stopService(new Intent(Scandal.this, MusicPlayer.class));
                        Intent music = new Intent(getApplication(), MusicPlayer.class);
                        music.putExtra("index", 1);
                        startService(music);

                        Intent deal = new Intent(Scandal.this, DealWithVlad.class);
                        deal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                        deal.putExtra("character",character_index);
                        deal.putExtra("scandal",tinyZebra);
                        deal.putExtra("level",level);
                        deal.putExtra("score",score);
                        deal.putExtra("speed",speed);
                        deal.putExtra("recovery",recovery);
                        deal.putExtra("position",position);
                        deal.putExtra("delay",delay);
                        deal.putExtra("vlad", vlad);
                        //fixed typo
                        deal.putExtra("pauseTime", pauseTime);
                        startActivity(deal);
                        finish();
                    }
                    else
                    {
                        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
                        Intent music = new Intent(getApplicationContext(), MusicPlayer.class);
                        music.putExtra("index", 2);
                        startService(music);

                        Intent gameOver = new Intent(Scandal.this, GameOver.class);
                        gameOver.putExtra("score",score);
                        startActivity(gameOver);
                    }

                }
                else if(tinyZebra == 2)
                {
                    stopService(new Intent(Scandal.this, MusicPlayer.class));
                    Intent music = new Intent(getApplication(), MusicPlayer.class);
                    music.putExtra("index", 3);
                    startService(music);

                    Intent Main = new Intent(Scandal.this, MainGame.class);
                    Main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    Main.putExtra("character",character_index);
                    Main.putExtra("scandal",tinyZebra);
                    Main.putExtra("level",level);
                    Main.putExtra("score",score);
                    Main.putExtra("speed",speed);
                    Main.putExtra("recovery",recovery);
                    Main.putExtra("position",position);
                    Main.putExtra("delay",delay);
                    Main.putExtra("vlad", vlad);
                    Main.putExtra("pauseTime", pauseTime);
                    startActivity(Main);
                    finish();
                }
                else
                {
                    Intent Main = new Intent(Scandal.this, MainGame.class);
                    Main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    Main.putExtra("character",character_index);
                    Main.putExtra("scandal",tinyZebra);
                    Main.putExtra("level",level);
                    Main.putExtra("score",score);
                    Main.putExtra("speed",speed);
                    Main.putExtra("recovery",recovery);
                    Main.putExtra("position",position);
                    Main.putExtra("delay",delay);
                    Main.putExtra("pauseTime", pauseTime);
                    startActivity(Main);
                    finish();
                }
            }

        });


    }


    public void setScandalousCand()
    {
        index = ((character_index == 0)? 5 :
        (character_index == 2)? 4 :
        (character_index == 4)? 3 :
        (character_index == 6)? 6 :
        (character_index == 8)? 2 :
        (character_index == 10)? 1 : 0);
        scandalousCand= candidates[index];
    }

    public String getScandalousCand()
    {
        return scandalousCand;
    }

    public String getScandal()
    {
        switch(character_index){
            case 12:
                return abeScandal[numScandal];
            case 10:
                return clintonScandal[numScandal];
            case 8:
                return fdrScandal[numScandal];

            case 4:
                return obamaScandal[numScandal];

            case 2:
                return trumpScandal[numScandal];

            case 0:
                return "Washington Scandal";

            case 6:
                return "Kennedy Scandal";

            default:
                return bernScandal[numScandal];

        }
    }

    @Override
    public void onBackPressed() {
        stopService(new Intent(getApplicationContext(), MusicPlayer.class));
        Intent music = new Intent(getApplication(), MusicPlayer.class);
        music.putExtra("index", 0);

        Intent MainMenu = new Intent(Scandal.this, MainMenu.class);
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
        startService(new Intent(Scandal.this, MusicPlayer.class));
    }
}
