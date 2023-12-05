package com.invaders.invadersapp;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class InGame extends AppCompatActivity {
    Intent intent;
    private DifficultyLevel difficultyLevel;



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
    private EnemyFormation enemyFormation;
    private ImageView enemyBullet;
    private int remainLives = 3;
    private TextView livesTextView;
    private ImageView[] lifeIcons;
    private TextView gameOverTextView;
    private int score = 0;
    private TextView scoreTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        intent = getIntent();
        difficultyLevel = (DifficultyLevel) intent.getSerializableExtra("Level", DifficultyLevel.class);

        gameOverTextView = (TextView) findViewById(R.id.gameover);
        gameOverTextView.setVisibility(View.GONE);
        gameOverTextView.setTextColor(Color.WHITE);

        scoreTextView = (TextView) findViewById(R.id.score);
        scoreTextView.setText(score+"");

        livesTextView = (TextView) findViewById(R.id.livesText);
        livesTextView.setTextColor(Color.WHITE);
        livesTextView.setText(remainLives+"");
        lifeIcons = new ImageView[] {
                (ImageView) findViewById(R.id.life1),
                (ImageView) findViewById(R.id.life2),
                (ImageView) findViewById(R.id.life3)
        };

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



        enemyBullet = (ImageView) findViewById(R.id.enemybullet);
        enemyBullet.setImageResource(R.drawable.enemybullet);
        enemyBullet.setVisibility(View.GONE);
        enemyFormation = new EnemyFormation();
        difficultyLevel.getContext(this);
        setEnemiesByLevel();

        handlerEnemyShooting.postDelayed(enemyShootingRunnable, 3000);



        bullet1 = (ImageView) findViewById(R.id.bullet1);
        bullet2 = (ImageView) findViewById(R.id.bullet2);

        // Initialize runnableMap for linking bullet ImageView and their runnable.
        runnableMap = new HashMap<ImageView, BulletRunnable>() {{
            put(bullet1, new BulletRunnable(bullet1, enemyFormation));
            put(bullet2, new BulletRunnable(bullet2, enemyFormation));
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
                loadedBullet.setImageResource(R.drawable.bullet);
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
            handlerShooting.removeCallbacks(shootingCoolDown);
        }
    };

    private void setEnemiesByLevel() {
        Enemy[][] tmpEnemies = difficultyLevel.setEnemies();
        for (int i = 0; i < tmpEnemies.length; i++) {
            enemyFormation.setNewEnemiesList();
            for (int j = 0; j < tmpEnemies[0].length; j++) {
                Enemy e = tmpEnemies[i][j];
                e.setVisible();
                enemyFormation.addEnemy(e, i);
            }
        }
    }
    public void nextLevel() {
        handlerEnemyShooting.removeCallbacks(enemyShootingRunnable);
        difficultyLevel.nextLevel();
        setEnemiesByLevel();
    }
    private Handler handlerEnemyShooting = new Handler(Looper.getMainLooper());
    private Runnable enemyShootingRunnable = new Runnable() {
        @Override
        public void run() {
            int shooterSetIdx = (int) Math.random() * enemyFormation.size();
            List<Enemy> shooterSet = enemyFormation.getOneList(shooterSetIdx);
            Enemy shooter = shooterSet.get(0);
            enemyBullet.setX((shooter.getPositionSides()[0]+shooter.getPositionSides()[1])/2 - bulletWidth/2);
            enemyBullet.setY(shooter.getPositionTopBottom()[1]);
            enemyBullet.setVisibility(View.VISIBLE);
            handlerEnemyBullet.post(enemyBulletRunnable);
            handlerEnemyShooting.postDelayed(this, 1500 + (int) (Math.random()*3500)); // Cool down = 1.5~5 sec
        }
    };


    public Handler handlerEnemyBullet = new Handler(Looper.getMainLooper());
    private Runnable enemyBulletRunnable = new Runnable() {
        @Override
        public void run() {
            enemyBullet.setY(enemyBullet.getY()+16);
            if(enemyBullet.getY() + enemyBullet.getHeight() > ship.getY()+ship.getHeight()) {
                enemyBullet.setVisibility(View.GONE);
                handlerEnemyBullet.removeCallbacks(this);
            } else if (checkShipDestruction()) {
                enemyBullet.setVisibility(View.GONE);
                destroyShip();
                handlerEnemyBullet.removeCallbacks(this);
            } else {
                handlerEnemyBullet.postDelayed(this, 17); // fps = 1000/17
            }
        }
    };
    private boolean checkShipDestruction() {
        float x = enemyBullet.getX();
        float y = enemyBullet.getY()+enemyBullet.getHeight();
        if (x+bulletWidth > ship.getX() && x < ship.getX()+ship.getWidth() && y > ship.getY())
            return true;
        return false;
    }
    private void destroyShip() {
        remainLives--;
        livesTextView.setText(remainLives+"");
        lifeIcons[remainLives].setVisibility(View.GONE);
        if (checkNoLives()) gameOver();
        else {
            leftIcon.setEnabled(false);
            rightIcon.setEnabled(false);
            shootBtn.setEnabled(false);
            leftIcon.setImageResource(R.drawable.left_disable);
            rightIcon.setImageResource(R.drawable.right_disable);
            shootBtn.setTextColor(Color.GRAY);
            ship.setImageResource(R.drawable.ship_destroyed);
            shipDestructionHandler.postDelayed(shipDestructionRunnable, 1500); // destruction cool down = 1.5 sec
        }
    }
    private Handler shipDestructionHandler = new Handler(Looper.getMainLooper());
    private Runnable shipDestructionRunnable = new Runnable() {
        @Override
        public void run() {
            leftIcon.setEnabled(true);
            rightIcon.setEnabled(true);
            shootBtn.setEnabled(true);
            leftIcon.setImageResource(R.drawable.left_button);
            rightIcon.setImageResource(R.drawable.right_button);
            shootBtn.setTextColor(Color.WHITE);
            ship.setImageResource(R.drawable.ship);
            shipDestructionHandler.removeCallbacks(this);
        }
    };
    private boolean checkNoLives() { return remainLives == 0; }
    private void gameOver() {
        handlerEnemyShooting.removeCallbacks(enemyShootingRunnable);
        runnableMap.get(bullet1).removeRunnable();
        runnableMap.get(bullet2).removeRunnable();
        handlerEnemyBullet.removeCallbacks(enemyBulletRunnable);
        handlerShooting.removeCallbacks(shootingCoolDown);
        shootBtn.setTextColor(Color.GRAY);
        shootBtn.setEnabled(false);
        leftIcon.setEnabled(false);
        rightIcon.setEnabled(false);
        leftIcon.setImageResource(R.drawable.left_disable);
        rightIcon.setImageResource(R.drawable.right_disable);
        if (!enemyFormation.noEnemies()) {
            enemyFormation.stopEnemiesMoving();
            ship.setImageResource(R.drawable.ship_destroyed);
        }
        gameOverTextView.setVisibility(View.VISIBLE);
    }
    public void plusScore(int s) {
        score += s;
        scoreTextView.setText(score+"");
    }
}
