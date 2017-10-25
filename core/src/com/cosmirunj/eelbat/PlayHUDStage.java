package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Inovatif on 10/18/2017.
 */

class PlayHUDStage extends Stage {
    private EelbatCosmir eelbatCosmir;
    private PlayScreen playScreen;
    private Label timeLabel;
    private Label.LabelStyle timeLabelStyle;

    private Label scoreLabel;
    private Label.LabelStyle scoreLabelStyle;

    private int lastSeconds;
    private float Score;

    private Image bar;

    private ImageButton ButtonHome;
    private Stage ButtonStage;
    boolean btnPause;

    public PlayHUDStage(PlayScreen playScreen, Viewport hudViewport, EelbatCosmir eelbatCosmir) {
        super(hudViewport, eelbatCosmir.batch);
        this.playScreen = playScreen;
        this.eelbatCosmir = eelbatCosmir;

        timeLabelStyle = new Label.LabelStyle();
        timeLabelStyle.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontMedium);
        timeLabel = new Label("", timeLabelStyle);
        timeLabel.setPosition(50, 1500);
        addActor(timeLabel);

        lastSeconds = 5;

        scoreLabelStyle = new Label.LabelStyle();
        scoreLabelStyle.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontMedium);
        scoreLabel = new Label("Score: ", scoreLabelStyle);
        scoreLabel.setPosition(1950, 1440);

        addActor(scoreLabel);
        Score = 0;

        bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar1));
        bar.setPosition(900, 1460);
        addActor(bar);

        ButtonStage=new Stage();
        ButtonPause();

    }


    public void ButtonPause(){
        float widthScreen = Gdx.graphics.getWidth();
        float heightScreen = Gdx.graphics.getHeight();

        //Button Home
        Texture BtnHome = eelbatCosmir.assets.getTexture(Assets.btnHome);
        TextureRegionDrawable BtnImageHome = new TextureRegionDrawable(new TextureRegion(BtnHome));
        ButtonHome = new ImageButton(BtnImageHome);
        ButtonHome.setSize(BtnHome.getWidth(),BtnHome.getHeight());
        ButtonHome.setPosition(1600, 50);
        ButtonHome.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ButtonHome.getImage().setColor(Color.BROWN);
                btnPause=true;
//                ButtonEasy.setVisible(true);
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

    void update(float time) {
        int timeInt = (int)Math.ceil(time);
        if(timeInt < lastSeconds) {
            if(lastSeconds == 5) {
                timeLabelStyle.fontColor = Color.RED;
            }
            lastSeconds--;
        }

        int m = timeInt/60;
        int s = timeInt % 60;
        timeLabel.setText(String.format("%d:%02d", m, s));

        scoreLabel.setText("Score : "+String.format("200"));

        ButtonStage.draw();

    }

    enum RUN{STOP, SLOW, FAST}

}
