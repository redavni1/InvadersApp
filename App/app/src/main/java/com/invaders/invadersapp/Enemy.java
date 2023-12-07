package com.invaders.invadersapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;

public class Enemy extends InGame {
    private ImageView enemy;
    private float positionX;
    private float positionY;
    private LinkedList<Integer> drawables;
    private int score;
    Context mContext;

    public Enemy(Context context, int id, int d1, int d2, float x, float y, int s) {
        mContext = context;
        enemy = (ImageView) ((InGame) mContext).findViewById(id);
        drawables = new LinkedList<>();
        drawables.add(d1);
        drawables.add(d2);
        enemy.setImageResource(drawables.getFirst());
        drawables.add(drawables.poll());
        positionX = x;
        positionY = y;
        score = s;
        enemy.setVisibility(View.GONE);
    }
    public void setVisible() {
        enemy.setX(positionX);
        enemy.setY(positionY);
        enemy.setVisibility(View.VISIBLE);
        enemyHandler.postDelayed(movingRunnable, 1500);
    }
    public float[] getPositionSides() { return new float[]{ positionX, positionX + enemy.getWidth() }; }
    public float[] getPositionTopBottom() { return new float[]{ positionY, positionY + enemy.getHeight() }; }
    public void destroy() {
        ((InGame) mContext).plusScore(score);
        stopMoving();
        enemy.setImageResource(R.drawable.explosion);
        enemyHandler.postDelayed(explosionRunnable, 500);
        if (((InGame) mContext).enemyFormationIsEmpty()) ((InGame) mContext).gameOver();
    }
    public float distanceX = 200;
    public float distanceY = 0;
    private Handler enemyHandler = new Handler(Looper.getMainLooper());
    private Runnable movingRunnable = new Runnable() {
        @Override
        public void run() {
//            enemy.setX(enemy.getX()+distanceX);
//            enemy.setY(enemy.getY()+distanceY);
            enemy.setImageResource(drawables.getFirst());
            drawables.add(drawables.poll());
            enemyHandler.postDelayed(this, 1500);
        }
    };
    private Runnable explosionRunnable = new Runnable() {
        @Override
        public void run() {
            enemy.setVisibility(View.GONE);
            enemyHandler.removeCallbacks(this);
        }
    };
    public void stopMoving() { enemyHandler.removeCallbacks(movingRunnable); }
}
