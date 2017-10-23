package com.cosmirunj.eelbat;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Inovatif on 10/18/2017.
 */

class PlayGameStage extends Stage {
    private PlayHUDStage playHUDStage;
    private EelbatCosmir eelbatCosmir;
    private ShapeRenderer shapeRenderer;
    private float touchpadXnya;
    private float touchpadYnya;
    private Touchpad touchpad;
    private Vector3 cameraPosition;
    private BackgroundTiles backgroundTiles;
    private float x = 0;
    private float y = 0;

    private float time;
    private final int TOTAL_TIME = 3*60;

    private float score;
    private final int TOTAL_SCORE = 200;


    private CharacterEelBat characterEelBat;

    public PlayGameStage(Viewport gameViewport, EelbatCosmir eelbatCosmir, PlayHUDStage playHUDStage, float touchpadXnya, float touchpadYnya, Touchpad touchpad) {
        super(gameViewport, eelbatCosmir.batch);
        this.playHUDStage = playHUDStage;
        this.eelbatCosmir = eelbatCosmir;

        time = TOTAL_TIME;
        score = TOTAL_SCORE;


        shapeRenderer = new ShapeRenderer();

        this.touchpadXnya = touchpadXnya;
        this.touchpadYnya = touchpadYnya;
        this.touchpad = touchpad;

        cameraPosition = getViewport().getCamera().position;

        backgroundTiles = new BackgroundTiles(eelbatCosmir);
        addActor(backgroundTiles);

        characterEelBat = new CharacterEelBat(eelbatCosmir);
        characterEelBat.updatePosition(x,y,DIRECTION.NONE);

        //backgroundTiles.update(x,y);

    }

    public boolean sendMainWave() {
        return true;
    }

    @Override
    public void act(float delta) {
        updateLocation(delta);
        time -= delta;
    }

    private void updateLocation(float delta){
        playHUDStage.update(time);

    }


    enum DIRECTION{RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN, NONE}
}
