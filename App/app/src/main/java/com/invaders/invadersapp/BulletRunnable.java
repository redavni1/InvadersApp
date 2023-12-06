package com.invaders.invadersapp;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;



public class BulletRunnable implements Runnable {
    /** Handler to control runnable. */
    private Handler handlerBullet = new Handler(Looper.getMainLooper());
    /** ImageView of loaded bullet */
    private ImageView loadedBullet;

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
            loadedBullet.setVisibility(View.GONE);
            handlerBullet.removeCallbacks(this);
        } else {
            handlerBullet.postDelayed(this, 17); // fps = 1000/17
        }
    }
}
