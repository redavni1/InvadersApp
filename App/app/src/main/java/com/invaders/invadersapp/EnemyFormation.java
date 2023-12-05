package com.invaders.invadersapp;

import java.util.ArrayList;
import java.util.List;

public class EnemyFormation extends InGame {
    private List<List<Enemy>> enemyFormation;
    public EnemyFormation() {
        enemyFormation = new ArrayList<>();
    }
    public void setNewEnemiesList() {
        enemyFormation.add(new ArrayList<>());
    }
    public void addEnemy(Enemy enemy, int idx) {
        enemyFormation.get(idx).add(enemy);
    }
    public int size() { return enemyFormation.size(); }
    public List<Enemy> getOneList(int idx) { return enemyFormation.get(idx); }
    public void removeEnemy(Enemy enemy, int idx) { enemyFormation.get(idx).remove(enemy); }
}
