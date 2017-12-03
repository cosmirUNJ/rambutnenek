package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Amelia Apriliani on 22/10/2017.
 */

public class FinishedScreen implements Screen {
    private GameOverStage stage;
    private Viewport viewport;
    private EelbatCosmir eelbatCosmir;

    public FinishedScreen(EelbatCosmir eelbatCosmir, boolean completed) {
        //Assets.music1.stop();
        //Assets.steps.stop();
        this.eelbatCosmir = eelbatCosmir;
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(EelbatCosmir.WIDTH, EelbatCosmir.HEIGHT, camera);
        stage = new GameOverStage(viewport, eelbatCosmir, completed);
        if(!completed){
            //Assets.death.play();
        }
        else{
            //Assets.wincry.play();
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        stage.draw();
        if(Gdx.input.justTouched()) {
            eelbatCosmir.setScreen(new MainMenu(eelbatCosmir));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private class GameOverStage extends Stage {
        public GameOverStage(Viewport viewport, final EelbatCosmir eelbatCosmir, boolean completed) {
            super(viewport, eelbatCosmir.batch);
            Texture backgroundTexture = eelbatCosmir.assets.getTexture(completed ? Assets.levelComplete : Assets.gameOver);
            addActor(new Image(backgroundTexture));
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontLarge);
            String message = completed ? "Level n complete!" : "Game Over :(";
            Label label = new Label(message, labelStyle);
            label.setPosition(
                    300,
                    (EelbatCosmir.HEIGHT - label.getPrefHeight())/2);
            addActor(label);
            Label.LabelStyle labelStyle2 = new Label.LabelStyle();
            labelStyle2.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontSmall);
            String message2 = completed ? "Tap to next level!" : "Tap to play again";
            Label label2 = new Label(message2, labelStyle2);
            label2.setPosition(
                    1500,
                    (EelbatCosmir.HEIGHT - label.getPrefHeight())/5);
            addActor(label2);
        }
    }
}
