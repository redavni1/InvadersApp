package com.invaders.invadersapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

/**
 * Test enemy's drawables.
 */
public class EnemyTest {
    @Test
    public void enemyDrawableTest() {
        int[][] drawables = {
                { R.drawable.enemyship_a1, R.drawable.enemyship_a2 }, // score = 10
                { R.drawable.enemyship_b1, R.drawable.enemyship_b2 }, // score = 20
                { R.drawable.enemyship_c1, R.drawable.enemyship_c2 }  // score = 30
        };
        List<Enemy> enemies = Arrays.asList(
                new Enemy(R.id.enemy1, drawables[0][0], drawables[0][1],504, 600, 10),
                new Enemy(R.id.enemy1, drawables[1][0], drawables[1][1],504, 450, 20),
                new Enemy(R.id.enemy1, drawables[2][0], drawables[2][1],504, 300, 30));
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getNextDrawable(), R.drawable.enemyship_a1 + i*2);
            assertEquals(enemies.get(i).getNextDrawable(), R.drawable.enemyship_a1 + i*2 + 1);
        }
    }
}
