package com.softwarei.epar2016;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            Scores s = new Scores(this);
        }catch (IOException e)
        {

        }


        setContentView(R.layout.activity_main_menu);
    }
}
