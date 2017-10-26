package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    private PlayScreenStage stage2;
    private ImageButton ButtonHome, ButtonAlas;
    private Stage ButtonStage;
    boolean btnPause;

    public PlayScreen(EelbatCosmir eelbatCosmir) {
        this.eelbatCosmir = eelbatCosmir;

        ButtonStage=new Stage();

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("cont/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("cont/touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        //ukuran knob
        touchKnob.setMinHeight(25);
        touchKnob.setMinWidth(25);

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;


        touchpad = new Touchpad(10, touchpadStyle);
        //bound, bound, width dan height background
        touchpad.setBounds(15,15,75,75);
        //lokasi
        touchpad.setPosition(15,75);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, camera);
        stage = new Stage(viewport, batch); //cek batch nya
        ButtonStage.addActor(touchpad);
        //stage.addActor(touchpad);
        float touchpadXnya = touchpad.getKnobPercentX();
        float touchpadYnya = touchpad.getKnobPercentY();


        //Assets.playmusic.setLooping(true);
        //Assets.playmusic.play();

        OrthographicCamera hudCamera = new OrthographicCamera();
        hudViewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, hudCamera);
        playHUD = new PlayHUDStage(this, hudViewport, eelbatCosmir);

        OrthographicCamera gameCamera = new OrthographicCamera();
        gameViewport = new FillViewport(eelbatCosmir.WIDTH,eelbatCosmir.HEIGHT, gameCamera);
        gameStage = new PlayGameStage(gameViewport, eelbatCosmir, playHUD, touchpadXnya, touchpadYnya, touchpad);

        Gdx.input.setInputProcessor(stage);

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, camera);
        stage2 = new PlayScreenStage(viewport, eelbatCosmir);
        //Gdx.input.setInputProcessor(stage2);

        ButtonPause();
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

        ButtonStage.act(Gdx.graphics.getDeltaTime());
        ButtonStage.draw();

        playHUD.update(delta);
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

    private class PlayScreenStage extends Stage {
        public PlayScreenStage(Viewport viewport, EelbatCosmir eelbatCosmir) {

        }
    }

    public void ButtonPause(){
        float widthScreen = Gdx.graphics.getWidth();
        float heightScreen = Gdx.graphics.getHeight();

        //deee?
        //Button Alas
        Texture BtnAlas = eelbatCosmir.assets.getTexture(Assets.btnAlas);
        TextureRegionDrawable BtnImageAlas = new TextureRegionDrawable(new TextureRegion(BtnAlas));
        ButtonAlas = new ImageButton(BtnImageAlas);
        ButtonAlas.setSize(BtnAlas.getWidth(),BtnAlas.getHeight());
        ButtonAlas.setPosition(widthScreen/2, heightScreen/2);

        ButtonStage.addActor(ButtonAlas);
        ButtonAlas.setVisible(false);

        //Button Home
        Texture BtnHome = eelbatCosmir.assets.getTexture(Assets.btnHome);
        TextureRegionDrawable BtnImageHome = new TextureRegionDrawable(new TextureRegion(BtnHome));
        ButtonHome = new ImageButton(BtnImageHome);
        ButtonHome.setSize(BtnHome.getWidth()-100,BtnHome.getHeight()-100);
        ButtonHome.setPosition(widthScreen-80, 25);
        ButtonHome.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonHome.getImage().setColor(Color.BROWN);
                btnPause=true;
                ButtonAlas.setVisible(true);
                //System.out.print("bisa");
//                ButtonMedium.setVisible(true);
//                ButtonHard.setVisible(true);

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonHome.getImage().setColor(Color.WHITE);
                super.touchUp(event, x, y, pointer, button);
            }
        });


        ButtonStage.addActor(ButtonHome);
        Gdx.input.setInputProcessor(ButtonStage);

    }
}
