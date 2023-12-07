package com.invaders.invadersapp;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;



public class BulletRunnable extends InGame implements Runnable {
    /** Handler to control runnable. */
    private Handler bulletHandler = new Handler(Looper.getMainLooper());
    /** ImageView of loaded bullet */
    private ImageView loadedBullet;
    private EnemyFormation enemyFormation;


    /**
     * Initialize b's BulletRunnable.
     *
     * @param b Loaded bullet.
     */
    public BulletRunnable(ImageView b, EnemyFormation e) {
        // Set parameter to loadedBullet.
        loadedBullet = b;
        enemyFormation = e;
    }
    /** Start run to shoot bullet. */
    @Override
    public void run() {
        loadedBullet.setY(loadedBullet.getY()-16);
        if(loadedBullet.getY() < 200) {
            loadedBullet.setVisibility(View.GONE);
            bulletHandler.removeCallbacks(this);
        } else if (checkCollision()) {
            loadedBullet.setVisibility(View.GONE);
            bulletHandler.removeCallbacks(this);
        } else {
            bulletHandler.postDelayed(this, 17); // fps = 1000/17
        }
    }
    private boolean checkCollision() {
        float x = loadedBullet.getX();
        float y = loadedBullet.getY();
        for (int i=0; i<enemyFormation.size(); i++) {
            for (Enemy enemy : enemyFormation.getOneList(i)) {
                if (x+loadedBullet.getWidth() > enemy.getPositionSides()[0] && x < enemy.getPositionSides()[1] && y < enemy.getPositionTopBottom()[1]) {
                    enemyFormation.removeEnemy(enemy, i);
                    enemy.destroy();
                    return true;
                }
            }
        }
        return false;
    }
    public void stop() {
        loadedBullet.setVisibility(View.GONE);
        bulletHandler.removeCallbacks(this);
    }
}
