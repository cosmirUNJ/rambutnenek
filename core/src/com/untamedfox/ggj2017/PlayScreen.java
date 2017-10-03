package com.untamedfox.ggj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen {

    private final String TAG = "PlayScreen";

    private Viewport hudViewport;
    private PlayHUDStage playHudStage;

    private Viewport gameViewport;
    private PlayGameStage gameStage;

    //tambahan
    private Viewport viewport;
    private OrthographicCamera camera;
    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Drawable touchBackground;
    private Drawable touchKnob;
    public Touchpad touchpad;
    private Stage stage;
    private SpriteBatch batch;
    private final GGJ2017 ggj2017;
    //public PlayScreen(GGJ2017 ggj2017, float initialPitch, float initialRoll) {

    public PlayScreen(GGJ2017 ggj2017) {
        //tambahan
        this.ggj2017 = ggj2017;
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
        viewport = new FillViewport(GGJ2017.WIDTH, GGJ2017.HEIGHT, camera);
        stage = new Stage(viewport, batch); //cek batch nya
        stage.addActor(touchpad);
        float touchpadXnya = touchpad.getKnobPercentX();
        float touchpadYnya = touchpad.getKnobPercentY();
        //tambahanlagi
        /*
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float touchpadXnya = touchpad.getKnobPercentX();
                float touchpadYnya = touchpad.getKnobPercentY();
                //gameStage = new PlayGameStage(gameViewport, ggj2017, playHudStage, touchpadXnya, touchpadYnya);
            }
        });
        */

        Assets.music1.setVolume(1.0f);
        Assets.music1.setLooping(true);
        Assets.music1.play();
        OrthographicCamera hudCamera = new OrthographicCamera();
        hudViewport = new FillViewport(GGJ2017.WIDTH, GGJ2017.HEIGHT, hudCamera);
        playHudStage = new PlayHUDStage(this, hudViewport, ggj2017);

        OrthographicCamera gameCamera = new OrthographicCamera();
        gameViewport = new FillViewport(GGJ2017.WIDTH, GGJ2017.HEIGHT, gameCamera);
        gameStage = new PlayGameStage(gameViewport, ggj2017, playHudStage, touchpadXnya, touchpadYnya, touchpad);





        Gdx.input.setInputProcessor(stage);

        //Gdx.input.setInputProcessor(playHudStage);
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

        playHudStage.act(delta);
        hudViewport.apply();
        playHudStage.draw();

        //tambahan juga
        //batch.end();
        stage.act(delta);
        stage.draw();
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
