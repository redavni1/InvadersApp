package com.invaders.invadersapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class InGame extends AppCompatActivity {
    /** ImageView of ship */
    public ImageView ship;
    private ImageView leftIcon;
    private ImageView rightIcon;
    private TextView shootBtn;
    private LinkedList<ImageView> bullets = new LinkedList<>();;
    private ImageView bullet1;
    private ImageView bullet2;
    private Map<ImageView, BulletRunnable> runnableMap;
    private ImageView loadedBullet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        leftIcon.setImageResource(R.drawable.left_button);
        rightIcon.setImageResource(R.drawable.right_button);

        MovingRunnable movingLeft = new MovingRunnable(leftIcon);
        MovingRunnable movingRight = new MovingRunnable(rightIcon);


        ship = (ImageView) findViewById(R.id.ship);
        shootBtn = (TextView) findViewById(R.id.shoot);
        // set img on left and right buttons


        shootBtn.setTextColor(Color.WHITE);

        bullet1 = (ImageView) findViewById(R.id.bullet1);
        bullet2 = (ImageView) findViewById(R.id.bullet2);

        runnableMap = new HashMap<ImageView, BulletRunnable>() {{
            put(bullet1, new BulletRunnable(bullet1));
            put(bullet2, new BulletRunnable(bullet2));
        }};

        bullets.add(bullet1);
        bullets.add(bullet2);

        leftIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // change left button green when it touched
                    leftIcon.setImageResource(R.drawable.left_touched);
                    movingLeft.movingHandler.post(movingLeft);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // change left button white
                    leftIcon.setImageResource(R.drawable.left_button);
                    movingLeft.movingHandler.removeCallbacks(movingLeft);
                }
                return true;
            }
        });

        rightIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // change right button green when it touched
                    rightIcon.setImageResource(R.drawable.right_touched);
                    movingRight.movingHandler.post(movingRight);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // change right button white
                    rightIcon.setImageResource(R.drawable.right_button);
                    movingRight.movingHandler.removeCallbacks(movingRight);
                }
                return true;
            }
        });

        shootBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadedBullet = bullets.poll();
                loadedBullet.setImageResource(R.drawable.bullet);
                shootBtn.setTextColor(Color.GRAY);
                shootBtn.setEnabled(false);
                loadedBullet.setX(ship.getX()+ship.getWidth()/2-loadedBullet.getWidth()/2);
                loadedBullet.setY(ship.getY()+2);
                runnableMap.get(loadedBullet).run();
                bullets.add(loadedBullet);
                handlerShooting.postDelayed(shootingCooldown, 1000);
            }
        });
    }

    private Handler handlerShooting = new Handler(Looper.getMainLooper());
    private Runnable shootingCooldown = new Runnable() {
        @Override
        public void run() {
            shootBtn.setTextColor(Color.WHITE);
            shootBtn.setEnabled(true);
        }
    };
}
