package com.invaders.invadersapp;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.util.List;


public class BulletRunnable extends InGame implements Runnable {
    /** Handler to control runnable. */
    private Handler handlerBullet = new Handler(Looper.getMainLooper());
    /** ImageView of loaded bullet */
    private ImageView loadedBullet;
    private List<Enemy> enemyList;

    /**
     * Initialize b's BulletRunnable.
     *
     * @param b Loaded bullet.
     */
    public BulletRunnable(ImageView b) {
        // Set parameter to loadedBullet.
        loadedBullet = b;
    }
    /** Start run to shoot bullet. */
    @Override
    public void run() {
        loadedBullet.setY(loadedBullet.getY()-16);
        if(loadedBullet.getY() < 0) {
            loadedBullet.setImageResource(0);
            handlerBullet.removeCallbacks(this);
        } else if (checkCollision()) {
            loadedBullet.setImageResource(0);
            handlerBullet.removeCallbacks(this);
        } else {
            handlerBullet.postDelayed(this, 17); // fps = 1000/17
        }
    }
    public void setEnemyList(List<Enemy> list) {
        enemyList = list;
    }

    private boolean checkCollision() {
        float x = loadedBullet.getX();
        float y = loadedBullet.getY();
        for (Enemy enemy : enemyList) {
            if (x > enemy.getPositionSides()[0] && x < enemy.getPositionSides()[1] && y < enemy.getPositionBottom()) {
                enemyList.remove(enemy);
                enemy.destroy();
                return true;
            }
        }
        return false;
    }
}
