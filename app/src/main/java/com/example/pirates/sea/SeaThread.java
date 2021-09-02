package com.example.pirates.sea;

import android.graphics.Canvas;

public class SeaThread extends Thread {
    private SeaView view;
    private boolean running = false;
    private static final long FPS = 10;

    public SeaThread(SeaView view) {
        this.view = view;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } catch (Exception e) {

            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(3);
            } catch (Exception e) {
            }
        }
    }
}
