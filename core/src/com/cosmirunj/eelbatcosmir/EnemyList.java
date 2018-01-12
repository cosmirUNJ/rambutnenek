package com.cosmirunj.eelbatcosmir;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Inovatif on 10/18/2017.
 */

public class EnemyList extends Stage{

    private Map<Integer, Set<Aksesoris>> fixedEnemies;
    private Set<Aksesoris> freeEnemies;
    private HashSet<Integer> targetsFound;

    static final int TOTAL_TARGETS = 5;

    private final int MAX_RADIUS_X = 5* com.cosmirunj.eelbatcosmir.EelbatCosmir.WIDTH;
    private final int MAX_RADIUS_Y = 5* com.cosmirunj.eelbatcosmir.EelbatCosmir.HEIGHT;

    private com.cosmirunj.eelbatcosmir.EelbatCosmir eelbatCosmir;

//    public EnemyList(EelbatCosmir eelbatCosmir) {
//        this.eelbatCosmir = eelbatCosmir;
//        targetsFound = new HashSet<Integer>();
//        fixedEnemies = new HashMap<Integer, Set<Aksesoris>>();
//        freeEnemies = new HashSet<Aksesoris>();
//        for(int i = 0; i < TOTAL_TARGETS; i++) {
//            float x = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
//            float y = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
//            //TargetActor target = new TargetActor(ggj2017.assets, x, y, i);
//            //targets.add(target);
//            //addActor(target);
//            Set<Aksesoris> aksesorisGroup = new HashSet<Aksesoris>();
//            int k = 7 + EelbatCosmir.random.nextInt(8);
//            for(int j = 0; j < k; j++) {
//                Aksesoris aksesoris = new Aksesoris(eelbatCosmir.assets, x, y, );
//                aksesorisGroup.add(aksesoris);
//                addActor(aksesoris);
//            }
//            fixedEnemies.put(i, aksesorisGroup);
//        }
//
//        for(int i = 0; i < 50; i++) {
//            float x = EelbatCosmir.random.nextInt(2 * MAX_RADIUS_X) - MAX_RADIUS_X;
//            float y = EelbatCosmir.random.nextInt(2 * MAX_RADIUS_Y) - MAX_RADIUS_Y;
//            Aksesoris aksesoris = new Aksesoris(eelbatCosmir.assets, x, y);
//            freeEnemies.add(aksesoris);
//            addActor(aksesoris);
//        }
//    }
}
