package com.invaders.invadersapp;

import android.graphics.Bitmap;
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
import java.util.Map;





public class InGame extends AppCompatActivity {
    /** ImageView of ship. */
    private ImageView ship;
    /** ImageView of left icon. */
    private ImageView leftIcon;
    /** ImageView of right icon. */
    private ImageView rightIcon;
    /** TextView of "S H O O T" button. */
    private TextView shootBtn;
    /** LinkedList for reusing bullets. */
    private LinkedList<ImageView> bullets = new LinkedList<>();
    /** ImageView of bullet1, 2. */
    private ImageView bullet1;
    private ImageView bullet2;
    /** Bullet's width. */
    private float bulletWidth = 17;
    /** Map for linking bullet and bullet's runnable. */
    private Map<ImageView, BulletRunnable> runnableMap;
    /** Temporary ImageView for shot bullet. */
    private ImageView loadedBullet;

    private MediaPlayer inGameMediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        // MainActivity BGM STOP and release resources
        if (MainActivity.mBGMManager != null && MainActivity.mBGMManager.mMediaPlayerForGameScreenBGM.isPlaying()) {
            MainActivity.mBGMManager.mMediaPlayerForGameScreenBGM.stop();
            MainActivity.mBGMManager.mMediaPlayerForGameScreenBGM.release();
        }

        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        leftIcon.setImageResource(R.drawable.left_button);
        rightIcon.setImageResource(R.drawable.right_button);

        ship = (ImageView) findViewById(R.id.ship);
        // Initialize direction icons' runnable.
        MovingRunnable movingLeft = new MovingRunnable(leftIcon, ship);
        MovingRunnable movingRight = new MovingRunnable(rightIcon, ship);

        shootBtn = (TextView) findViewById(R.id.shoot);
        // Initialize shoot button's color white.
        shootBtn.setTextColor(Color.WHITE);

        bullet1 = (ImageView) findViewById(R.id.bullet1);
        bullet2 = (ImageView) findViewById(R.id.bullet2);
        bullet1.setVisibility(View.GONE);
        bullet2.setVisibility(View.GONE);

        // Initialize runnableMap for linking bullet ImageView and their runnable.
        runnableMap = new HashMap<ImageView, BulletRunnable>() {{
            put(bullet1, new BulletRunnable(bullet1));
            put(bullet2, new BulletRunnable(bullet2));
        }};
        // Add bullets into their LinkedList for reusing.
        bullets.add(bullet1);
        bullets.add(bullet2);

        leftIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Change left button's color green and ship's position to left when it touched.
                    leftIcon.setImageResource(R.drawable.left_touched);
                    movingLeft.movingHandler.post(movingLeft);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Change left button's color white and Stop ship's moving when it stops being touched.
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
                    // Change right button's color green and ship's position to right when it touched.
                    rightIcon.setImageResource(R.drawable.right_touched);
                    movingRight.movingHandler.post(movingRight);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Change left button's color white and Stop ship's moving when it stops being touched.
                    rightIcon.setImageResource(R.drawable.right_button);
                    movingRight.movingHandler.removeCallbacks(movingRight);
                }
                return true;
            }
        });

        shootBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load bullet that is head of the list.
                loadedBullet = bullets.poll();
                // Set loaded bullet's image resource.
                loadedBullet.setVisibility(View.VISIBLE);
                // Change shootbtn's color gray and Set it disable.
                shootBtn.setTextColor(Color.GRAY);
                shootBtn.setEnabled(false);
                // Initialize loaded bullet's position.
                loadedBullet.setX(ship.getX() + ((float) ship.getWidth() - bulletWidth)/2);
                loadedBullet.setY(ship.getY());
                // Run loaded bullet's runnable for shooting.
                runnableMap.get(loadedBullet).run();
                // Add bullet to last of the list.
                bullets.add(loadedBullet);
                // Set shootbtn's color white and Set it enable again one second later.
                handlerShooting.postDelayed(shootingCoolDown, 1000);
            }
        });
    }

    /** Handler to control shooting cool down runnable. */
    private Handler handlerShooting = new Handler(Looper.getMainLooper());
    /** Runnable for cool down. */
    private Runnable shootingCoolDown = new Runnable() {
        @Override
        public void run() {
            // Set shootbtn's color white and Set it enable.
            shootBtn.setTextColor(Color.WHITE);
            shootBtn.setEnabled(true);
        }
    };
}
