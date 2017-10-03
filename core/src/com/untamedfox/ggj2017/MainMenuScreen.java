package com.untamedfox.ggj2017;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {

    private final String TAG = "MainMenuScreen";

    private MainMenuStage stage;
    private Viewport viewport;
    private GGJ2017 ggj2017;

    public MainMenuScreen(GGJ2017 ggj2017) {
        this.ggj2017 = ggj2017;
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(GGJ2017.WIDTH, GGJ2017.HEIGHT, camera);
        stage = new MainMenuStage(viewport, ggj2017);
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
            //float pitch = Gdx.input.getPitch();
            //float roll = Gdx.input.getRoll();
            //ketika dipencet, pindah ke class playscreen
            ggj2017.setScreen(new PlayScreen(ggj2017));
            //ggj2017.setScreen(new PlayScreen(ggj2017, pitch, roll));
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        //cek https://gist.github.com/Leejjon/7fb8aa3ea2e4024a9eba31fa4f3339fb
        public MainMenuStage(Viewport viewport, final GGJ2017 ggj2017) {
            //super();
            super(viewport, ggj2017.batch);
            addActor(new Image(ggj2017.assets.getTexture(Assets.menu)));
            if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
                Label.LabelStyle labelStyleSmall = new Label.LabelStyle();
                labelStyleSmall.font = ggj2017.assets.getBitmapFont(Assets.bitmapFontSmall);
                Label labelSmall = new Label("[  LEFT  /  RIGHT  /  UP  /  DOWN  ]  +  [  W  ]", labelStyleSmall);
                labelSmall.setPosition(1500, 100);
                addActor(labelSmall);
            }
        }
    }
}