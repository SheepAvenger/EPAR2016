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
        Level level = new Level(this);
        Scores s = new Scores(this);
        Character c = new Character(this);
        setContentView(R.layout.activity_main_menu);
    }
}
