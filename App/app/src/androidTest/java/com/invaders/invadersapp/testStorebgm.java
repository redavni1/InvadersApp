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

@RunWith(AndroidJUnit4.class)
public class testStorebgm {

    private StoreBGM storeBGM;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        storeBGM = StoreBGM.getInstance(context);
    }

    @After
    public void tearDown() {
        // Release any resources used by MediaPlayer
        storeBGM.storebgm_player.release();
    }

    @Test
    public void testStart() {
        // Check if the MediaPlayer is not playing initially
        assertFalse(storeBGM.storebgm_player.isPlaying());

        // Start playing
        storeBGM.start();

        // Check if the MediaPlayer is playing after start
        assertTrue(storeBGM.storebgm_player.isPlaying());
    }
}
