package com.invaders.invadersapp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import java.util.logging.Logger;

public class Enemy extends InGame {
    private ImageView enemy;
    private float positionX;
    private float positionY;
    Context mcontext;

    public Enemy(Context context, int id, int drawable, float x, float y) {
        mcontext = context;
        enemy = (ImageView) ((InGame) mcontext).findViewById(id);
        enemy.setImageResource(drawable);
        positionX = x;
        positionY = y;
        enemy.setVisibility(View.GONE);
    }
    public void setVisible() {
        enemy.setX(positionX);
        enemy.setY(positionY);
        enemy.setVisibility(View.VISIBLE);
        //handlerMoving.post(movingRunnable);
    }
    public float[] getPositionSides() { return new float[]{ positionX, positionX + enemy.getWidth() }; }
    public float[] getPositionTopBottom() { return new float[]{ positionY, positionY + enemy.getHeight() }; }
    public void destroy() {
        //handlerMoving.removeCallbacks(movingRunnable);
        enemy.setVisibility(View.GONE);

    }
    public float distanceX = 200;
    public float distanceY = 0;
    private Handler handlerMoving = new Handler(Looper.getMainLooper());
    private Runnable movingRunnable = new Runnable() {
        @Override
        public void run() {
            enemy.setX(enemy.getX()+distanceX);
            enemy.setY(enemy.getY()+distanceY);
            handlerMoving.postDelayed(this, 1000);
        }
    };

}
