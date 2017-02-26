package com.softwarei.epar2016;


import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;

    private long jumpButtonTime;
    private GameLoop gameLoop;
    private Background backGround;
    private Sprite sprite;

    public GameView(Context context) {
        super(context);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

    }
}
