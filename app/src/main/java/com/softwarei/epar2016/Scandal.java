package com.softwarei.epar2016;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;


/**
 * Created by Stu on 3/5/2017.
 */

public class Scandal extends AppCompatActivity{
    private int numScandal, tinyZebra, index, character_index, score, level, speed, delay;
    boolean recovery;
    int[] position;
    boolean bold = true;
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
                if(tinyZebra == 3)
                {
                    stopService(new Intent(Scandal.this, MusicPlayer.class));
                    Intent music = new Intent(getApplication(), MusicPlayer.class);
                    music.putExtra("index", 1);
                    startService(music);

                    Intent deal = new Intent(Scandal.this, DealWithVlad.class);
                    deal.putExtra("character",character_index);
                    deal.putExtra("scandal",tinyZebra);
                    deal.putExtra("level",level);
                    deal.putExtra("score",score);
                    deal.putExtra("speed",speed);
                    deal.putExtra("recovery",recovery);
                    deal.putExtra("position",position);
                    deal.putExtra("delay",delay);
                    startActivity(deal);
                }
                else
                {
                    Intent Main = new Intent(Scandal.this, MainGame.class);
                    Main.putExtra("character",character_index);
                    Main.putExtra("scandal",tinyZebra);
                    Main.putExtra("level",level);
                    Main.putExtra("score",score);
                    Main.putExtra("speed",speed);
                    Main.putExtra("recovery",recovery);
                    Main.putExtra("position",position);
                    Main.putExtra("delay",delay);
                    startActivity(Main);
                    //return to main game.
                    //need current level, score, scandal count, and character
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

}
