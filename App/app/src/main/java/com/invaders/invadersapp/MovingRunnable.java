package com.invaders.invadersapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovingRunnable implements Runnable{
    /** Map of direction and location info. */
    private Map<String, List<Float>> direction = new HashMap<String, List<Float>>() {{
        put("LEFT", Arrays.asList((float) -8, (float) 0));
        put("RIGHT", Arrays.asList((float) 8, (float) 1008));
    }};
    /** ImageView of ship. */
    private ImageView ship;
    /** Ship's X position. */
    private float shipXPosition;
    /** Ship's moving distance by a frame. */
    private float distance;
    /** Edge's X location(left : 0/right : 1008). */
    private float edge;
    /** Handler to repeat runnable. */
    private Handler movingHandler = new Handler();

    /**
     * Initialize i's MovingRunnable.
     *
     * @param dir String of this runnable direction.
     */
    public MovingRunnable(String dir) {
        // Set ship's distance and edge's X location.
        distance = direction.get(dir).get(0);
        edge = direction.get(dir).get(1);
    }

    /**
     * Set ship and shipXPosition.
     *
     * @param s ImageView of ship.
     */
    public void setShip(ImageView s) {
        ship = s;
        setShipXPosition(ship.getX());
    }

    /**
     * Set shipXPosition x.
     *
     * @param x position x.
     */
    public void setShipXPosition(float x) {
        shipXPosition = x;
    }

    /**
     * Return shipXPosition.
     *
     * @return Float of shipXPosition.
     */
    public float getShipXPosition() {
        return shipXPosition;
    }
    /**
     * Update shipXPosition according direction.
     */
    public void move() {
        float nextX = shipXPosition + distance;
        if (nextX > 0 && nextX < 1008) setShipXPosition(nextX);
        else setShipXPosition(edge);
    }

    /**
     * Start run to move ship.
     */
    @Override
    public void run() {
        setShipXPosition(ship.getX());
        move();
        ship.setX(shipXPosition);
        movingHandler.postDelayed(this, 17); // fps = 1000/17
    }

    /**
     * Stop moving.
     */
    public void stop() {
        movingHandler.removeCallbacks(this);
    }
}
