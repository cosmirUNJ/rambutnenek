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
    private int score;

    private boolean sonarButtonActive;

    private Image sonarImageDesktop;
    private Image bar;
    private int lives;


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
        lives = 4;
        score = 0;

        scoreLabelStyle = new Label.LabelStyle();
        scoreLabelStyle.font = eelbatCosmir.assets.getBitmapFont(Assets.bitmapFontMedium);
        scoreLabel = new Label("Score: ", scoreLabelStyle);
        scoreLabel.setText("Score : "+String.format("%d", score));
        scoreLabel.setPosition(1950, 1440);

        addActor(scoreLabel);

        bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar1));
        bar.setPosition(900, 1460);
        addActor(bar);

        sonarButtonActive = true;

//        sonarButtonAndroid.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if(sonarButtonActive) {
//                    if(playScreen.sendMainWave()) {
//                        buttonStyle.up = sonarCooldown;
//                        buttonStyle.down = sonarCooldown;
//                        sonarButtonActive = false;
//                    }
//                }
//            }
//        });
    }

    void enableSonarButton() {
//        if(Gdx.app.getType() == Application.ApplicationType.Android) {
//            buttonStyle.up = sonarIdle;
//            buttonStyle.down = sonarActive;
//        } else {
//            sonarImageDesktop.setVisible(false);
//        }
        sonarButtonActive = true;
    }

    void showSonarImage() {
        sonarImageDesktop.setVisible(true);
    }



    public void update(float time) {
        int timeInt = (int)Math.ceil(time);
        if(timeInt < lastSeconds) {
            if(lastSeconds == 5) {
                timeLabelStyle.fontColor = Color.WHITE;
            }
            lastSeconds--;
        }

        int m = timeInt/60;
        int s = timeInt % 60;
        timeLabel.setText(String.format("%d:%02d", m, s));


    }

    public void updateScore(int skor) {
        score = score+skor;
        scoreLabel.setText("Score : "+String.format("%d", score));


    }

    public void healthRestored() {
        lives++;
        if(lives > 4) {
            lives = 4;
        }
        bar.remove();
        if(lives == 3) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar2));
        } else if(lives == 2) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar3));
        } else if(lives == 1) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar4));
        } else if(lives == 4) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar1));
        }
        bar.setPosition(900, 1460);
        addActor(bar);


    }

    void gotHit() {
        if(lives == 0) {
            eelbatCosmir.setScreen(new FinishedScreen(eelbatCosmir, false, 1));
        }
        lives--;
        bar.remove();
        if(lives == 3) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar2));
        } else if(lives == 2) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar3));
        } else if(lives == 1) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar4));
        } else if(lives == 0) {
            bar = new Image(eelbatCosmir.assets.getTexture(Assets.bar5));
        }
        bar.setPosition(900, 1460);
        addActor(bar);
    }

    int getLives() {
        return lives;
    }


    enum RUN{STOP, SLOW, FAST}

}
