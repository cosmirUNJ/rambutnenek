package com.untamedfox.ggj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnemyCowActor extends Actor {

    private final String TAG = "EnemyCowActor";

    private final int SPEED = 100;
    private final int MAX_ANGLE_CHANGE = 300;
    private final int CLOSE_RADIUS = 800;

    private final float TARGET_X;
    private final float TARGET_Y;
    private float x, y;
    private float currentAngle;
    private Texture head, shadow;
    private ParticleEffect particleEffect;
    private final int SHADOW_OFFSET = 150;
    private boolean fixed;

    public EnemyCowActor(Assets assets, float x, float y, boolean fixed) {
        this.fixed = fixed;
        TARGET_X = x;
        TARGET_Y = y;
        int dx = GGJ2017.random.nextInt(2*CLOSE_RADIUS) - CLOSE_RADIUS;
        int dy = GGJ2017.random.nextInt(2*CLOSE_RADIUS) - CLOSE_RADIUS;
        this.x = x + dx;
        this.y = y + dy;
        head = assets.getTexture(Assets.enemy);
        shadow = assets.getTexture(Assets.shadowSkull);
        currentAngle = GGJ2017.random.nextFloat()*360;

        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("enemigo/bodyghost"), Gdx.files.internal("enemigo/"));
        particleEffect.start();
        //particleEffect.scaleEffect(factor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(shadow, x - shadow.getWidth()/2, y - SHADOW_OFFSET);
        particleEffect.draw(batch);
        batch.draw(head, x - head.getWidth()/2, y - head.getHeight()/2);
    }

    @Override
    public void act(float delta) {
        if(!fixed || Math.pow(x - TARGET_X, 2) + Math.pow(y - TARGET_Y, 2) <= Math.pow(CLOSE_RADIUS, 2)) {
            currentAngle += delta*(GGJ2017.random.nextFloat()*2*MAX_ANGLE_CHANGE - MAX_ANGLE_CHANGE);
        } else {
            if((x - TARGET_X)*Math.sin(currentAngle/180*Math.PI) - (y - TARGET_Y)*Math.cos(currentAngle/180*Math.PI) >= 0) {
                currentAngle += delta*GGJ2017.random.nextFloat()*MAX_ANGLE_CHANGE;
            } else {
                currentAngle -= delta*GGJ2017.random.nextFloat()*MAX_ANGLE_CHANGE;
            }
        }
        while(currentAngle > 360) {
            currentAngle -= 360;
        }
        while(currentAngle < 0) {
            currentAngle += 360;
        }
        x += delta*Math.cos(currentAngle/180*Math.PI)*SPEED;
        y += delta*Math.sin(currentAngle/180*Math.PI)*SPEED;

        particleEffect.setPosition(x, y);
        particleEffect.update(delta);
    }

    public float getPositionX() {
        return x;
    }

    public float getPositionY() {
        return y;
    }

    void unfix() {
        fixed = false;
    }
}
