package com.invaders.invadersapp.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.invaders.invadersapp.R;

/**
 * This class represents the background music player for the store in the Invaders app.
 * It follows the singleton pattern to ensure only one instance exists.
 */
public class StoreBGM {
    /** The single instance of the StoreBGM class. */
    public static StoreBGM storebgm;

    /** The MediaPlayer responsible for playing the store background music. */
    public MediaPlayer storebgm_player;

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the MediaPlayer and sets it to looping mode.
     *
     * @param player The MediaPlayer instance for background music.
     */
    private StoreBGM(MediaPlayer player) {
        this.storebgm_player = player;
        storebgm_player.setLooping(true);
    }

    /**
     * Retrieves the singleton instance of the StoreBGM class.
     * Creates a new instance if it does not exist yet.
     *
     * @param context The context used to create the MediaPlayer.
     * @return The StoreBGM instance.
     */
    public static StoreBGM getInstance(Context context) {
        if (storebgm == null) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.store_bgm);
            storebgm = new StoreBGM(mediaPlayer);
        }
        return storebgm;
    }

    /**
     * Starts playing the background music if it is not already playing.
     */
    public void start() {
        if (!storebgm_player.isPlaying()) {
            storebgm_player.start();
        }
    }
}
