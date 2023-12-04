package com.invaders.invadersapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

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
    }
    public float[] getPositionSides() { return new float[]{positionX, positionX + enemy.getWidth()}; }
    public float getPositionBottom() { return positionY + enemy.getHeight(); }
    public void destroy() {
        enemy.setVisibility(View.GONE);
    }
}
