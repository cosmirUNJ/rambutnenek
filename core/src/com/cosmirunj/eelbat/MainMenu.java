package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    private Button.ButtonStyle buttonStyle;
    private TextureRegionDrawable playButton;
    private ImageButton ButtonPlay, ButtonEasy, ButtonMedium, ButtonHard, ButtonSoundActive;
    private Stage ButtonStage;
    boolean btnPlay;
    boolean playMusic;

    public MainMenu(EelbatCosmir eelbatCosmir) {
        this.eelbatCosmir = eelbatCosmir;

        Assets.mainmenumusic.setLooping(true);
        Assets.mainmenumusic.play();

        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, camera);
        stage = new MainMenuStage(viewport, eelbatCosmir);
        Gdx.input.setInputProcessor(stage);

        playMusic = true;

        ButtonStage=new Stage();
        ButtonPlay();
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
        ButtonStage.draw();
//        if(Gdx.input.justTouched()){
//            Assets.mainmenumusic.stop();
//            eelbatCosmir.setScreen(new PlayScreen(eelbatCosmir));
//        }
//        ButtonStage.act();

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
//            Label.LabelStyle labelStyleSmall = new Label.LabelStyle();
//            labelStyleSmall.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontSmall);
//            Label labelSmall = new Label("Tap to Play",labelStyleSmall);
//            labelSmall.setPosition(1150,300);
//            addActor(labelSmall);
        }
    }

    public void ButtonPlay(){
        float widthScreen = Gdx.graphics.getWidth();
        float heightScreen = Gdx.graphics.getHeight();

        //Button Sound
        Texture BtnSoundActive = eelbatCosmir.assets.getTexture(Assets.btnSoundActive);
        TextureRegionDrawable BtnImageSound = new TextureRegionDrawable(new TextureRegion(BtnSoundActive));
        ButtonSoundActive = new ImageButton(BtnImageSound);
        ButtonSoundActive.setSize(BtnSoundActive.getWidth()-100,BtnSoundActive.getHeight()-100);
        ButtonSoundActive.setPosition(widthScreen-((BtnSoundActive.getWidth()-100)/2), heightScreen-((BtnSoundActive.getHeight()-100)/2));
        //(widthScreen/2)-(buttonAlasWidth/2), (heightScreen/2)-(buttonAlasHeight/2)
        ButtonSoundActive.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonSoundActive.getImage().setColor(Color.BROWN);
                playMusic = !playMusic;

                if (playMusic){
                    Assets.mainmenumusic.play();
                } else {
                    Assets.mainmenumusic.stop();
                }
                //btnPlay ? Assets.mainmenumusic.stop() :  Assets.mainmenumusic.play() ;
                //Assets.mainmenumusic.stop();
                return playMusic;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonSoundActive.getImage().setColor(Color.WHITE);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        ButtonStage.addActor(ButtonSoundActive);

        //Button Easy
        Texture BtnEasy = eelbatCosmir.assets.getTexture(Assets.btnEasy);
        TextureRegionDrawable BtnImageEasy = new TextureRegionDrawable(new TextureRegion(BtnEasy));
        ButtonEasy = new ImageButton(BtnImageEasy);
        ButtonEasy.setSize(BtnEasy.getWidth(),BtnEasy.getHeight());
        ButtonEasy.setPosition(widthScreen/2-(486/2), 20);
        ButtonEasy.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonEasy.getImage().setColor(Color.BLACK);
                Assets.mainmenumusic.stop();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonEasy.getImage().setColor(Color.WHITE);
                super.touchUp(event, x, y, pointer, button);
                eelbatCosmir.setScreen(new PlayScreen(eelbatCosmir));
            }
        });

        ButtonStage.addActor(ButtonEasy);
        ButtonEasy.setVisible(false);

        //Button Medium
        Texture BtnMedium = eelbatCosmir.assets.getTexture(Assets.btnMedium);
        TextureRegionDrawable BtnImageMedium = new TextureRegionDrawable(new TextureRegion(BtnMedium));
        ButtonMedium = new ImageButton(BtnImageMedium);
        ButtonMedium.setSize(BtnMedium.getWidth(),BtnMedium.getHeight());
        ButtonMedium.setPosition(widthScreen/2-(162/2), 20);
        ButtonMedium.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonMedium.getImage().setColor(Color.BLACK);
                Assets.mainmenumusic.stop();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonMedium.getImage().setColor(Color.WHITE);
                super.touchUp(event, x, y, pointer, button);
                eelbatCosmir.setScreen(new PlayScreen(eelbatCosmir));
            }
        });

        ButtonStage.addActor(ButtonMedium);
        ButtonMedium.setVisible(false);

        //Button Hard
        Texture BtnHard = eelbatCosmir.assets.getTexture(Assets.btnHard);
        TextureRegionDrawable BtnImageHard = new TextureRegionDrawable(new TextureRegion(BtnHard));
        ButtonHard = new ImageButton(BtnImageHard);
        ButtonHard.setSize(BtnHard.getWidth(),BtnHard.getHeight());
        ButtonHard.setPosition(widthScreen/2+(162/2), 20);
        ButtonHard.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonHard.getImage().setColor(Color.BLACK);
                Assets.mainmenumusic.stop();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonHard.getImage().setColor(Color.WHITE);
                super.touchUp(event, x, y, pointer, button);
                eelbatCosmir.setScreen(new PlayScreen(eelbatCosmir));
            }
        });

        ButtonStage.addActor(ButtonHard);
        ButtonHard.setVisible(false);

        //Button Play
        Texture Btn = eelbatCosmir.assets.getTexture(Assets.btnPlay);
        TextureRegionDrawable BtnImage = new TextureRegionDrawable(new TextureRegion(Btn));
        ButtonPlay = new ImageButton(BtnImage);
        ButtonPlay.setSize(Btn.getWidth(),Btn.getHeight());
        ButtonPlay.setPosition(widthScreen/2-(Btn.getWidth()/2), 75);
        ButtonPlay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonPlay.getImage().setColor(Color.BROWN);
                btnPlay=true;
                ButtonEasy.setVisible(true);
                ButtonMedium.setVisible(true);
                ButtonHard.setVisible(true);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonPlay.getImage().setColor(Color.WHITE);
                super.touchUp(event, x, y, pointer, button);
            }
        });

        ButtonStage.addActor(ButtonPlay);
        Gdx.input.setInputProcessor(ButtonStage);
    }
}
