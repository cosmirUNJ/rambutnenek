package com.cosmirunj.eelbat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by USER on 24/10/2017.
 */

public class Fruits extends Actor {
    private float x, y;
    private final int id;
    Texture idle;
//    Texture idle, animate1, animate2, vanish;
    private boolean active;
//    private boolean animating;
//    private boolean vanishing;
//    private int animatingIndex;
//    private float time;

    public Fruits(Assets assets, float x, float y, int id, int mf) {
        this.id = id;
        this.x = x;
        this.y = y;
        if(mf == 0){
            idle = assets.getTexture(Assets.mf1);
        }else if(mf == 1){
            idle = assets.getTexture(Assets.mf2);
        }else if(mf == 2){
            idle = assets.getTexture(Assets.mf3);
        }else if(mf == 3){
            idle = assets.getTexture(Assets.mf4);
        }else if(mf == 4){
            idle = assets.getTexture(Assets.fruit1);
        }else if(mf == 5){
            idle = assets.getTexture(Assets.fruit2);
        }else if(mf == 6){
            idle = assets.getTexture(Assets.fruit3);
        }else if(mf == 7){
            idle = assets.getTexture(Assets.fruit4);
        }else if(mf == 8){
            idle = assets.getTexture(Assets.fruit5);
        }else if(mf == 9){
            idle = assets.getTexture(Assets.fruit6);
        }else if(mf == 10){
            idle = assets.getTexture(Assets.fruit1lv3);
        }else if(mf == 11){
            idle = assets.getTexture(Assets.fruit2lv3);
        }else if(mf == 12){
            idle = assets.getTexture(Assets.fruit3lv3);
        }else if(mf == 13){
            idle = assets.getTexture(Assets.fruit4lv3);
        }else if(mf == 14){
            idle = assets.getTexture(Assets.fruit5lv3);
        }else if(mf == 15){
            idle = assets.getTexture(Assets.fruit6lv3);
        }
//        animate1 = assets.getTexture(Assets.targetAnim1);
//        animate2 = assets.getTexture(Assets.targetAnim2);
//        vanish = assets.getTexture(Assets.targetVanish);
        active = true;
//        animating = false;
//        vanishing = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(active) {
            batch.draw(idle, x - idle.getWidth()/2, y - idle.getHeight()/2);
        }
//        if(active) {
//            batch.draw(idle, x - idle.getWidth()/2, y - idle.getHeight()/2);
//        } else if(animating) {
//            if(animatingIndex == 0) {
//                batch.draw(animate1, x - animate1.getWidth()/2, y - animate1.getHeight()/2);
//            } else {
//                batch.draw(animate2, x - animate2.getWidth()/2, y - animate2.getHeight()/2);
//            }
//        }  else if(vanishing){
//            batch.draw(vanish, x - vanish.getWidth()/2, y - vanish.getHeight()/2);
//        }
    }

    @Override
    public void act(float delta) {
//        if(animating || vanishing) {
//            time += delta;
//            if(animating) {
//                if(time <= 1) {
//                    int time2 = (int)(time*10);
//                    animatingIndex = (time2 % 2);
//                } else {
//                    animating = false;
//                    vanishing = true;
//                    time = 0;
//                }
//            } else if(vanishing) {
//                if(time > 0.5f) {
//                    vanishing = false;
//                    remove();
//                }
//            }
//        }
    }

    public float getPositionX() {
        return x;
    }

    public float getPositionY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public void deactivate() {
        active = false;
//        animating = true;
//        animatingIndex = 0;
//        time = 0;
    }
}
