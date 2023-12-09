package com.invaders.invadersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class selectlevel extends AppCompatActivity {
    Intent intent;
    /** Previously generated object to control difficulty and level. */
    private DifficultyLevel difficultyLevel;
    private BlinkingRunnable br;
    private TextView select_level;
    Button btnLevel1, btnLevel2, btnLevel3, btnLevel4,btnLevel5, btnLevel6, btnLevel7, btnmain;
    public int level;
    public boolean isNextActivityButtonClick = false;

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
        setContentView(R.layout.activity_selectlevel);

        intent = getIntent();
        difficultyLevel = (DifficultyLevel) intent.getSerializableExtra("DifficultyLevel", DifficultyLevel.class);


        this.getOnBackPressedDispatcher().addCallback(this, callback);

        select_level = (TextView) findViewById(R.id.leveltitle);
        btnLevel1 = findViewById(R.id.btnLevel1);
        btnLevel2 = findViewById(R.id.btnLevel2);
        btnLevel3 = findViewById(R.id.btnLevel3);
        btnLevel4 = findViewById(R.id.btnLevel4);
        btnLevel5 = findViewById(R.id.btnLevel5);
        btnLevel6 = findViewById(R.id.btnLevel6);
        btnLevel7 = findViewById(R.id.btnLevel7);
        btnmain = findViewById(R.id.btnmain);

        //setting for Blinking function
        TextView[] textViews = new TextView[]{select_level, btnLevel1, btnLevel2, btnLevel3, btnLevel4, btnLevel5, btnLevel6, btnLevel7, btnmain};
        String[] colors = new String[]{"GREEN","GREY", "WHITE", "WHITE", "WHITE", "WHITE", "GREY","WHITE","GREY"};

        //Blinking starts.
        br = new BlinkingRunnable(textViews, colors);
        Thread t = new Thread(br);
        t.start();


        btnLevel1.setOnClickListener(v -> levelSelectEvent(1));
        btnLevel2.setOnClickListener(v -> levelSelectEvent(2));
        btnLevel3.setOnClickListener(v -> levelSelectEvent(3));
        btnLevel4.setOnClickListener(v -> levelSelectEvent(4));
        btnLevel5.setOnClickListener(v -> levelSelectEvent(5));
        btnLevel6.setOnClickListener(v -> levelSelectEvent(6));
        btnLevel7.setOnClickListener(v -> levelSelectEvent(7));

        //go back to main when click 'mainbutton'
        btnmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                br.changeColor(8, "GREEN");

                isNextActivityButtonClick = true;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void levelSelectEvent(int level) {
        this.level = level;
        br.changeColor(level, "GREEN");
        difficultyLevel.setLevel(level);
        isNextActivityButtonClick = true;
        MainActivity.mBGMManager.mMediaPlayerForGameScreenBGM.pause();
        Intent intent = new Intent(getApplicationContext(), InGame.class);
        intent.putExtra("Level", difficultyLevel);
        startActivity(intent);
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