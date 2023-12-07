package com.invaders.invadersapp;

import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

public class MovingRunnable extends InGame implements Runnable{
    /** ImageView of this runnable. */
    private ImageView icon;
    /** ImageView of ship */
    private ImageView ship;
    /** Ship's moving distance by a frame. */
    private float distance;
    /** Edge's X location(left : 0/right : 1008). */
    private float edge;
    /** Handler to repeat runnable. */
    private Handler movingHandler = new Handler(Looper.getMainLooper());

    /**
     * Initialize i's MovingRunnable.
     *
     * @param i ImageView of this runnable.
     */
    public MovingRunnable(ImageView i, ImageView s) {
        // Set i to icon.
        icon = i;
        // Set s to ship
        ship = s;
        // Set ship's distance and edge's X location.
        if (icon.getId() == R.id.left_icon) { // if i is left icon
            distance = -8;
            edge = 0;
        } else {
            distance = 8;
            edge = 1008;
        }
    }
    /**
     * Start run to move ship.
     */
    @Override
    public void run() {
        float nextX = ship.getX()+distance;
        if (nextX > 0 && nextX < 1008) ship.setX(nextX);
        else ship.setX(edge);
        movingHandler.postDelayed(this, 17); // fps = 1000/17
    }

    /**
     * Stop moving.
     */
    public void stop() {
        movingHandler.removeCallbacks(this);
    }
}
