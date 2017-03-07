package com.softwarei.epar2016;

import android.content.Context;
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
    private int numScandal=0;
    private int index=0;
    boolean bold = true;
    private String[] candidates={"Dishonest Abe!","Clinton!","FDR!","Obama!",
                                "Trump", "Bernie Sanders!", "Washington", "Kennedy" };
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
                if(numScandal ==3)
                {
                    Intent deal = new Intent(Scandal.this, DealWithVlad.class);
                    startActivity(deal);
                }
                else
                {
                    //return to main game.
                    finish();
                }
            }

        });


    }


    public void setScandalousCand()
    {
        scandalousCand= candidates[index];
    }

    public String getScandalousCand()
    {
        return scandalousCand;
    }

    public String getScandal()
    {
        switch(index){
            case 0:
                return abeScandal[numScandal];
            case 1:
                return clintonScandal[numScandal];
            case 2:
                return fdrScandal[numScandal];

            case 3:
                return obamaScandal[numScandal];

            case 4:
                return trumpScandal[numScandal];

            case 5:
                return bernScandal[numScandal];

            case 6:
                return "Washington Scandal";

            case 7:
                return "Kennedy Scandal";

            default:
                return "Invalid Scandal";
        }
    }

}
