package com.invaders.invadersapp;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;

public class Enemy {
    /** ImageView of enemy. */
    private ImageView enemy;
    /** ID of this enemy's ImageView. */
    private int id;
    /** X coordination of enemy. */
    private float positionX;
    /** Y coordination of enemy. */
    private float positionY;
    /** Enemy's drawables  of changing appearances */
    private LinkedList<Integer> drawables;
    /** Score assigned to this enemy. */
    private int score;
    /** Context of InGame class. */
    Context mContext;

    /**
     * Initialize enemy object.
     *
     * @param i ID of this enemy's ImageView.
     * @param d1 Drawable1 of this enemy.
     * @param d2 Drawable2 of this enemy.
     * @param x X coordination of this enemy.
     * @param y Y coordination of this enemy.
     * @param s Score assigned to this enemy.
     */
    public Enemy(int i, int d1, int d2, float x, float y, int s) {
        id = i;
        drawables = new LinkedList<>();
        drawables.add(d1);
        drawables.add(d2);
        drawables.add(drawables.poll());
        positionX = x;
        positionY = y;
        score = s;
    }

    /**
     * Set context.
     *
     * @param context Context of InGame class.
     */
    public void setAboutContext(Context context) {
        mContext = context;
        enemy = (ImageView) ((InGame) mContext).findViewById(id);
        enemy.setImageResource(drawables.getFirst());
        enemy.setVisibility(View.GONE);
    }

    /**
     * Set this enemy's position and it visible.
     */
    public void setVisible() {
        enemy.setX(positionX);
        enemy.setY(positionY);
        enemy.setVisibility(View.VISIBLE);
        enemyHandler.postDelayed(changeDrawableRunnable, 1500);
    }

    /**
     * Return this enemy's X coordination considered its width.
     *
     * @return X coordinations of this enemy's each side.
     */
    public float[] getPositionSides() { return new float[]{ positionX, positionX + enemy.getWidth() }; }

    /**
     * Return this enemy's Y coordination considered its height.
     *
     * @return Y coordinations of this enemy's top and bottom.
     */
    public float[] getPositionTopBottom() { return new float[]{ positionY, positionY + enemy.getHeight() }; }

    /**
     * Set this enemy destroyed.
     */
    public void destroy() {
        ((InGame) mContext).plusScore(score);
        stopMoving();
        enemy.setImageResource(getExplosionDrawable());
        enemyHandler.postDelayed(explosionRunnable, 500);
        if (((InGame) mContext).enemyFormationIsEmpty()) ((InGame) mContext).gameOver();
    }

    /**
     * Handler to control enemy's runnable.
     */
    private Handler enemyHandler = new Handler();

    /**
     * Poll drawable from drawables and add it.
     *
     * @return drawable polled.
     */
    public int getNextDrawable() {
        int next = drawables.poll();
        drawables.add(next);
        return next;
    }

    /**
     * Runnable for change drawable.
     */
    private Runnable changeDrawableRunnable = new Runnable() {
        @Override
        public void run() {
            enemy.setImageResource(getNextDrawable());
            enemyHandler.postDelayed(this, 1500);
        }
    };
    public int getExplosionDrawable() {
        return R.drawable.explosion;
    }

    /**
     * Runnable for enemy's explosion.
     */
    private Runnable explosionRunnable = new Runnable() {
        @Override
        public void run() {
            enemy.setVisibility(View.GONE);
            enemyHandler.removeCallbacks(this);
        }
    };

    /**
     * Stop movingRunnable.
     */
    public void stopMoving() { enemyHandler.removeCallbacks(changeDrawableRunnable); }
}
