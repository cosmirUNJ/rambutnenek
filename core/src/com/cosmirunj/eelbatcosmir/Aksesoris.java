package com.cosmirunj.eelbatcosmir;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Inovatif on 10/18/2017.
 */

public class Aksesoris extends Actor {
    private final int SPEED_ENEMY= 100;
    private final int MAX_ANGLE_CHANGE = 300;
    private final int CLOSE_RADIUS = 800;

    private final float TARGET_X;
    private final float TARGET_Y;
    private float x,y;
    private float currentAngle;
    private Texture enemyJelly, enemyJellyAngry, shadowEnemy,moorisIdol, manyherringfish;
    private final int SHADOW_OFFSET = 150;
    private int level;
    private Assets assets;
    private boolean isAngry;
    private int typeAksesoris;
    private Texture[] whirlpool;
    private Animation whirpoolAnim;
    private SpriteBatch batch;
    private float elapsedTime;
    //private boolean fixed;

    public Aksesoris(Assets assets, float x, float y, int level){
        //this.fixed = fixed;
        TARGET_X = x;
        TARGET_Y = y;
        this.assets = assets;
        this.x = x + (com.cosmirunj.eelbatcosmir.EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);
        this.y = y + (com.cosmirunj.eelbatcosmir.EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);
        this.level = level;

        isAngry = false;

        batch = new SpriteBatch();
        checkLevel();
        shadowEnemy = assets.getTexture(Assets.shadowEnemy);
    }

    private void checkLevel() {
        switch (level){
            case 1:
                enemyJelly = assets.getTexture(Assets.enemyJelly);
                //enemyJellyAngry = assets.getTexture(Assets.activeJelly);
                break;
            case 2:
                enemyJelly = assets.getTexture(Assets.enemyJelly);
                enemyJellyAngry = assets.getTexture(Assets.activeJelly);
                moorisIdol = assets.getTexture(Assets.moorishIdol);
                manyherringfish = assets.getTexture(Assets.manyherringfish);

                whirlpool = new Texture[4];
                for (int i=0;i<4;i++){
                    whirlpool[i] = assets.getTexture(Assets.whirlpool[i]);
                }
                whirpoolAnim = new Animation(1f/4f, whirlpool);
                break;
            default:
                enemyJelly = assets.getTexture(Assets.manyherringfish);
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        this.elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(shadowEnemy, x-shadowEnemy.getWidth()/2,y-SHADOW_OFFSET);
        batch.draw(getTextureAksesoris(elapsedTime),x-enemyJelly.getWidth()/2,y-enemyJelly.getHeight()/2);
    }

    @Override
    public void act(float delta){
        if(Math.pow(x - TARGET_X, 2) + Math.pow(y - TARGET_Y, 2) <= Math.pow(CLOSE_RADIUS, 2)) {
            currentAngle += delta*(com.cosmirunj.eelbatcosmir.EelbatCosmir.random.nextFloat()*2*MAX_ANGLE_CHANGE - MAX_ANGLE_CHANGE);
        } else {
            if((x - TARGET_X)*Math.sin(currentAngle/180*Math.PI) - (y - TARGET_Y)*Math.cos(currentAngle/180*Math.PI) >= 0) {
                currentAngle += delta* com.cosmirunj.eelbatcosmir.EelbatCosmir.random.nextFloat()*MAX_ANGLE_CHANGE;
            } else {
                currentAngle -= delta* com.cosmirunj.eelbatcosmir.EelbatCosmir.random.nextFloat()*MAX_ANGLE_CHANGE;
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

    }

    public float getAksesorisPositionX(){
        return  x;
    }
    public float getAksesorisPositionY(){
        return y;
    }

    public boolean getSateAksesorisIsAngry(){
        return isAngry;
    }
    public void setSateAksesoris(boolean isAngry){
        this.isAngry = isAngry;
    }
    public Texture getTextureAksesoris(float delta){
        if (level == 2){
            switch (getTypeAksesoris()){
                case 1:
                    if (getSateAksesorisIsAngry() == false) {
                        return enemyJelly;
                    } else if (getSateAksesorisIsAngry() == true) {
                        return enemyJellyAngry;
                    } else {
                        return enemyJelly;
                    }
                case 2:
                    return (Texture) whirpoolAnim.getKeyFrame(delta, true);
                case 3:
                    return manyherringfish;
                default:
                    return moorisIdol;
            }
        }
        return enemyJelly;
    }
    public void setTypeAksesoris(int typeAksesoris){
        this.typeAksesoris = typeAksesoris;
    }
    //urutanya enemyjely, tornado, hering, mory
    public int getTypeAksesoris(){
        return typeAksesoris;
    }



}
