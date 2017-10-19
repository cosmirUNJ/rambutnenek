package com.cosmirunj.eelbat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Inovatif on 10/18/2017.
 */

public class CharacterEelBat extends Actor {

    private Texture shadow;
    private Texture eelbatchar;
    private TextureRegion karakter;
    private float x,y;
    private final int VERTICAL_OFFSET = 120;
    private final int SHADOW_VERTICAL_OFFSET = 180;

    public CharacterEelBat(EelbatCosmir eelbatCosmir) {
        shadow = eelbatCosmir.assets.getTexture(Assets.shadow);
        //eelbatchar = eelbatCosmir.assets.getTexture(Assets.eelbatpic);
        eelbatchar = new Texture("eelbat/eelbatpic.png");
        karakter = new TextureRegion(eelbatchar);
    }

    public void updatePosition(float x, float y, PlayGameStage.DIRECTION direction) {
        this.x = x;
        this.y = y;
        

    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(shadow, x-shadow.getWidth()/2,y-SHADOW_VERTICAL_OFFSET);
        batch.draw(eelbatchar,x,y);
    }

    private enum STATE{
        IDLE, RUN;

        @Override
        public String toString() {
            return this == IDLE ? "idle" : "run";
        }
    }

    private enum FACING{FRONT, BACK, RIGHT, LEFT}
}
