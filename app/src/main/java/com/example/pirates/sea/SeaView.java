package com.example.pirates.sea;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pirates.GameFieldActivity;
import com.example.pirates.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class SeaView extends SurfaceView implements SurfaceHolder.Callback {

    private SeaThread seaThread;
    private Random random;
    private Thread thread;
    private Ship ship;
    private ArrayList<Bomb> playerBombs = new ArrayList<>();
    private ArrayList<Bomb> enemiesBombs = new ArrayList<>();
    private ArrayList<SeaEnemy> enemies = new ArrayList<>();
    private final int[] picArray = new int[]{R.drawable.ship1, R.drawable.ship2, R.drawable.ship3};
    private float dragY = 0;
    private int count, countToWin;
    private AppCompatActivity appCompatActivity;

    public SeaView(Context context) {
        super(context);
        unit(context);
        startGame();
    }

    public SeaView(Context context, AttributeSet attributes) {
        super(context, attributes);
        unit(context);
        startGame();
    }

    private void unit(Context context) {
        getHolder().addCallback(this);
        random = new Random();
        appCompatActivity = ((AppCompatActivity) context);
        seaThread = new SeaThread(this);
        ship = new Ship(this, BitmapFactory.decodeResource(getResources(), R.drawable.ship_player));
    }

    private void startGame() {
        ship.setAlive(true);
        count = 0;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                fillArray(random.nextInt(3));
                while (ship.isAlive()) {
                    try {
                        Thread.sleep(random.nextInt(3000) + 1000);
                        fillArray(random.nextInt(3));
                        fillEnemiesBombArray(random.nextInt(enemies.size()));
                    } catch (InterruptedException e) {
                    }
                }
                stopGame();
            }
        });
        thread.start();
    }

    private void fillArray(int r) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), picArray[r]);
        enemies.add(new SeaEnemy(this, bitmap));
    }

    public void fillPlayerBombArray(int y) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        if (playerBombs.size() < 7 && ship.isAlive()) {
            playerBombs.add(new Bomb(this, bitmap, y));
        }
    }

    public void fillEnemiesBombArray(int r) {
        SeaEnemy seaEnemy = enemies.get(r);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        if (enemiesBombs.size() < 7) {
            Bomb bomb = new Bomb(this, bitmap, seaEnemy.getY() + 120);
            bomb.setSpeed(-30);
            bomb.setX(seaEnemy.getX());
            enemiesBombs.add(bomb);
        }
    }

    private void stopGame() {
        enemies.clear();
        playerBombs.clear();
        enemiesBombs.clear();
        thread.interrupt();
        Intent intent = new Intent(appCompatActivity, GameFieldActivity.class);
        intent.putExtra("isWin", count >= countToWin);
        appCompatActivity.setResult(RESULT_OK, intent);
        appCompatActivity.finish();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.blue));
        ship.onDraw(canvas);
        Iterator<Bomb> bulletIterator = playerBombs.iterator();
        while (bulletIterator.hasNext()) {
            Bomb bomb = bulletIterator.next();
            if (bomb.getX() >= getScreenWidthSea()) {
                bulletIterator.remove();
            }
            bomb.onDraw(canvas);
        }
        Iterator<SeaEnemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            SeaEnemy seaEnemy = enemyIterator.next();
            if (seaEnemy.getX() <= 0) {
                enemyIterator.remove();
                stopGame();
            }
            seaEnemy.onDraw(canvas);
        }
        Iterator<Bomb> enemyBombIterator = enemiesBombs.iterator();
        while (enemyBombIterator.hasNext()) {
            Bomb enemyBomb = enemyBombIterator.next();
            if (enemyBomb.getX() <= 0) {
                enemyBombIterator.remove();
            }
            enemyBomb.onDraw(canvas);
        }
        testCollisions();
    }

    private void testCollisions() {
        Iterator<SeaEnemy> seaEnemyIterator = enemies.iterator();
        while (seaEnemyIterator.hasNext()) {
            SeaEnemy seaEnemy = seaEnemyIterator.next();
            if (ship.isCollision(seaEnemy.getX(), seaEnemy.getY() + seaEnemy.getHeight() / 2)) {
                seaEnemyIterator.remove();
                ship.setAlive(false);
                ship.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.explosion));
                stopGame();
            }
            Iterator<Bomb> playerBombIterator = playerBombs.iterator();
            while (playerBombIterator.hasNext()) {
                Bomb bomb = playerBombIterator.next();
                if (seaEnemy.isCollision(bomb.getX() + bomb.getWidth(), bomb.getY())) {
                    playerBombIterator.remove();
                    seaEnemyIterator.remove();
                    count++;
                    if (count >= countToWin) {
                        stopGame();
                    }
                }
            }
            Iterator<Bomb> enemiesBombIterator = enemiesBombs.iterator();
            while (enemiesBombIterator.hasNext()) {
                Bomb bomb = enemiesBombIterator.next();
                if (ship.isCollision(bomb.getX() + bomb.getWidth(), bomb.getY())) {
                    ship.setAlive(false);
                    ship.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.explosion));
                    enemiesBombIterator.remove();
                    stopGame();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (ship.isAlive()) {
            float evY = event.getY();
            synchronized (getHolder()) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ship.setDrag(true);
                        dragY = (int) (evY - ship.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (ship.isDrag()) {
                            int z = (int) (evY - dragY);
                            if (z > -30 && z < getScreenHeightSea() - ship.getHeight() + 30) {
                                ship.setY((int) (evY - dragY));
                                invalidate();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        ship.setDrag(false);
                        break;
                }

            }
        }
        return true;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        seaThread.setRunning(true);
        seaThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        seaThread.setRunning(false);
        while (retry) {
            try {
                seaThread.join();
                retry = false;
            } catch (Exception e) {
            }
        }
    }

    public int getScreenWidthSea() {
        return this.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeightSea() {
        return this.getResources().getDisplayMetrics().heightPixels;
    }

    public void setCountToWin(int countToWin) {
        this.countToWin = countToWin;
    }
}