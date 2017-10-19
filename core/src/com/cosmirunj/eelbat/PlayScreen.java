package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Inovatif on 10/18/2017.
 */

class PlayScreen implements Screen {
    private Viewport hudViewport;
    private PlayHUDStage playHUD;

    private Viewport gameViewport;
    private PlayGameStage gameStage;

    private Viewport viewport;
    private OrthographicCamera camera;
    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Drawable touchKnob;
    public Touchpad touchpad;
    private Stage stage;
    private SpriteBatch batch;
    private final EelbatCosmir eelbatCosmir;

    public PlayScreen(EelbatCosmir eelbatCosmir) {
        this.eelbatCosmir = eelbatCosmir;
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("cont/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("cont/touchKnob.png"));
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(15,15,200,200);
        touchpad.setPosition(15,100);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, camera);
        stage = new Stage(viewport, batch); //cek batch nya
        stage.addActor(touchpad);
        float touchpadXnya = touchpad.getKnobPercentX();
        float touchpadYnya = touchpad.getKnobPercentY();


        //Assets.playmusic.setLooping(false);
        //Assets.playmusic.play();

        OrthographicCamera hudCamera = new OrthographicCamera();
        hudViewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, hudCamera);
        playHUD = new PlayHUDStage(this, hudViewport, eelbatCosmir);

        OrthographicCamera gameCamera = new OrthographicCamera();
        gameViewport = new FillViewport(eelbatCosmir.WIDTH,eelbatCosmir.HEIGHT, gameCamera);
        gameStage = new PlayGameStage(gameViewport, eelbatCosmir, playHUD, touchpadXnya, touchpadYnya, touchpad);

        Gdx.input.setInputProcessor(stage);
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

        //tambahan
        //batch.begin();

        gameStage.act(delta);
        gameViewport.apply();
        gameStage.draw();


        playHUD.act(delta);
        hudViewport.apply();
        playHUD.draw();

        //tambahan juga
        //batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        hudViewport.update(width,height);
        gameViewport.update(width,height);
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
