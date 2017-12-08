package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by USER on 28/10/2017.
 */

public class Enemy extends Actor {
    private int SPEED_ENEMY = 100;
    private final int MAX_ANGLE_CHANGE = 300;
    private final int CLOSE_RADIUS = 800;
    private final float TARGET_X;
    private final float TARGET_Y;
    private float x,y;
    private float currentAngle;
    private Texture enemyFish, shadowEnemy;
    private final int SHADOW_OFFSET = 150;
    private boolean fixed;
    KEJARGAK kejargak;
    float eelbatPositionX, eelbatPositionY, eelbatPositionRadius;

    public Enemy(Assets assets, float x, float y, boolean fixed){
        this.fixed = fixed;
        eelbatPositionX = 0;
        eelbatPositionY = 0;
        //SPEED_ENEMY = 0;
        //getViewport().getCamera().position;
        TARGET_X = x;
        TARGET_Y = y;
        this.x = x + (EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);
        this.y = y + (EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);

        enemyFish = assets.getTexture(Assets.enemyFish);
        shadowEnemy = assets.getTexture(Assets.shadowEnemy);

        kejargak = KEJARGAK.GAK;
    }

    public void setStateEnemy(KEJARGAK kejargak, float x, float y, float r){
        this.kejargak = kejargak;

        if (kejargak == KEJARGAK.KEJAR){
            Gdx.app.log("myTag","kenakenaknea");
        }
        eelbatPositionRadius = r;
        eelbatPositionX = x;
        eelbatPositionY = y;
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        /*
        float cameraPositionX = playGameStage.getCameraPositionX();
        float cameraPositionY = playGameStage.getCameraPositionY();

        if(Math.pow(x-cameraPositionX,2)+Math.pow(y-cameraPositionY,2) <= Math.pow(CLOSE_RADIUS,2)){
            Gdx.app.log("myTag","kenakenaknea");
            batch.draw(shadowEnemy, x-shadowEnemy.getWidth()/2,y-SHADOW_OFFSET);
            batch.draw(enemyFish,x-enemyFish.getWidth()/2,y-enemyFish.getHeight()/2);
            //System.out.print("kenakenakena");
        }
        */
            batch.draw(shadowEnemy, x-shadowEnemy.getWidth()/2,y-SHADOW_OFFSET);
            batch.draw(enemyFish,x-enemyFish.getWidth()/2,y-enemyFish.getHeight()/2);
    }

    /*
    //check radius, untuk ngecek di radius tsb ada eelbat/gk? kalo ada return true
    private boolean checkRadius() {
        if(Math.pow(x,2)+Math.pow(y,2) <= Math.pow(CLOSE_RADIUS,2)){
            return true;
        } else {
            return false;
        }

        //float x = enemy.getPositionX();
        //float y = enemy.floatgetPositionY();
        //if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2))

    }
    */

    @Override
    public void act(float delta){
        switch (kejargak){
            case KEJAR:
                //currentAngle = (float) Math.cos(eelbatPositionX/eelbatPositionRadius);
                x = (float) Math.cos(eelbatPositionX-x/eelbatPositionRadius)*SPEED_ENEMY;
                y = (float) Math.sin(eelbatPositionY-y/eelbatPositionRadius)*SPEED_ENEMY;

                break;
            case GAK:
                if(!fixed || Math.pow(x - TARGET_X, 2) + Math.pow(y - TARGET_Y, 2) <= Math.pow(CLOSE_RADIUS, 2)) {
                    currentAngle += delta*(EelbatCosmir.random.nextFloat()*2*MAX_ANGLE_CHANGE - MAX_ANGLE_CHANGE);
                } else {
                    if((x - TARGET_X)*Math.sin(currentAngle/180*Math.PI) - (y - TARGET_Y)*Math.cos(currentAngle/180*Math.PI) >= 0) {
                        currentAngle += delta*EelbatCosmir.random.nextFloat()*MAX_ANGLE_CHANGE;
                    } else {
                        currentAngle -= delta*EelbatCosmir.random.nextFloat()*MAX_ANGLE_CHANGE;
                    }
                }
                while(currentAngle > 360) {
                    currentAngle -= 360;
                }
                while(currentAngle < 0) {
                    currentAngle += 360;
                }
                x += delta*Math.cos(currentAngle/180*Math.PI)*SPEED_ENEMY;
                y += delta*Math.sin(currentAngle/180*Math.PI)*SPEED_ENEMY;
                break;
        }


    }

    public float getEnemyPositionX(){
        return  x;
    }
    public float getEnemyPositionY(){
        return y;
    }

    void unfix(){
        fixed = false;
    }

    enum KEJARGAK{
        GAK, KEJAR
    }
}
