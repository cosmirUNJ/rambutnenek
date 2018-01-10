package com.cosmirunj.eelbat;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.cosmirunj.eelbat.Assets.btnSkill;
import static com.cosmirunj.eelbat.Assets.btnSkillActive;

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
    private int level;
    private int difficulty;
    private Aksesoris aksesoris;

    private PlayScreenStage stage2;
    private Label pauseLabel;
    private Label.LabelStyle pauseLabelStyle;
    private ImageButton ButtonHome, ButtonAlas, ButtonResume, ButtonReplay, ButtonSetting, ButtonExit;
    private ImageButton ButtonSkill, ButtonSonar;
    private TextureRegionDrawable sonarIdle, sonarActive, sonarCooldown, skillIdle, skillActive;

    private Button.ButtonStyle buttonStyle;
    private Button sonarButtonAndroid, skillButtonAndroid;
    private boolean sonarButtonActive, skillButtonActive;

    private Stage ButtonStage;
    boolean btnPause;
    public int enemySpeed;
    int aksesorisSpeed;
    //boolean btnResume;

    ShapeRenderer shapeRenderer;
    boolean drawDebug;


    public PlayScreen(EelbatCosmir eelbatCosmir, int level, int difficulty) {

        float widthScreen = Gdx.graphics.getWidth();
        float heightScreen = Gdx.graphics.getHeight();

        this.eelbatCosmir = eelbatCosmir;
        this.level = level;
        this.difficulty = difficulty;

        ButtonStage=new Stage();

        btnPause = true;


        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("cont/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("cont/touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        //ukuran knob
        touchKnob.setMinHeight(50);
        touchKnob.setMinWidth(50);

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;


        touchpad = new Touchpad(10, touchpadStyle);
        //bound, bound, width dan height background
        touchpad.setBounds(30,30,150,150);
        //lokasi
        touchpad.setPosition(widthScreen*0.05F, heightScreen*0.085F);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, camera);
        stage = new Stage(viewport, batch); //cek batch nya
        ButtonStage.addActor(touchpad);
        //stage.addActor(touchpad);
        float touchpadXnya = touchpad.getKnobPercentX();
        float touchpadYnya = touchpad.getKnobPercentY();


        Assets.playmusic.setLooping(true);
        Assets.playmusic.play();

        OrthographicCamera hudCamera = new OrthographicCamera();
        hudViewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, hudCamera);
        playHUD = new PlayHUDStage(this, hudViewport, eelbatCosmir, level, difficulty);

        OrthographicCamera gameCamera = new OrthographicCamera();
        gameViewport = new FillViewport(eelbatCosmir.WIDTH,eelbatCosmir.HEIGHT, gameCamera);
        gameStage = new PlayGameStage(gameViewport, eelbatCosmir, playHUD, touchpadXnya, touchpadYnya, touchpad, level, difficulty);


        Gdx.input.setInputProcessor(stage);


        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FillViewport(eelbatCosmir.WIDTH, eelbatCosmir.HEIGHT, camera);
        stage2 = new PlayScreenStage(viewport, eelbatCosmir);
        //Gdx.input.setInputProcessor(stage2);

        ButtonPause();
        ButtonSkill();
        ButtonWave();
        enableSonarButton();
    }

    boolean sendMainWave() {
        return gameStage.sendMainWave();
    }
    void useAbility() {
        gameStage.useAbility();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (btnPause) {
            //tambahan
            //batch.begin();
            gameStage.act(delta);
        }
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

//        if(!isPaused){
//            Gdx.graphics.requestRendering();
//        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            setPauseapaTidak(!getPauseapaTidak());
        }
    }

//    //@Override
//    public boolean keyDown(int keycode) {
//        if(keycode == Input.Keys.BACK){
//            // Respond to the back button click here
//            return true;
//        }
//        return false;
//    }

//    public void setSpeedPause() {
//        enemySpeed = 0;
//        aksesorisSpeed = 0;
//    }
//
//    public void setSpeedMulai() {
//        enemySpeed = 100;
//        aksesorisSpeed = 100;
//    }
//    public void getSpeedPause(){
//        enemySpeed = 0;
//        aksesorisSpeed = 0;
//    }
//    public void getSpeedMulai(){
//        enemySpeed = 100;
//        aksesorisSpeed = 100;
//    }

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
        playHUD.dispose();
    }

    private class PlayScreenStage extends Stage {
        public PlayScreenStage(Viewport viewport, EelbatCosmir eelbatCosmir) {

        }
    }

    public void ButtonPause(){
        /*
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            float widthScreen = Gdx.graphics.getWidth();
            float heightScreen = Gdx.graphics.getHeight();
        } else {
            float widthScreen = EelbatCosmir.WIDTH;
            float heightScreen = EelbatCosmir.HEIGHT;
        }
        */
        float widthScreen = Gdx.graphics.getWidth();
        float heightScreen = Gdx.graphics.getHeight();

//        //Pause Label
//        pauseLabelStyle = new Label.LabelStyle();
//        pauseLabelStyle.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontSmall);
//        pauseLabel = new Label("", pauseLabelStyle);
//        pauseLabel.setPosition(250, 380);
//        ButtonStage.addActor(pauseLabel);
//        pauseLabel.setVisible(false);

        //Button Alas
        Texture BtnAlas = eelbatCosmir.assets.getTexture(Assets.btnAlas);
        TextureRegionDrawable BtnImageAlas = new TextureRegionDrawable(new TextureRegion(BtnAlas));
        ButtonAlas = new ImageButton(BtnImageAlas);
        int buttonAlasWidth = BtnAlas.getWidth()+50;
        int buttonAlasHeight = BtnAlas.getHeight()+50;
        ButtonAlas.setSize(buttonAlasWidth, buttonAlasHeight);
        ButtonAlas.setPosition((widthScreen/2)-(buttonAlasWidth/2), (heightScreen/2)-(buttonAlasHeight/2));

        ButtonStage.addActor(ButtonAlas);
        ButtonAlas.setVisible(false);

        //Button Resume
        Texture BtnResume = eelbatCosmir.assets.getTexture(Assets.btnResume);
        TextureRegionDrawable BtnImageResume = new TextureRegionDrawable(new TextureRegion(BtnResume));
        ButtonResume = new ImageButton(BtnImageResume);
        int buttonResumeWidth = BtnResume.getWidth()-25;
        int buttonResumeHeight = BtnResume.getHeight()-25;//jarak antar button 40
        int spaceAntarButton = (buttonAlasHeight - (buttonResumeHeight*4))/125;//jangan tanya 125 darimana
        ButtonResume.setSize(buttonResumeWidth,buttonResumeHeight);
        ButtonResume.setPosition((widthScreen/2)-(buttonResumeWidth/2), (heightScreen/2)+(spaceAntarButton*3/2)+buttonResumeHeight);
        ButtonResume.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonResume.getImage().setColor(Color.BLACK);
                btnPause=!btnPause;
                ButtonAlas.setVisible(!btnPause);
                ButtonResume.setVisible(!btnPause);
                ButtonReplay.setVisible(!btnPause);
                ButtonSetting.setVisible(!btnPause);
                ButtonExit.setVisible(!btnPause);
                //pauseLabel.setVisible(true);

                return btnPause;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonResume.getImage().setColor(Color.WHITE);
            }
        });

        ButtonStage.addActor(ButtonResume);
        ButtonResume.setVisible(false);

        //Button Replay
        Texture BtnReplay = eelbatCosmir.assets.getTexture(Assets.btnReplay);
        TextureRegionDrawable BtnImageReplay = new TextureRegionDrawable(new TextureRegion(BtnReplay));
        ButtonReplay = new ImageButton(BtnImageReplay);

        int buttonReplayWidth = BtnReplay.getWidth()-25;
        int buttonReplayHeight = BtnReplay.getHeight()-25;
        ButtonReplay.setSize(buttonReplayWidth,buttonReplayHeight);
        ButtonReplay.setPosition((widthScreen/2)-(buttonResumeWidth/2), (heightScreen/2)+(spaceAntarButton*1/2));
        ButtonReplay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonReplay.getImage().setColor(Color.BLACK);
                eelbatCosmir.setScreen(new PlayScreen(eelbatCosmir, level, difficulty));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonReplay.getImage().setColor(Color.WHITE);
            }
        });

        ButtonStage.addActor(ButtonReplay);
        ButtonReplay.setVisible(false);

        //Button Setting
        Texture BtnSetting = eelbatCosmir.assets.getTexture(Assets.btnSetting);
        TextureRegionDrawable BtnImageSetting = new TextureRegionDrawable(new TextureRegion(BtnSetting));
        ButtonSetting = new ImageButton(BtnImageSetting);
        int buttonSettingWidth = BtnSetting.getWidth()-25;
        int buttonSettingHeight = BtnSetting.getHeight()-25;
        ButtonSetting.setSize(buttonSettingWidth,buttonSettingHeight);
        ButtonSetting.setPosition((widthScreen/2)-(buttonResumeWidth/2), (heightScreen/2)-(spaceAntarButton*1/2)-buttonResumeHeight);
        ButtonSetting.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonSetting.getImage().setColor(Color.BLACK);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonSetting.getImage().setColor(Color.WHITE);
            }
        });

        ButtonStage.addActor(ButtonSetting);
        ButtonSetting.setVisible(false);

        //Button Exit
        Texture BtnExit = eelbatCosmir.assets.getTexture(Assets.btnExit);
        TextureRegionDrawable BtnImageExit = new TextureRegionDrawable(new TextureRegion(BtnExit));
        ButtonExit = new ImageButton(BtnImageExit);
        int buttonExitWidth = BtnExit.getWidth()-25;
        int buttonExitHeight = BtnExit.getHeight()-25;
        ButtonExit.setSize(buttonExitWidth,buttonExitHeight);
        ButtonExit.setPosition((widthScreen/2)-(buttonResumeWidth/2), (heightScreen/2)-(spaceAntarButton*3/2)-(buttonResumeHeight*2));
        ButtonExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonExit.getImage().setColor(Color.BLACK);
                eelbatCosmir.setScreen(new MainMenu(eelbatCosmir));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ButtonExit.getImage().setColor(Color.WHITE);
            }
        });

        ButtonStage.addActor(ButtonExit);
        ButtonExit.setVisible(false);

        //Button Home
//        Texture BtnHome = eelbatCosmir.assets.getTexture(Assets.btnReplayPlayScreen);
//        TextureRegionDrawable BtnImageHome = new TextureRegionDrawable(new TextureRegion(BtnHome));
//        ButtonHome = new ImageButton(BtnImageHome);
//        ButtonHome.setSize(BtnHome.getWidth()-100,BtnHome.getHeight()-100);
//        ButtonHome.setPosition(widthScreen-80, 25);
//        ButtonHome.addListener(new InputListener(){
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                if(!btnPause){
//                    ButtonHome.getImage().setColor(Color.BROWN);
//                }
//                btnPause=!btnPause;
//                ButtonAlas.setVisible(!btnPause);
//                ButtonResume.setVisible(!btnPause);
//                ButtonReplay.setVisible(!btnPause);
//                ButtonSetting.setVisible(!btnPause);
//                ButtonExit.setVisible(!btnPause);
//                //pauseLabel.setVisible(true);
//                Gdx.graphics.setContinuousRendering(true);
//                //Gdx.graphics.requestRendering();
//                //Gdx.app.getApplicationListener().dispose();
//                Assets.playmusic.stop();
//                return btnPause;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                ButtonHome.getImage().setColor(Color.WHITE);
//                super.touchUp(event, x, y, pointer, button);
//            }
//        });


        //ButtonStage.addActor(ButtonHome);
        Gdx.input.setInputProcessor(ButtonStage);
        Gdx.input.setCatchBackKey(true);

    }

    public void ButtonSkill(){

        float widthScreen = Gdx.graphics.getWidth();
        float heightScreen = Gdx.graphics.getHeight();

        skillIdle = new TextureRegionDrawable(new TextureRegion(eelbatCosmir.assets.getTexture(Assets.btnSkill)));
        skillActive = new TextureRegionDrawable(new TextureRegion(eelbatCosmir.assets.getTexture(Assets.btnSkillActive)));

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = skillIdle;
        buttonStyle.down = skillActive;
        skillButtonAndroid = new Button(buttonStyle);
        skillButtonActive = true;

        skillButtonAndroid.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                useAbility();
            }
        });

        if(Gdx.app.getType() == Application.ApplicationType.Android){
            skillButtonAndroid.setPosition(widthScreen*0.75F, heightScreen*0.05F);
            skillButtonAndroid.setSize(skillButtonAndroid.getWidth()*1.5F, skillButtonAndroid.getHeight()*1.5F);
        } else {
            skillButtonAndroid.setPosition(widthScreen*0.75F, heightScreen*0.05F);
        }

        ButtonStage.addActor(skillButtonAndroid);
        Gdx.input.setInputProcessor(ButtonStage);

    }

    public void ButtonWave(){
        float widthScreen = Gdx.graphics.getWidth();
        float heightScreen = Gdx.graphics.getHeight();

        sonarIdle = new TextureRegionDrawable(new TextureRegion(eelbatCosmir.assets.getTexture(Assets.btnSonar)));
        sonarActive = new TextureRegionDrawable(new TextureRegion(eelbatCosmir.assets.getTexture(Assets.btnSonarActive)));
        sonarCooldown = new TextureRegionDrawable(new TextureRegion(eelbatCosmir.assets.getTexture(Assets.btnSonarCooldown)));

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = sonarIdle;
        buttonStyle.down = sonarActive;
        sonarButtonAndroid = new Button(buttonStyle);
        sonarButtonActive = true;

        sonarButtonAndroid.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sendMainWave();
            }
        });

        if(Gdx.app.getType() == Application.ApplicationType.Android){
            sonarButtonAndroid.setPosition(widthScreen*0.87F, heightScreen*0.15F);
            sonarButtonAndroid.setSize(sonarButtonAndroid.getWidth()*1.5F, sonarButtonAndroid.getHeight()*1.5F);
        } else {
            sonarButtonAndroid.setPosition(widthScreen*0.83F, heightScreen*0.15F);
        }

        ButtonStage.addActor(sonarButtonAndroid);
        Gdx.input.setInputProcessor(ButtonStage);

    }

    void enableSonarButton() {
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            buttonStyle.up = sonarIdle;
            buttonStyle.down = sonarActive;
        }
        sonarButtonActive = true;
    }

    public boolean getPauseapaTidak(){
        return btnPause;
    }

    public void setPauseapaTidak(boolean btnPause){
        this.btnPause = btnPause;
        Gdx.app.log("kejarTag", "escape");
        //btnPause = !btnPause;
        ButtonAlas.setVisible(!btnPause);
        ButtonResume.setVisible(!btnPause);
        ButtonReplay.setVisible(!btnPause);
        ButtonSetting.setVisible(!btnPause);
        ButtonExit.setVisible(!btnPause);
        Assets.playmusic.stop();

    }
}
