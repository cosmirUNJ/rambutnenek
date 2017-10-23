package com.cosmirunj.eelbat;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Inovatif on 10/18/2017.
 */

public class EnemyList extends Stage{

    private Map<Integer, Set<Enemy>> fixedEnemies;
    private Set<Enemy> freeEnemies;
    private HashSet<Integer> targetsFound;

    static final int TOTAL_TARGETS = 5;

    private final int MAX_RADIUS_X = 5*EelbatCosmir.WIDTH;
    private final int MAX_RADIUS_Y = 5*EelbatCosmir.HEIGHT;

    private EelbatCosmir eelbatCosmir;

    public EnemyList(EelbatCosmir eelbatCosmir) {
        this.eelbatCosmir = eelbatCosmir;
    }


    public void addEnemies() {
        targetsFound = new HashSet<Integer>();
        fixedEnemies = new HashMap<Integer, Set<Enemy>>();
        freeEnemies = new HashSet<Enemy>();
        for(int i = 0; i < TOTAL_TARGETS; i++) {
            float x = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
            //TargetActor target = new TargetActor(ggj2017.assets, x, y, i);
            //targets.add(target);
            //addActor(target);
            Set<Enemy> enemyGroup = new HashSet<Enemy>();
            int k = 7 + EelbatCosmir.random.nextInt(8);
            for(int j = 0; j < k; j++) {
                Enemy enemy = new Enemy(eelbatCosmir.assets, x, y, true);
                enemyGroup.add(enemy);
                addActor(enemy);
            }
            fixedEnemies.put(i, enemyGroup);
        }

        for(int i = 0; i < 50; i++) {
            float x = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
            Enemy enemy = new Enemy(eelbatCosmir.assets, x, y, false);
            freeEnemies.add(enemy);
            addActor(enemy);
        }
    }
}
