package com.invaders.invadersapp;

import android.content.Context;

import java.io.Serializable;

public class DifficultyLevel implements Serializable {
    /** String of difficulty. */
    private String difficulty;
    /** Number of level. */
    private int level;
    /** Array of enemies. */
    private Enemy[][] enemiesFormation;
    /** Context gotten. */
    Context mContext;
    /** Array of drawables. */
    private int[][] drawables = {
            { R.drawable.enemyship_a1, R.drawable.enemyship_a2 }, // score = 10
            { R.drawable.enemyship_b1, R.drawable.enemyship_b2 }, // score = 20
            { R.drawable.enemyship_c1, R.drawable.enemyship_c2 }  // score = 30
    };

    /**
     * Set global variable level.
     *
     * @param l Number of level.
     */
    public void setLevel(int l) { level = l; }
//    public int getLevel() { return level; }
//    public void nextLevel() { level++; }

    /**
     * Set global variable difficulty.
     *
     * @param d String of difficulty.
     */
    public void setDifficulty(String d) { difficulty = d; }

    /**
     * Return enemy's Array.
     *
     * @return enemiesFormation.
     */
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

    /**
     * Set enemiesFormation and initialize enemies.
     *
     * @param x Enemy's X coordination.
     * @param y Enemy's Y coordination.
     */
    private void setFormation(int x, int y) {
        enemiesFormation = new Enemy[x][y];
        int num = 0;
        int positionY;
        for (int i = 0; i < x; i++) {
            positionY = 600;
            for (int j = 0; j < y; j++) {
                enemiesFormation[i][j] = new Enemy(R.id.enemy1 + num++, drawables[j][0], drawables[j][1], 504, positionY, (j+1)*10);
                enemiesFormation[i][j].setAboutContext(mContext);
                positionY -= 150;
            }
        }
    }

    /**
     * Get previously generated class's context.
     *
     * @param context Previously generated class's context.
     */
    public void getContext(Context context) {
        mContext = context;
    }
}
