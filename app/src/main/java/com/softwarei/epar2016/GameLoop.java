package com.softwarei.epar2016;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    private int FPS = 60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running, pause;
    private static Canvas canvas;

    public GameLoop(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }
    @Override
    public void run() {
        int frameCount = 0;
        long startTime, timeMillis, waitTime, totalTime, targetTime;
        totalTime = 0;
        targetTime = 1000/FPS;

        while(running) {
            //if(!pause) {
                startTime = System.nanoTime();
                canvas = null;

                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        this.gameView.update();
                        this.gameView.draw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                timeMillis = (System.nanoTime() - startTime) / 1000000;
                waitTime = targetTime - timeMillis;
                if (waitTime >= 0) {
                    try {
                        sleep(waitTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                totalTime += System.nanoTime() - startTime;
                frameCount++;
                if (frameCount == FPS) {
                    averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                    frameCount = 0;
                    totalTime = 0;
                    //System.out.println(averageFPS);
                }
            //}
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

//    public void setPause(boolean pause) {
//        this.pause = pause;
//    }

}
