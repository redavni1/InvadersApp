package com.invaders.invadersapp.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.invaders.invadersapp.R;

import android.content.Context;
import android.media.MediaPlayer;

public class StoreBGM {
    private static StoreBGM storebgm;

    public MediaPlayer storebgm_player;
    private Context context;

    // Private constructor to prevent direct instantiation
    private StoreBGM(Context context) {
        this.context = context;
        storebgm_player = MediaPlayer.create(context, R.raw.store_bgm);

        // Check if MediaPlayer creation was successful
        if (storebgm_player == null) {
            throw new IllegalStateException("Failed to create MediaPlayer for store_bgm");
        }

        storebgm_player.setLooping(true);  // Set background music to loop
    }

    // Singleton pattern to ensure only one instance exists
    public static StoreBGM getInstance(Context context) {
        if (storebgm == null) { //when storebgm instance is null
            storebgm = new StoreBGM(context); //newly allocate storebgm instance
        }
        return storebgm;
    }

    // Start playing the background music
    public void start() {
        if (!storebgm_player.isPlaying()) {
            storebgm_player.start();
        }
    }

    public void stop() {
        if (storebgm_player.isPlaying()) {
            storebgm_player.stop();
        }
        storebgm_player.release();
        storebgm_player = null;
        storebgm = null; // Reset the instance to allow recreation if needed
    }
}

