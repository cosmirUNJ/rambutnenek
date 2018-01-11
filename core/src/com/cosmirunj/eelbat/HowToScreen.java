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
 * Created by USER on 11/01/2018.
 */

public class HowToScreen implements Screen {
    private HowToPlay stage;
    private Viewport viewport;
    private EelbatCosmir eelbatCosmir;

    public HowToScreen(EelbatCosmir eelbatCosmir) {
        this.eelbatCosmir = eelbatCosmir;
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(EelbatCosmir.WIDTH, EelbatCosmir.HEIGHT, camera);
        stage = new HowToPlay(viewport, eelbatCosmir);
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

    private class HowToPlay extends Stage {
        public HowToPlay(Viewport viewport, final EelbatCosmir eelbatCosmir) {
            super(viewport, eelbatCosmir.batch);
            Texture backgroundTexture = eelbatCosmir.assets.getTexture(Assets.instruction);
            addActor(new Image(backgroundTexture));
//            Label.LabelStyle labelStyle = new Label.LabelStyle();
//            labelStyle.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontLarge);
//            String message = "Aturan Main!";
//            Label label = new Label(message, labelStyle);
//            label.setPosition(
//                    300,
//                    (EelbatCosmir.HEIGHT - label.getPrefHeight()));
//            addActor(label);
        }
    }
}
