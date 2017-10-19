package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Inovatif on 10/18/2017.
 */

class MainMenu implements Screen {
    private final String TAG = "MainMenuScreen";

    private MainMenuStage stage;
    private Viewport viewport;
    private EelbatCosmir eelbatCosmir;

    public MainMenu(EelbatCosmir eelbatCosmir) {
        this.eelbatCosmir = eelbatCosmir;

        Assets.mainmenumusic.setLooping(false);
        Assets.mainmenumusic.play();

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, camera);
        stage = new MainMenuStage(viewport, eelbatCosmir);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        stage.draw();
        if(Gdx.input.justTouched()){
            Assets.mainmenumusic.stop();
            eelbatCosmir.setScreen(new PlayScreen(eelbatCosmir));
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

    private class MainMenuStage extends Stage {
        public MainMenuStage(Viewport viewport, EelbatCosmir eelbatCosmir) {
            super(viewport, eelbatCosmir.batch);
            addActor(new Image(eelbatCosmir.assets.getTexture(Assets.menu)));
            Label.LabelStyle labelStyleSmall = new Label.LabelStyle();
            labelStyleSmall.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontSmall);
            Label labelSmall = new Label("Eelbat",labelStyleSmall);
            labelSmall.setPosition(1500,100);
            addActor(labelSmall);
        }
    }
}
