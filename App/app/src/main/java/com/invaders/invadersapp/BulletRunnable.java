package com.invaders.invadersapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;



public class BulletRunnable extends InGame implements Runnable {
    public Handler handlerBullet = new Handler(Looper.getMainLooper());
    private ImageView loadedBullet;
    public BulletRunnable(ImageView b) {
        loadedBullet = b;
    }

    @Override
    public void run() {
        loadedBullet.setY(loadedBullet.getY()-16);
        if(loadedBullet.getY() < 0) {
            loadedBullet.setImageResource(0);
            handlerBullet.removeCallbacks(this);
        } else {
            handlerBullet.postDelayed(this, 17);
        }
    }
}
