package com.invaders.invadersapp;

import java.util.ArrayList;
import java.util.List;

public class EnemyFormation extends InGame {
    /** 2D list to control enemies. */
    private List<List<Enemy>> enemyFormation;

    /**
     * Initialize enemyFormation list.
     */
    public EnemyFormation() {
        enemyFormation = new ArrayList<>();
    }

    /**
     * Add list to enemyFormation list.
     */
    public void setNewEnemiesList() {
        enemyFormation.add(new ArrayList<>());
    }

    /**
     * Add enemy in enemyFormation.
     *
     * @param enemy Enemy to be added to list in enemyFormation.
     * @param idx Target list's index.
     */
    public void addEnemy(Enemy enemy, int idx) { enemyFormation.get(idx).add(enemy); }

    /**
     * Return enemyFormation's size.
     *
     * @return enemyFormation's size.
     */
    public int size() { return enemyFormation.size(); }

    /**
     * Return a list of index.
     *
     * @param idx Target list's index
     *
     * @return Target list.
     */
    public List<Enemy> getOneList(int idx) { return enemyFormation.get(idx); }

    /**
     * Remove enemy from enemyFormation.
     *
     * @param enemy Target enemy to be removed.
     * @param idx Target enemy's index.
     */
    public void removeEnemy(Enemy enemy, int idx) { enemyFormation.get(idx).remove(enemy); }

    /**
     * Check enemyFormation has no enemies.
     *
     * @return Return true if enemyFormation has no enemies.
     */
    public boolean noEnemies() {
        for (List<Enemy> list : enemyFormation) {
            if (!list.isEmpty()) return false;
        }
        return true;
    }

    /**
     * Stop enemies moving.
     */
    public void stopEnemiesMoving() {
        for (List<Enemy> list : enemyFormation) {
            for (Enemy enemy : list) {
                enemy.stopMoving();
            }
        }
    }
}
