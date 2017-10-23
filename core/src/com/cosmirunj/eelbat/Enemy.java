package com.cosmirunj.eelbat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Inovatif on 10/18/2017.
 */

public class Enemy extends Actor {
    private final int SPEED_ENEMY= 100;
    private final int MAX_ANGLE_CHANGE = 300;
    private final int CLOSE_RADIUS = 800;

    private final float TARGET_X;
    private final float TARGET_Y;
    private float x,y;
    private float currentAngle;
    private Texture enemyJelly, shadowEnemy;
    private final int SHADOW_OFFSET = 150;
    private boolean fixed;

    public Enemy(Assets assets, float x, float y, boolean fixed){
        this.fixed = fixed;
        TARGET_X = x;
        TARGET_Y = y;
        this.x = x + (EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);
        this.y = y + (EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);

        enemyJelly = assets.getTexture(Assets.enemyJelly);
        shadowEnemy = assets.getTexture(Assets.shadowEnemy);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(shadowEnemy, x-shadowEnemy.getWidth()/2,y-SHADOW_OFFSET);
        batch.draw(enemyJelly,x-enemyJelly.getWidth()/2,y-enemyJelly.getHeight()/2);
    }

    @Override
    public void act(float delta){

    }

}
