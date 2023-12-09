package com.invaders.invadersapp;


import android.content.Context;
import android.media.MediaPlayer;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.invaders.invadersapp.sound.StoreBGM;

/**
 * Test Code: testStorebgm
 * This code tests the start() method of the StoreBGM class.
 */
@RunWith(AndroidJUnit4.class)
public class testStorebgm {

    /** The target StoreBGM object for testing. */
    private StoreBGM storeBGM;

    /**
     * Method called before each test method execution.
     * Initializes the StoreBGM object.
     */
    @Before
    public void setUp() {
        // Create a StoreBGM object using the application's Context
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        storeBGM = StoreBGM.getInstance(context);
    }

    /**
     * Method called after each test method execution.
     * Releases MediaPlayer resources.
     */
    @After
    public void tearDown() {
        // Release MediaPlayer resources
        storeBGM.storebgm_player.release();
    }

    /**
     * Test method for the start() method of the StoreBGM class.
     * Checks if MediaPlayer is not playing initially, starts it using the start() method,
     * and then checks if it is playing after the start.
     */
    @Test
    public void testStart() {
        // Check if MediaPlayer is not playing initially
        assertFalse(storeBGM.storebgm_player.isPlaying());

        // Start playing using the start() method
        storeBGM.start();

        // Check if MediaPlayer is playing after the start
        assertTrue(storeBGM.storebgm_player.isPlaying());
    }
}
