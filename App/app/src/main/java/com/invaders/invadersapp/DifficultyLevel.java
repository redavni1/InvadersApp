package com.invaders.invadersapp;

import android.content.Context;

import java.io.Serializable;

public class DifficultyLevel implements Serializable {
    private String difficulty;
    private int level;
    private Enemy[][] enemiesFormation;
    Context mContext;
    private int[][] drawables = {
            { R.drawable.enemyship_a1, R.drawable.enemyship_a2 }, // score = 10
            { R.drawable.enemyship_b1, R.drawable.enemyship_b2 }, // score = 20
            { R.drawable.enemyship_c1, R.drawable.enemyship_c2 }  // score = 30
    };
    public void setLevel(int l) { level = l; }
    public int getLevel() { return level; }
    public void nextLevel() { level++; }
    public void setDifficulty(String d) { difficulty = d; }
    public Enemy[][] setEnemies() {
        switch (difficulty) {
            case "EASY":
                switch (level) {
                    case 1:
                        setFormation(1, 3);
                        break;
                    default: break;
                }
            default: break;
        }
        return enemiesFormation;
    }

    private void setFormation(int x, int y) {
        enemiesFormation = new Enemy[x][y];
        int num = 0;
        int positionY;
        for (int i = 0; i < x; i++) {
            positionY = 600;
            for (int j = 0; j < y; j++) {
                enemiesFormation[i][j] = new Enemy(mContext, R.id.enemy1 + num++, drawables[j][0], drawables[j][1], 504, positionY, (j+1)*10);
                positionY -= 150;
            }
        }
    }
    public void getContext(Context context) {
        mContext = context;
    }
}