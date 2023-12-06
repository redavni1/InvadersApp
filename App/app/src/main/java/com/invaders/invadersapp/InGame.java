package com.invaders.invadersapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    Intent intent;
    private DifficultyLevel difficultyLevel;



    /** ImageView of ship. */
    private ImageView shipImageView;
    /** ImageView of left icon. */
    private ImageView leftIconImageView;
    /** ImageView of right icon. */
    private ImageView rightIconImageView;
    /** TextView of "S H O O T" button. */
    private TextView shootBtnTextView;
    /** LinkedList for reusing bullets. */
    private LinkedList<ImageView> bullets = new LinkedList<>();
    /** ImageView of bullet1, 2. */
    private ImageView bullet1;
    private ImageView bullet2;
    /** Bullet's width. */
    private float bulletWidth = 17;
    /** Map for linking bullet and bullet's runnable. */
    private Map<ImageView, BulletRunnable> bulletRunnableMap;
    /** Temporary ImageView for shot bullet. */
    private ImageView loadedBullet;
    private EnemyFormation enemyFormation;
    private ImageView enemyBullet;
    private int remainLives = 3;
    private TextView livesTextView;
    private ImageView[] lifeIcons;
    private TextView gameOverTextView;
    private TextView scoreTextView;
    private int score = 0;


    private void setGameOverTextView() {
        gameOverTextView = (TextView) findViewById(R.id.gameover);
        gameOverTextView.setVisibility(View.GONE);
    }
    private void setScoreTextView() {
        scoreTextView = (TextView) findViewById(R.id.score);
        scoreTextView.setText(score+"");
    }
    private void setLives() {
        livesTextView = (TextView) findViewById(R.id.livesText);
        livesTextView.setText(remainLives+"");
        lifeIcons = new ImageView[] {
                (ImageView) findViewById(R.id.life1),
                (ImageView) findViewById(R.id.life2),
                (ImageView) findViewById(R.id.life3)
        };
    }
    private void setDirectionIcons() {
        leftIconImageView = (ImageView) findViewById(R.id.left_icon);
        rightIconImageView = (ImageView) findViewById(R.id.right_icon);
        leftIconImageView.setImageResource(R.drawable.left_button);
        rightIconImageView.setImageResource(R.drawable.right_button);
    }
    private void setEnemyBullet() {
        enemyBullet = (ImageView) findViewById(R.id.enemybullet);
        enemyBullet.setImageResource(R.drawable.enemybullet);
        enemyBullet.setVisibility(View.GONE);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        intent = getIntent();
        difficultyLevel = (DifficultyLevel) intent.getSerializableExtra("Level", DifficultyLevel.class);

        setGameOverTextView();
        setScoreTextView();
        setLives();
        setDirectionIcons();
        shipImageView = (ImageView) findViewById(R.id.ship);
        shootBtnTextView = (TextView) findViewById(R.id.shoot);

        // Initialize direction icons' runnable.
        MovingRunnable movingLeft = new MovingRunnable(leftIconImageView, shipImageView);
        MovingRunnable movingRight = new MovingRunnable(rightIconImageView, shipImageView);

        setEnemyBullet();

        enemyFormation = new EnemyFormation();
        difficultyLevel.getContext(this);
        setEnemiesByLevel();
        selectEnemyShooterHandler.postDelayed(selectEnemyShooterRunnable, 3000);

        bullet1 = (ImageView) findViewById(R.id.bullet1);
        bullet2 = (ImageView) findViewById(R.id.bullet2);
        bullet1.setVisibility(View.GONE);
        bullet2.setVisibility(View.GONE);

        // Initialize bulletRunnableMap for linking bullet ImageView and their runnable.
        bulletRunnableMap = new HashMap<ImageView, BulletRunnable>() {{
            put(bullet1, new BulletRunnable(bullet1, enemyFormation));
            put(bullet2, new BulletRunnable(bullet2, enemyFormation));
        }};
        // Add bullets into their LinkedList for reusing.
        bullets.add(bullet1);
        bullets.add(bullet2);





        leftIconImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Change left button's color green and ship's position to left when it touched.
                    leftIconImageView.setImageResource(R.drawable.left_touched);
                    movingLeft.movingHandler.post(movingLeft);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Change left button's color white and Stop ship's moving when it stops being touched.
                    leftIconImageView.setImageResource(R.drawable.left_button);
                    movingLeft.movingHandler.removeCallbacks(movingLeft);
                }
                return true;
            }
        });

        rightIconImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Change right button's color green and ship's position to right when it touched.
                    rightIconImageView.setImageResource(R.drawable.right_touched);
                    movingRight.movingHandler.post(movingRight);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Change left button's color white and Stop ship's moving when it stops being touched.
                    rightIconImageView.setImageResource(R.drawable.right_button);
                    movingRight.movingHandler.removeCallbacks(movingRight);
                }
                return true;
            }
        });

        shootBtnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load bullet that is head of the list.
                loadedBullet = bullets.poll();
                // Set loaded bullet's image resource.
                loadedBullet.setVisibility(View.VISIBLE);
                // Change shootbtn's color gray and Set it disable.
                shootBtnTextView.setTextColor(Color.GRAY);
                shootBtnTextView.setEnabled(false);
                // Initialize loaded bullet's position.
                loadedBullet.setX(shipImageView.getX() + ((float) shipImageView.getWidth() - bulletWidth)/2);
                loadedBullet.setY(shipImageView.getY());
                // Run loaded bullet's runnable for shooting.
                bulletRunnableMap.get(loadedBullet).run();
                // Add bullet to last of the list.
                bullets.add(loadedBullet);
                // Set shootbtn's color white and Set it enable again one second later.
                shootingCooldownHandler.postDelayed(shootingCoolDownRunnable, 1000);
            }
        });
    }
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
    /** Handler to control shooting cool down runnable. */
    private Handler shootingCooldownHandler = new Handler(Looper.getMainLooper());
    /** Runnable for cool down. */
    private Runnable shootingCoolDownRunnable = new Runnable() {
        @Override
        public void run() {
            // Set shootbtn's color white and Set it enable.
            shootBtnTextView.setTextColor(Color.WHITE);
            shootBtnTextView.setEnabled(true);
            shootingCooldownHandler.removeCallbacks(shootingCoolDownRunnable);
        }
    };
    private Handler selectEnemyShooterHandler = new Handler(Looper.getMainLooper());
    private Runnable selectEnemyShooterRunnable = new Runnable() {
        @Override
        public void run() {
            int shooterSetIdx = (int) Math.random() * enemyFormation.size();
            List<Enemy> shooterSet = enemyFormation.getOneList(shooterSetIdx);
            Enemy shooter = shooterSet.get(0);
            enemyBullet.setX((shooter.getPositionSides()[0]+shooter.getPositionSides()[1])/2 - bulletWidth/2);
            enemyBullet.setY(shooter.getPositionTopBottom()[1]);
            enemyBullet.setVisibility(View.VISIBLE);
            enemyBulletHandler.post(enemyBulletRunnable);
            selectEnemyShooterHandler.postDelayed(this, 1500 + (int) (Math.random()*3500)); // Cool down = 1.5~5 sec
        }
    };


    public Handler enemyBulletHandler = new Handler(Looper.getMainLooper());
    private Runnable enemyBulletRunnable = new Runnable() {
        @Override
        public void run() {
            enemyBullet.setY(enemyBullet.getY()+16);
            if(enemyBullet.getY() + enemyBullet.getHeight() > shipImageView.getY()+ shipImageView.getHeight()) {
                enemyBullet.setVisibility(View.GONE);
                enemyBulletHandler.removeCallbacks(this);
            } else if (checkShipDestruction()) {
                enemyBullet.setVisibility(View.GONE);
                destroyShip();
                enemyBulletHandler.removeCallbacks(this);
            } else {
                enemyBulletHandler.postDelayed(this, 17); // fps = 1000/17
            }
        }
    };
    private boolean checkShipDestruction() {
        float x = enemyBullet.getX();
        float y = enemyBullet.getY()+enemyBullet.getHeight();
        if (x+bulletWidth > shipImageView.getX() && x < shipImageView.getX()+ shipImageView.getWidth() && y > shipImageView.getY())
            return true;
        return false;
    }
    private void destroyShip() {
        remainLives--;
        livesTextView.setText(remainLives+"");
        lifeIcons[remainLives].setVisibility(View.GONE);
        if (checkNoLives()) gameOver();
        else {
            leftIconImageView.setEnabled(false);
            rightIconImageView.setEnabled(false);
            shootBtnTextView.setEnabled(false);
            leftIconImageView.setImageResource(R.drawable.left_disable);
            rightIconImageView.setImageResource(R.drawable.right_disable);
            shootBtnTextView.setTextColor(Color.GRAY);
            shipImageView.setImageResource(R.drawable.ship_destroyed);
            destroyShipHandler.postDelayed(destroyShipRunnable, 1500); // destruction cool down = 1.5 sec
        }
    }
    private Handler destroyShipHandler = new Handler(Looper.getMainLooper());
    private Runnable destroyShipRunnable = new Runnable() {
        @Override
        public void run() {
            leftIconImageView.setEnabled(true);
            rightIconImageView.setEnabled(true);
            shootBtnTextView.setEnabled(true);
            leftIconImageView.setImageResource(R.drawable.left_button);
            rightIconImageView.setImageResource(R.drawable.right_button);
            shootBtnTextView.setTextColor(Color.WHITE);
            shipImageView.setImageResource(R.drawable.ship);
            destroyShipHandler.removeCallbacks(this);
        }
    };
    private boolean checkNoLives() { return remainLives == 0; }
    public boolean enemyFormationIsEmpty() {
        return enemyFormation.noEnemies();
    }
    public void gameOver() {
        selectEnemyShooterHandler.removeCallbacks(selectEnemyShooterRunnable);
        bulletRunnableMap.get(bullet1).removeRunnable();
        bulletRunnableMap.get(bullet2).removeRunnable();
        enemyBulletHandler.removeCallbacks(enemyBulletRunnable);
        enemyBullet.setVisibility(View.GONE);
        shootingCooldownHandler.removeCallbacks(shootingCoolDownRunnable);
        destroyShipHandler.removeCallbacks(destroyShipRunnable);
        shootBtnTextView.setTextColor(Color.GRAY);
        shootBtnTextView.setEnabled(false);
        leftIconImageView.setEnabled(false);
        rightIconImageView.setEnabled(false);
        leftIconImageView.setImageResource(R.drawable.left_disable);
        rightIconImageView.setImageResource(R.drawable.right_disable);
        if (!enemyFormation.noEnemies()) {
            enemyFormation.stopEnemiesMoving();
            shipImageView.setImageResource(R.drawable.ship_destroyed);
        }
        gameOverTextView.setVisibility(View.VISIBLE);
    }
    public void plusScore(int s) {
        score += s;
        scoreTextView.setText(score+"");
    }
}
