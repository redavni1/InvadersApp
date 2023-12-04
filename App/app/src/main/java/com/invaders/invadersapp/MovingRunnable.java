package com.invaders.invadersapp;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class MovingRunnable extends InGame implements Runnable{
    private ImageView icon;
    private float distance;
    private float edge;

    public MovingRunnable(ImageView i) {
        icon = i;
        if (icon.getId() == (int) 1000024) { // if i.getId() == R.id.left_icon
            distance = -8;
            edge = 0;
        } else {
            distance = 8;
            edge = 1008;
        }
    }
    /** Handler to control runnable */
    public Handler movingHandler = new Handler(Looper.getMainLooper());
    /** Runnable to move ship */
    @Override
    public void run() {
        if (ship.getX()+distance > 0) ship.setX(ship.getX()+distance);
        else ship.setX(edge);
        movingHandler.postDelayed(this, 17); // fps = 1000/17
    }
}
