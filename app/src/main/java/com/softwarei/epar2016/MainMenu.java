package com.softwarei.epar2016;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //2130837587
        Bitmap t[] = new Bitmap[2];
        int x;
        for(int i = 0; i <= 1; i++)
        {
            x = R.drawable.l0;
            x +=i;
            t[i] = BitmapFactory.decodeResource(this.getResources(), x);
            System.out.println(t[i]);

        }
        setContentView(R.layout.activity_main_menu);
    }
}
