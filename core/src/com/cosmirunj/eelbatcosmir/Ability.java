package com.cosmirunj.eelbatcosmir;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by USER on 11/12/2017.
 */

public class Ability extends Actor {
    private final String TAG = "Ability";

    //private final int SPEED = 5000;
    //private final int MAX_DRAW_RADIUS_X = EelbatCosmir.WIDTH;

    private PlayGameStage playGameStage;
    private float radiusX, radiusY;
    private final float CENTER_X, CENTER_Y;
    private boolean alive;

    //private final boolean mainWave;


    public Ability(PlayGameStage playGameStage, float centerX, float centerY) {
        this.playGameStage = playGameStage;
        //this.mainWave = mainWave;
        radiusX = 50;
        radiusY = 25;
        CENTER_X = centerX;
        CENTER_Y = centerY;
        alive = true;
    }

    @Override
    public void act(float delta) {
        if(alive) {
            //radiusX += SPEED*delta;
            //radiusY += SPEED/2*delta;
//            if(mainWave) {
//                playGameStage.checkHitsWithOtherObjects(CENTER_X, CENTER_Y, radiusX, radiusY);
//            } else {
//                playGameStage.checkHitsWithMainCharacter(this, CENTER_X, CENTER_Y, radiusX, radiusY);
//            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(alive) {
            //batch.end();
            playGameStage.shapeRenderer.setProjectionMatrix(playGameStage.getCamera().combined);
            playGameStage.shapeRenderer.setColor(Color.WHITE);
            //playGameStage.shapeRenderer.setColor(mainWave ? Color.BLACK : Color.WHITE);
            playGameStage.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            playGameStage.shapeRenderer.ellipse(CENTER_X, CENTER_Y, 2*radiusX, 2*radiusY);
//            for(int i = 0; i < 8; i++) {
//                playGameStage.shapeRenderer.ellipse(CENTER_X - radiusX - i, CENTER_Y - radiusY - i, 2*radiusX + 2*i, 2*radiusY + 2*i);
//            }
            playGameStage.shapeRenderer.end();
            //batch.begin();
        }
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
