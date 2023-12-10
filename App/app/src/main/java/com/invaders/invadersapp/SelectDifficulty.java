package com.invaders.invadersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class SelectDifficulty extends AppCompatActivity {
    /** Difficulty Button. */
    private Button easyButton, normalButton, hardButton, hardcoreButton, mainButton;
    /** Difficulty Title textview. */
    private TextView title;
    /** Runnable object for text blinking. */
    private BlinkingRunnable br;
    /** Object to control difficulty and level. */
    private DifficultyLevel difficultyLevel = new DifficultyLevel();
    private boolean isNextActivityButtonClick = false;

    private final OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            isNextActivityButtonClick = true;
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectdifficulty);

        this.getOnBackPressedDispatcher().addCallback(this, callback);

        title = findViewById(R.id.difficultytitle);
        easyButton = findViewById(R.id.button_easy);
        normalButton = findViewById(R.id.button_normal);
        hardButton = findViewById(R.id.button_hard);
        hardcoreButton = findViewById(R.id.button_hardcore);
        mainButton = findViewById(R.id.button_main);

        TextView[] textViews = new TextView[]{title, easyButton, normalButton, hardButton, hardcoreButton, mainButton};
        String[] colors = new String[]{"GREEN", "WHITE", "WHITE", "WHITE", "WHITE", "GREY"};

        br = new BlinkingRunnable(textViews, colors);
        Thread t = new Thread(br);
        t.start();

        // easy button -> level intent
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(1, "GREEN");
                difficultyLevel.setDifficulty("EASY");

                isNextActivityButtonClick = true;

                Intent intent = new Intent(getApplicationContext(), selectlevel.class);
                intent.putExtra("DifficultyLevel", difficultyLevel);
                startActivity(intent);
            }
        });
        // normal button -> level intent
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(1, "GREEN");

                isNextActivityButtonClick = true;

                difficultyLevel.setDifficulty("NORMAL");
                Intent intent = new Intent(getApplicationContext(), selectlevel.class);
                intent.putExtra("DifficultyLevel", difficultyLevel);
                startActivity(intent);
            }
        });
        // hard button -> level intent
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(1, "GREEN");
                difficultyLevel.setDifficulty("HARD");

                isNextActivityButtonClick = true;

                Intent intent = new Intent(getApplicationContext(), selectlevel.class);
                intent.putExtra("DifficultyLevel", difficultyLevel);
                startActivity(intent);
            }
        });
        // hard core button -> level intent
        hardcoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(1, "GREEN");
                difficultyLevel.setDifficulty("HARDCORE");

                isNextActivityButtonClick = true;

                Intent intent = new Intent(getApplicationContext(), selectlevel.class);
                intent.putExtra("DifficultyLevel", difficultyLevel);
                startActivity(intent);
            }
        });

        //go back to main when click 'mainbutton'
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(5, "GREEN");

                isNextActivityButtonClick = true;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }


    // Start playing game screen BGM when the activity starts
    @Override
    protected void onStart() {
        super.onStart();

        MainActivity.mBGMManager.mMediaPlayerForGameScreenBGM.start();
    }

    // Pause the game screen BGM when the activity stops, unless transitioning to another activity
    @Override
    protected void onStop() {
        super.onStop();

        if (!isNextActivityButtonClick) {
            MainActivity.mBGMManager.mMediaPlayerForGameScreenBGM.pause();
        }else {
            isNextActivityButtonClick = false;
        }
    }
}

