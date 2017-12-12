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
    private TextureRegion karakter, karakterEelbat, karakterWinged;
    private float x,y;
    private final int VERTICAL_OFFSET = 120;
    private final int SHADOW_VERTICAL_OFFSET = 180;
    private SpriteBatch batch;
    private Texture img, imgEelbat, imgWinged;
    private TextureRegion[][] hero, heroEelbat, heroWinged;
    private TextureRegion[] animationFramesAtas, animationFramesKanan, animationFramesKiri, animationFramesKananAtas, animationFramesKiriAtas, animationFramesBawah, animationFramesKananBawah, animationFramesKiriBawah;
    private TextureRegion[] animationFramesAtasEelbat, animationFramesKananEelbat, animationFramesKiriEelbat, animationFramesKananAtasEelbat, animationFramesKiriAtasEelbat, animationFramesBawahEelbat, animationFramesKananBawahEelbat, animationFramesKiriBawahEelbat;
    private TextureRegion[] animationFramesAtasWinged, animationFramesKananWinged, animationFramesKiriWinged, animationFramesKananAtasWinged, animationFramesKiriAtasWinged, animationFramesBawahWinged, animationFramesKananBawahWinged, animationFramesKiriBawahWinged;
    private Animation animation, animationAtas, animationKanan, animationKiri, animationKananAtas, animationKiriAtas, animationBawah, animationKananBawah, animationKiriBawah;
    private Animation animationAtasEelbat, animationKananEelbat, animationKiriEelbat, animationKananAtasEelbat, animationKiriAtasEelbat, animationBawahEelbat, animationKananBawahEelbat, animationKiriBawahEelbat;
    private Animation animationAtasWinged, animationKananWinged, animationKiriWinged, animationKananAtasWinged, animationKiriAtasWinged, animationBawahWinged, animationKananBawahWinged, animationKiriBawahWinged;
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

        imgEelbat = new Texture("eelbat/eelbatsprite.png");
        karakterEelbat = new TextureRegion(imgEelbat);
        heroEelbat = karakterEelbat.split(300, 300);

        imgWinged = new Texture("eelbat/wingedsprite.png");
        karakterWinged = new TextureRegion(imgWinged);
        heroWinged = karakterWinged.split(300, 300);

        animationFramesAtas = new TextureRegion[3];
        animationFramesKanan = new TextureRegion[3];
        animationFramesKananAtas = new TextureRegion[3];
        animationFramesKiri = new TextureRegion[3];
        animationFramesKiriAtas = new TextureRegion[3];
        animationFramesBawah = new TextureRegion[3];
        animationFramesKananBawah = new TextureRegion[3];
        animationFramesKiriBawah = new TextureRegion[3];

        int indexAtas = 0;
        int indexKanan = 0;
        int indexKananAtas = 0;
        int indexKiri = 0;
        int indexKiriAtas = 0;
        int indexBawah = 0;
        int indexKananBawah = 0;
        int indexKiriBawah = 0;

        for (int i = 0; i < 3; i++){
            animationFramesAtas[indexAtas++] = hero[4][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKanan[indexKanan++] = hero[5][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananAtas[indexKananAtas++] = hero[7][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiri[indexKiri++] = hero[1][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriAtas[indexKiriAtas++] = hero[3][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesBawah[indexBawah++] = hero[0][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananBawah[indexKananBawah++] = hero[6][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriBawah[indexKiriBawah++] = hero[2][i];
        }

        animationAtas = new Animation(1f/8f,animationFramesAtas);
        animationKiri = new Animation(1f/8f,animationFramesKiri);
        animationKanan = new Animation(1f/8f,animationFramesKanan);
        animationKiriAtas = new Animation(1f/8f,animationFramesKiriAtas);
        animationKananAtas = new Animation(1f/8f,animationFramesKananAtas);
        animationBawah = new Animation(1f/8f,animationFramesBawah);
        animationKiriBawah = new Animation(1f/8f,animationFramesKiriBawah);
        animationKananBawah = new Animation(1f/8f,animationFramesKananBawah);

        //EELBATHEADED
        animationFramesAtasEelbat = new TextureRegion[3];
        animationFramesKananEelbat = new TextureRegion[3];
        animationFramesKananAtasEelbat = new TextureRegion[3];
        animationFramesKiriEelbat = new TextureRegion[3];
        animationFramesKiriAtasEelbat = new TextureRegion[3];
        animationFramesBawahEelbat = new TextureRegion[3];
        animationFramesKananBawahEelbat = new TextureRegion[3];
        animationFramesKiriBawahEelbat = new TextureRegion[3];

        int indexAtasEelbat = 0;
        int indexKananEelbat = 0;
        int indexKananAtasEelbat = 0;
        int indexKiriEelbat = 0;
        int indexKiriAtasEelbat = 0;
        int indexBawahEelbat = 0;
        int indexKananBawahEelbat = 0;
        int indexKiriBawahEelbat = 0;

        for (int i = 0; i < 3; i++){
            animationFramesAtasEelbat[indexAtasEelbat++] = heroEelbat[4][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananEelbat[indexKananEelbat++] = heroEelbat[5][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananAtasEelbat[indexKananAtasEelbat++] = heroEelbat[7][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriEelbat[indexKiriEelbat++] = heroEelbat[1][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriAtasEelbat[indexKiriAtasEelbat++] = heroEelbat[3][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesBawahEelbat[indexBawahEelbat++] = heroEelbat[0][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananBawahEelbat[indexKananBawahEelbat++] = heroEelbat[6][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriBawahEelbat[indexKiriBawahEelbat++] = heroEelbat[2][i];
        }

        animationAtasEelbat = new Animation(1f/8f,animationFramesAtasEelbat);
        animationKiriEelbat = new Animation(1f/8f,animationFramesKiriEelbat);
        animationKananEelbat = new Animation(1f/8f,animationFramesKananEelbat);
        animationKiriAtasEelbat = new Animation(1f/8f,animationFramesKiriAtasEelbat);
        animationKananAtasEelbat = new Animation(1f/8f,animationFramesKananAtasEelbat);
        animationBawahEelbat = new Animation(1f/8f,animationFramesBawahEelbat);
        animationKiriBawahEelbat = new Animation(1f/8f,animationFramesKiriBawahEelbat);
        animationKananBawahEelbat = new Animation(1f/8f,animationFramesKananBawahEelbat);

        //WINGED EELBAT
        animationFramesAtasWinged = new TextureRegion[3];
        animationFramesKananWinged = new TextureRegion[3];
        animationFramesKananAtasWinged = new TextureRegion[3];
        animationFramesKiriWinged = new TextureRegion[3];
        animationFramesKiriAtasWinged = new TextureRegion[3];
        animationFramesBawahWinged = new TextureRegion[3];
        animationFramesKananBawahWinged = new TextureRegion[3];
        animationFramesKiriBawahWinged = new TextureRegion[3];

        int indexAtasWinged = 0;
        int indexKananWinged = 0;
        int indexKananAtasWinged = 0;
        int indexKiriWinged = 0;
        int indexKiriAtasWinged = 0;
        int indexBawahWinged = 0;
        int indexKananBawahWinged = 0;
        int indexKiriBawahWinged = 0;

        for (int i = 0; i < 3; i++){
            animationFramesAtasWinged[indexAtasWinged++] = heroWinged[4][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananWinged[indexKananWinged++] = heroWinged[5][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananAtasWinged[indexKananAtasWinged++] = heroWinged[7][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriWinged[indexKiriWinged++] = heroWinged[1][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriAtasWinged[indexKiriAtasWinged++] = heroWinged[3][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesBawahWinged[indexBawahWinged++] = heroWinged[0][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKananBawahWinged[indexKananBawahWinged++] = heroWinged[6][i];
        }

        for (int i = 0; i < 3; i++){
            animationFramesKiriBawahWinged[indexKiriBawahWinged++] = heroWinged[2][i];
        }

        animationAtasWinged = new Animation(1f/8f,animationFramesAtasWinged);
        animationKiriWinged = new Animation(1f/8f,animationFramesKiriWinged);
        animationKananWinged = new Animation(1f/8f,animationFramesKananWinged);
        animationKiriAtasWinged = new Animation(1f/8f,animationFramesKiriAtasWinged);
        animationKananAtasWinged = new Animation(1f/8f,animationFramesKananAtasWinged);
        animationBawahWinged = new Animation(1f/8f,animationFramesBawahWinged);
        animationKiriBawahWinged = new Animation(1f/8f,animationFramesKiriBawahWinged);
        animationKananBawahWinged = new Animation(1f/8f,animationFramesKananBawahWinged);

    }

    void updatePosition(float x, float y, PlayGameStage.DIRECTION direction, PlayGameStage.FORM form) {
        this.x = x;
        this.y = y;

        if(form == PlayGameStage.FORM.EEL){
            animation = animationBawah;

            if(direction == PlayGameStage.DIRECTION.UP) {
                animation = animationAtas;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT) {
                animation = animationKanan;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT_UP) {
                animation = animationKananAtas;
            } else if(direction == PlayGameStage.DIRECTION.LEFT) {
                animation = animationKiri;
            } else if(direction == PlayGameStage.DIRECTION.LEFT_UP) {
                animation = animationKiriAtas;
            } else if(direction == PlayGameStage.DIRECTION.DOWN) {
                animation = animationBawah;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT_DOWN) {
                animation = animationKananBawah;
            } else if(direction == PlayGameStage.DIRECTION.LEFT_DOWN) {
                animation = animationKiriBawah;
            }
        }else if(form == PlayGameStage.FORM.EELBAT){
            animation = animationBawahEelbat;

            if(direction == PlayGameStage.DIRECTION.UP) {
                animation = animationAtasEelbat;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT) {
                animation = animationKananEelbat;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT_UP) {
                animation = animationKananAtasEelbat;
            } else if(direction == PlayGameStage.DIRECTION.LEFT) {
                animation = animationKiriEelbat;
            } else if(direction == PlayGameStage.DIRECTION.LEFT_UP) {
                animation = animationKiriAtasEelbat;
            } else if(direction == PlayGameStage.DIRECTION.DOWN) {
                animation = animationBawahEelbat;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT_DOWN) {
                animation = animationKananBawahEelbat;
            } else if(direction == PlayGameStage.DIRECTION.LEFT_DOWN) {
                animation = animationKiriBawahEelbat;
            }
        }else if(form == PlayGameStage.FORM.WINGED_EELBAT){
            animation = animationBawahWinged;

            if(direction == PlayGameStage.DIRECTION.UP) {
                animation = animationAtasWinged;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT) {
                animation = animationKananWinged;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT_UP) {
                animation = animationKananAtasWinged;
            } else if(direction == PlayGameStage.DIRECTION.LEFT) {
                animation = animationKiriWinged;
            } else if(direction == PlayGameStage.DIRECTION.LEFT_UP) {
                animation = animationKiriAtasWinged;
            } else if(direction == PlayGameStage.DIRECTION.DOWN) {
                animation = animationBawahWinged;
            } else if(direction == PlayGameStage.DIRECTION.RIGHT_DOWN) {
                animation = animationKananBawahWinged;
            } else if(direction == PlayGameStage.DIRECTION.LEFT_DOWN) {
                animation = animationKiriBawahWinged;
            }
        }
        

    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(shadow, x-shadow.getWidth()/2,y-shadow.getHeight()/2);
        //batch.draw(eelbatchar,x,y);
        batch.draw((TextureRegion)animation.getKeyFrame(elapsedTime,true),x-150,y-150);
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
