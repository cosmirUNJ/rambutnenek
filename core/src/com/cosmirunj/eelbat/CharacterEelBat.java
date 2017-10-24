package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;

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
    private SpriteBatch batch;
    private Texture img;
    private TextureRegion[][] hero;
    private TextureRegion[] animationFramesAtas;
    private TextureRegion[] animationFramesKanan;
    private TextureRegion[] animationFramesKiri;
    private TextureRegion[] animationFramesBawah;
    private Animation animation;
    private Animation animationAtas;
    private Animation animationKanan;
    private Animation animationKiri;
    private Animation animationBawah;
    private float elapsedTime;

    public CharacterEelBat(EelbatCosmir eelbatCosmir) {
        shadow = eelbatCosmir.assets.getTexture(Assets.shadow);
        //eelbatchar = eelbatCosmir.assets.getTexture(Assets.eelbatpic);
        //eelbatchar = new Texture("eelbat/eelsprite.png");
        //karakter = new TextureRegion(eelbatchar);
        batch = new SpriteBatch();
        img = new Texture("eelbat/eelsprite.png");
        karakter = new TextureRegion(img);
        hero = karakter.split(300, 300);

        animationFramesAtas = new TextureRegion[3];
        animationFramesKanan = new TextureRegion[3];
        animationFramesKiri = new TextureRegion[3];
        animationFramesBawah = new TextureRegion[3];

        int indexAtas = 0;
        int indexKanan = 0;
        int indexKiri = 0;
        int indexBawah = 0;

        for (int i = 0; i < 3; i++){
            animationFramesAtas[indexAtas++] = hero[0][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKanan[indexKanan++] = hero[3][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiri[indexKiri++] = hero[1][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesBawah[indexBawah++] = hero[2][i];
        }

        animationAtas = new Animation(1f/8f,animationFramesAtas);
        animationKiri = new Animation(1f/8f,animationFramesKiri);
        animationKanan = new Animation(1f/8f,animationFramesKanan);
        animationBawah = new Animation(1f/8f,animationFramesBawah);

    }

    void updatePosition(float x, float y, PlayGameStage.DIRECTION direction) {
        this.x = x;
        this.y = y;

        animation = animationBawah;

        if(direction == PlayGameStage.DIRECTION.UP || direction == PlayGameStage.DIRECTION.RIGHT_UP || direction == PlayGameStage.DIRECTION.LEFT_UP) {
            animation = animationAtas;
        } else if(direction == PlayGameStage.DIRECTION.RIGHT) {
            animation = animationKanan;
        } else if(direction == PlayGameStage.DIRECTION.LEFT) {
            animation = animationKiri;
        } else if(direction == PlayGameStage.DIRECTION.DOWN || direction == PlayGameStage.DIRECTION.RIGHT_DOWN || direction == PlayGameStage.DIRECTION.LEFT_DOWN) {
            animation = animationBawah;
        }
        

    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(shadow, x-shadow.getWidth()/2,y-SHADOW_VERTICAL_OFFSET+100);
        //batch.draw(eelbatchar,x,y);
        batch.draw((TextureRegion)animation.getKeyFrame(elapsedTime,true),x-150,y);
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
