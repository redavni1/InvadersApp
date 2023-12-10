package com.invaders.invadersapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that does the ship move by a distance of 8 when method move() called.
 */
public class MovingRunnableTest {
    @Test
    public void shipMovingTest() {
        // Set up.
        float xPosition = 504;
        MovingRunnable movingLeft = new MovingRunnable("LEFT");
        MovingRunnable movingRight = new MovingRunnable("RIGHT");

        movingLeft.setShipXPosition(xPosition);

        // Moving left.
        movingLeft.move();
        assertEquals(movingLeft.getShipXPosition(), xPosition - 8, 0.000001);
        xPosition = movingLeft.getShipXPosition();
        for (int i = (int) xPosition; i > 0; i -= 8) {
            movingLeft.move();
            xPosition -= 8;
        }
        assertEquals(movingLeft.getShipXPosition(), xPosition, 0.000001);
        movingLeft.move();
        assertEquals(movingLeft.getShipXPosition(), 0, 0.000001);

        xPosition = movingLeft.getShipXPosition();
        movingRight.setShipXPosition(xPosition);

        // Moving right.
        movingRight.move();
        assertEquals(movingRight.getShipXPosition(), xPosition + 8, 0.000001);
        xPosition = movingRight.getShipXPosition();
        for (int i = (int) xPosition; i < 1008; i += 8) {
            movingRight.move();
            xPosition += 8;
        }
        assertEquals(movingRight.getShipXPosition(), xPosition, 0.000001);
        movingRight.move();
        assertEquals(movingRight.getShipXPosition(), 1008, 0.000001);
    }
}
