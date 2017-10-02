package com.untamedfox.ggj2017;

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

public class FinishedScreen implements Screen {

    private GameOverStage stage;
    private Viewport viewport;
    private GGJ2017 ggj2017;

    public FinishedScreen(GGJ2017 ggj2017, boolean win) {
        Assets.music1.stop();
        Assets.steps.stop();
        this.ggj2017 = ggj2017;
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(GGJ2017.WIDTH, GGJ2017.HEIGHT, camera);
        stage = new GameOverStage(viewport, ggj2017, win);
        if(!win){
            Assets.death.play();
        }
        else{
            Assets.wincry.play();
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
            ggj2017.setScreen(new MainMenuScreen(ggj2017));
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
        public GameOverStage(Viewport viewport, final GGJ2017 ggj2017, boolean win) {
            super(viewport, ggj2017.batch);
            Texture backgroundTexture = ggj2017.assets.getTexture(win ? Assets.win : Assets.gameOver);
            addActor(new Image(backgroundTexture));
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = ggj2017.assets.getBitmapFont(Assets.bitmapFontLarge);
            String message = win ? "You rock!" : "Game Over :(";
            Label label = new Label(message, labelStyle);
            label.setPosition(
                    300,
                    (GGJ2017.HEIGHT - label.getPrefHeight())/2);
            addActor(label);
        }
    }
}
