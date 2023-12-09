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

import com.invaders.invadersapp.sound.BGMManager;

import android.media.MediaPlayer;





public class InGame extends AppCompatActivity {
    Intent intent;
    /** DifficultyLevel class object previously generated */
    private DifficultyLevel difficultyLevel;



    /** ImageView of ship. */
    private ImageView shipImageView;
    /** ImageView of left icon. */
    private ImageView leftIconImageView;
    /** ImageView of right icon. */
    private ImageView rightIconImageView;
    /** Runnable for steering ship left. */
    private MovingRunnable movingLeft;
    /** Runnable for steering ship right. */
    private MovingRunnable movingRight;
    /** TextView of "S H O O T" button. */
    private TextView shootBtnTextView;
    /** LinkedList for reusing bullets. */
    private LinkedList<ImageView> bullets = new LinkedList<>();
    /** ImageView of bullet1, 2. */
    private ImageView bullet1; private ImageView bullet2;
    /** Bullet's width. */
    private float bulletWidth = 17;
    /** Map for linking bullet and bullet's runnable. */
    private Map<ImageView, BulletRunnable> bulletRunnableMap;
    /** Temporary ImageView for shot bullet. */
    private ImageView loadedBullet;
    /** Management enemies set as list. */
    private EnemyFormation enemyFormation;
    /** ImageView of enemy's bullet. */
    private ImageView enemyBullet;
    /** Number of life remaining. */
    private int remainLives = 3;
    /** TextView of remainLives. */
    private TextView livesTextView;
    /** ImageView array of life icons. */
    private ImageView[] lifeIcons;
    /** TextView of game over. */
    private TextView gameOverTextView;
    /** TextView that displaing current score. */
    private TextView scoreTextView;
    /** Number of current score. */
    private int score = 0;


    /**
     * Initialize game over TextView.
     */
    private void setGameOverTextView() {
        gameOverTextView = (TextView) findViewById(R.id.gameover);
        gameOverTextView.setVisibility(View.GONE);
    }

    /**
     * Initialize score TextView.
     */
    private void setScoreTextView() {
        scoreTextView = (TextView) findViewById(R.id.score);
        scoreTextView.setText(score+"");
    }

    /**
     * Initialize about life.
     */
    private void setLives() {
        livesTextView = (TextView) findViewById(R.id.livesText);
        livesTextView.setText(remainLives+"");
        lifeIcons = new ImageView[] {
                (ImageView) findViewById(R.id.life1),
                (ImageView) findViewById(R.id.life2),
                (ImageView) findViewById(R.id.life3)
        };
    }

    /**
     * Initialize steering ship icons.
     */
    private void setSteeringShipIcons() {
        leftIconImageView = (ImageView) findViewById(R.id.left_icon);
        rightIconImageView = (ImageView) findViewById(R.id.right_icon);
        leftIconImageView.setImageResource(R.drawable.left_button);
        rightIconImageView.setImageResource(R.drawable.right_button);
        // Initialize steering icon's runnable.
        movingLeft = new MovingRunnable("LEFT");
        movingRight = new MovingRunnable("RIGHT");
        movingLeft.setShip(shipImageView);
        movingRight.setShip(shipImageView);
    }

    /**
     * Initialize bullets.
     */
    private void setBullets() {
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
    }

    /**
     * Initialize enemy's bullet.
     */
    private void setEnemyBullet() {
        enemyBullet = (ImageView) findViewById(R.id.enemybullet);
        enemyBullet.setImageResource(R.drawable.enemybullet);
        enemyBullet.setVisibility(View.GONE);
    }

    /**
     * Initialize enemies formation by level.
     */
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingame);

        intent = getIntent();
        // Get the previously created DifficultyLevel class object.
        difficultyLevel = (DifficultyLevel) intent.getSerializableExtra("Level", DifficultyLevel.class);
        // Set components.
        setGameOverTextView();
        setScoreTextView();
        setLives();
        shipImageView = (ImageView) findViewById(R.id.ship);
        shootBtnTextView = (TextView) findViewById(R.id.shoot);
        setSteeringShipIcons();
        setEnemyBullet();
        enemyFormation = new EnemyFormation();
        difficultyLevel.getContext(this);
        setEnemiesByLevel();
        setBullets();
        // Start enemy's shooting after 3 sec.
        inGameHandler.postDelayed(selectEnemyShooterRunnable, 3000);

        leftIconImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Change left button's color green and ship's position to left when it touched.
                    leftIconImageView.setImageResource(R.drawable.left_touched);
                    movingLeft.run();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Change left button's color white and Stop ship's moving when it stops being touched.
                    leftIconImageView.setImageResource(R.drawable.left_button);
                    movingLeft.stop();
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
                    movingRight.run();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Change left button's color white and Stop ship's moving when it stops being touched.
                    rightIconImageView.setImageResource(R.drawable.right_button);
                    movingRight.stop();
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
                inGameHandler.postDelayed(shootingCoolDownRunnable, 1000);
            }
        });
    }

    /**
     * Handler to control runnables in InGame.java
     */
    private Handler inGameHandler = new Handler(Looper.getMainLooper());

    /**
     * Runnable for ship's shooting cool down.
     */
    private Runnable shootingCoolDownRunnable = new Runnable() {
        @Override
        public void run() {
            // Set shootbtn's color white and set it enable.
            shootBtnTextView.setTextColor(Color.WHITE);
            shootBtnTextView.setEnabled(true);
            inGameHandler.removeCallbacks(shootingCoolDownRunnable);
        }
    };

    /**
     * Runnable for select shooter from enemies after cool down.
     */
    private Runnable selectEnemyShooterRunnable = new Runnable() {
        @Override
        public void run() {
            int shooterSetIdx = (int) Math.random() * enemyFormation.size();
            List<Enemy> shooterSet = enemyFormation.getOneList(shooterSetIdx);
            Enemy shooter = shooterSet.get(0);
            enemyBullet.setX((shooter.getPositionSides()[0]+shooter.getPositionSides()[1])/2 - bulletWidth/2);
            enemyBullet.setY(shooter.getPositionTopBottom()[1]);
            enemyBullet.setVisibility(View.VISIBLE);
            inGameHandler.post(enemyBulletRunnable);
            inGameHandler.postDelayed(this, 1500 + (int) (Math.random()*3500)); // Cool down = 1.5~5 sec
        }
    };

    /**
     * Start runnable to shoot bullet.
     */
    private Runnable enemyBulletRunnable = new Runnable() {
        @Override
        public void run() {
            enemyBullet.setY(enemyBullet.getY()+16);
            if(enemyBullet.getY() + enemyBullet.getHeight() > shipImageView.getY()+ shipImageView.getHeight()) {
                enemyBullet.setVisibility(View.GONE);
                inGameHandler.removeCallbacks(this);
            } else if (checkShipDestruction()) {
                enemyBullet.setVisibility(View.GONE);
                destroyShip();
                inGameHandler.removeCallbacks(this);
            } else {
                inGameHandler.postDelayed(this, 17); // fps = 1000/17
            }
        }
    };

    /**
     * Check ship has been attacked by enemy's bullet.
     *
     * @return Return true if ship is attacked.
     */
    private boolean checkShipDestruction() {
        float x = enemyBullet.getX();
        float y = enemyBullet.getY()+enemyBullet.getHeight();
        if (x+bulletWidth > shipImageView.getX() && x < shipImageView.getX()+ shipImageView.getWidth() && y > shipImageView.getY())
            return true;
        return false;
    }

    /**
     * Update settings when ship destroyed.
     */
    private void destroyShip() {
        remainLives--;
        livesTextView.setText(remainLives+"");
        lifeIcons[remainLives].setVisibility(View.GONE);
        leftIconImageView.setEnabled(false);
        rightIconImageView.setEnabled(false);
        shootBtnTextView.setEnabled(false);
        leftIconImageView.setImageResource(R.drawable.left_disable);
        rightIconImageView.setImageResource(R.drawable.right_disable);
        shootBtnTextView.setTextColor(Color.GRAY);
        shipImageView.setImageResource(R.drawable.ship_destroyed);
        if (checkNoLives()) gameOver();
        else { // Set cool down about ship's destruction.
            inGameHandler.postDelayed(terminateShipDestructionCoolDown, 1500); // destruction cool down = 1.5 sec
        }
    }

    /**
     * Runnable for termination ship's destruction cool down.
     */
    private Runnable terminateShipDestructionCoolDown = new Runnable() {
        @Override
        public void run() {
            leftIconImageView.setEnabled(true);
            rightIconImageView.setEnabled(true);
            shootBtnTextView.setEnabled(true);
            leftIconImageView.setImageResource(R.drawable.left_button);
            rightIconImageView.setImageResource(R.drawable.right_button);
            shootBtnTextView.setTextColor(Color.WHITE);
            shipImageView.setImageResource(R.drawable.ship);
            inGameHandler.removeCallbacks(this);
        }
    };

    /**
     * Update score when enemy is destroyed.
     *
     * @param s Scores assigned to the destroyed enemy.
     */
    public void plusScore(int s) {
        score += s;
        scoreTextView.setText(score+"");
    }

    /**
     * Check ship has no lives.
     *
     * @return Return true if ship has no lives.
     */
    private boolean checkNoLives() { return remainLives == 0; }

    /**
     * Check enemy formation is empty.
     *
     * @return Return true if enemy formation is empty.
     */
    public boolean enemyFormationIsEmpty() {
        return enemyFormation.noEnemies();
    }

    /**
     * Change View setting to game over in InGame activity.
     */
    public void gameOver() {
        inGameHandler.removeCallbacks(selectEnemyShooterRunnable);
        bulletRunnableMap.get(bullet1).stop();
        bulletRunnableMap.get(bullet2).stop();
        movingLeft.stop();
        movingRight.stop();
        if (!enemyFormationIsEmpty()) enemyFormation.stopEnemiesMoving();
        inGameHandler.removeCallbacks(enemyBulletRunnable);
        enemyBullet.setVisibility(View.GONE);
        inGameHandler.removeCallbacks(shootingCoolDownRunnable);
        inGameHandler.removeCallbacks(terminateShipDestructionCoolDown);
        shootBtnTextView.setTextColor(Color.GRAY);
        shootBtnTextView.setEnabled(false);
        leftIconImageView.setEnabled(false);
        rightIconImageView.setEnabled(false);
        leftIconImageView.setImageResource(R.drawable.left_disable);
        rightIconImageView.setImageResource(R.drawable.right_disable);
        gameOverTextView.setVisibility(View.VISIBLE);
    }

    public float getShipXCoordination() { return shipImageView.getX(); }
    public int getScore() { return score; }

    @Override
    protected void onStart() {
        super.onStart();

        // InGame BGM start
        MainActivity.mBGMManager.mMediaPlayerInGame.start();
    }

    // Pause the game screen BGM when the activity stops, unless transitioning to another activity
    @Override
    protected void onStop() {
        super.onStop();

        MainActivity.mBGMManager.mMediaPlayerInGame.seekTo(0);
        MainActivity.mBGMManager.mMediaPlayerInGame.pause();
    }
}
