package com.invaders.invadersapp.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.invaders.invadersapp.R;

public class StoreBGM {
    public static StoreBGM storebgm;
    public MediaPlayer storebgm_player;

    // Private constructor to prevent direct instantiation
    public StoreBGM(MediaPlayer player) {
        this.storebgm_player = player;
        storebgm_player.setLooping(true);
    }

    // Singleton pattern to ensure only one instance exists
    public static StoreBGM getInstance(Context context) {
        if (storebgm == null) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.store_bgm);
            storebgm = new StoreBGM(mediaPlayer);
        }
        return storebgm;
    }

    // Start playing the background music
    public void start() {
        if (!storebgm_player.isPlaying()) {
            storebgm_player.start();
        }
    }
}
