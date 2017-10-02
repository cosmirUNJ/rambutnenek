package com.untamedfox.ggj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {

    private final String TAG = "PlayScreen";

    private Viewport hudViewport;
    private PlayHUDStage playHudStage;

    private Viewport gameViewport;
    private PlayGameStage gameStage;

    public PlayScreen(GGJ2017 ggj2017, float initialPitch, float initialRoll) {
        Assets.music1.setVolume(1.0f);
        Assets.music1.setLooping(true);
        Assets.music1.play();
        OrthographicCamera hudCamera = new OrthographicCamera();
        hudViewport = new FillViewport(GGJ2017.WIDTH, GGJ2017.HEIGHT, hudCamera);
        playHudStage = new PlayHUDStage(this, hudViewport, ggj2017);

        OrthographicCamera gameCamera = new OrthographicCamera();
        gameViewport = new FillViewport(GGJ2017.WIDTH, GGJ2017.HEIGHT, gameCamera);
        gameStage = new PlayGameStage(gameViewport, ggj2017, playHudStage, initialPitch, initialRoll);

        Gdx.input.setInputProcessor(playHudStage);
    }

    boolean sendMainWave() {
        return gameStage.sendMainWave();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.act(delta);
        gameViewport.apply();
        gameStage.draw();

        playHudStage.act(delta);
        hudViewport.apply();
        playHudStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        hudViewport.update(width, height);
        gameViewport.update(width, height);
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
}
