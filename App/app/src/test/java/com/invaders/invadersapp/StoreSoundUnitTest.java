package com.invaders.invadersapp;

import android.content.Context;
import android.media.MediaPlayer;

import com.invaders.invadersapp.R;
import com.invaders.invadersapp.sound.StoreBGM;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;



public class StoreSoundUnitTest {
    @Mock
    Context mockContext;

    @Mock
    MediaPlayer mockMediaPlayer;

    @InjectMocks
    private StoreBGM storebgmMock;

    public void setup(){
        MockitoAnnotations.openMocks(this);
        when(mockContext.getResources()).thenReturn(mockContext.getResources()); // Mock resources if needed
        when(mockMediaPlayer.isPlaying()).thenReturn(false); // Set initial state to not playing
        when(mockMediaPlayer.create(any(), anyInt())).thenReturn(mockMediaPlayer); // Mock MediaPlayer creation
    }

    public void tearDown(){
        storebgmMock.stop();
    }

    public void Test_startmethod(){
        storebgmMock.start();
        verify(mockMediaPlayer).start();
    }

    public void Test_stopmethod(){
        storebgmMock.stop();
        verify(mockMediaPlayer).stop();
        verify(mockMediaPlayer).release();
    }
}

