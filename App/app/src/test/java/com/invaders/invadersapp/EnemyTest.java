package com.invaders.invadersapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

/**
 * Test enemy's drawables.
 */
public class EnemyTest {
    int[][] drawables = {
            { R.drawable.enemyship_a1, R.drawable.enemyship_a2 }, // score = 10
            { R.drawable.enemyship_b1, R.drawable.enemyship_b2 }, // score = 20
            { R.drawable.enemyship_c1, R.drawable.enemyship_c2 }  // score = 30
    };
    List<Enemy> enemies = Arrays.asList(
            new Enemy(R.id.enemy1, drawables[0][0], drawables[0][1],504, 600, 10),
            new Enemy(R.id.enemy1, drawables[1][0], drawables[1][1],504, 450, 20),
            new Enemy(R.id.enemy1, drawables[2][0], drawables[2][1],504, 300, 30));
    @Test
    public void enemyDrawableTest() {
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(R.drawable.enemyship_a1 + i*2, enemies.get(i).getNextDrawable());
            assertEquals(R.drawable.enemyship_a1 + i*2 + 1, enemies.get(i).getNextDrawable());
        }
    }
    @Test
    public void enemyFormationTest() {
        EnemyFormation enemyFormation = new EnemyFormation();
        for (int i = 0; i < enemies.size(); i++) {
            enemyFormation.setNewEnemiesList();
            enemyFormation.addEnemy(enemies.get(i), i);
        }
        assertEquals(3, enemyFormation.size());
        for (int i = 0; i < enemyFormation.size(); i++) {
            assertEquals(Arrays.asList(enemies.get(i)), enemyFormation.getOneList(i));
        }
        for (int i = 0; i < enemyFormation.size(); i++) {
            enemyFormation.removeEnemy(enemies.get(i), i);
        }
        assertEquals(true,enemyFormation.noEnemies());
    }
}
