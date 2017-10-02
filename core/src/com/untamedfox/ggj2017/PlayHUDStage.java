package com.untamedfox.ggj2017;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayHUDStage extends Stage {
    private Label.LabelStyle timeLabelStyle;
    private Label timeLabel;
    private Image smallHole;
    private float smallHoleTime;

    private TextureRegionDrawable sonarIdle;
    private TextureRegionDrawable sonarActive;
    private TextureRegionDrawable sonarCooldown;

    private PlayScreen playScreen;
    private int lastSeconds;

    private Button.ButtonStyle buttonStyle;
    private Button sonarButtonAndroid;
    private Image sonarImageDesktop;
    private boolean sonarButtonActive;
    private Image[] targetPlaceholders;
    private Image[] targetAquires;
    private Image bar;
    private int lives;
    private GGJ2017 ggj2017;

    public PlayHUDStage(final PlayScreen playScreen, Viewport viewport, GGJ2017 ggj2017) {
        super(viewport, ggj2017.batch);

        this.ggj2017 = ggj2017;
        this.playScreen = playScreen;

        smallHole = new Image(ggj2017.assets.getTexture(Assets.smallHole));
        smallHole.setOrigin(smallHole.getWidth()/2, smallHole.getHeight()/2);
        addActor(smallHole);
        smallHoleTime = 0;

        Image bigHole = new Image(ggj2017.assets.getTexture(Assets.bigHole));
        addActor(bigHole);

        timeLabelStyle = new Label.LabelStyle();
        timeLabelStyle.font = ggj2017.assets.getBitmapFont(Assets.bitmapFontMedium);
        timeLabel = new Label("", timeLabelStyle);
        timeLabel.setPosition(2250, 1500);
        addActor(timeLabel);

        lastSeconds = 6;
        lives = 2;

        sonarIdle = new TextureRegionDrawable(new TextureRegion(ggj2017.assets.getTexture(Assets.sonarIdle)));
        sonarActive = new TextureRegionDrawable(new TextureRegion(ggj2017.assets.getTexture(Assets.sonarActive)));
        sonarCooldown = new TextureRegionDrawable(new TextureRegion(ggj2017.assets.getTexture(Assets.sonarCooldown)));

        buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = sonarIdle;
        buttonStyle.down = sonarActive;
        sonarButtonAndroid = new Button(buttonStyle);
        sonarButtonActive = true;

        sonarButtonAndroid.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(sonarButtonActive) {
                    if(playScreen.sendMainWave()) {
                        buttonStyle.up = sonarCooldown;
                        buttonStyle.down = sonarCooldown;
                        sonarButtonActive = false;
                    }
                }
            }
        });
        sonarButtonAndroid.setPosition(2070, 100);

        sonarImageDesktop = new Image(ggj2017.assets.getTexture(Assets.sonarCooldown));
        sonarImageDesktop.setVisible(false);
        sonarImageDesktop.setPosition(2070, 100);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            addActor(sonarButtonAndroid);
        } else {
            addActor(sonarImageDesktop);
        }

        targetAquires = new Image[PlayGameStage.TOTAL_TARGETS];
        targetPlaceholders = new Image[PlayGameStage.TOTAL_TARGETS];
        for(int i = 0; i < PlayGameStage.TOTAL_TARGETS; i++) {
            targetAquires[i] = new Image(ggj2017.assets.getTexture(Assets.targetAquire));
            targetPlaceholders[i] = new Image(ggj2017.assets.getTexture(Assets.targetPlaceholder));
            targetAquires[i].setPosition(920 + i*140, 1430);
            targetPlaceholders[i].setPosition(920 + i*140, 1430);
            targetAquires[i].setVisible(false);
            addActor(targetAquires[i]);
            addActor(targetPlaceholders[i]);
        }
        bar = new Image(ggj2017.assets.getTexture(Assets.bar1));
        bar.setPosition(60, 1350);
        addActor(bar);
    }

    void enableSonarButton() {
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            buttonStyle.up = sonarIdle;
            buttonStyle.down = sonarActive;
        } else {
            sonarImageDesktop.setVisible(false);
        }
        sonarButtonActive = true;
    }

    void showSonarImage() {
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            sonarImageDesktop.setVisible(true);
        }
    }

    @Override
    public void act(float delta) {
        smallHoleTime += 6*delta;
        if(smallHoleTime > 2*Math.PI) {
            smallHoleTime -= 2*Math.PI;
        }
        float scale = 1.01f + (float)Math.sin(smallHoleTime)*0.01f;
        smallHole.setScale(scale);
    }

    void update(float time) {
        int timeInt = (int)Math.ceil(time);
        if(timeInt < lastSeconds) {
            if(lastSeconds == 6) {
                timeLabelStyle.fontColor = Color.RED;
            }
            Assets.beep.play();
            lastSeconds--;
        }

        int m = timeInt/60;
        int s = timeInt % 60;
        timeLabel.setText(String.format("%d:%02d", m, s));
    }

    void setTargetsFound(int found) {
        for(int i = 0; i < PlayGameStage.TOTAL_TARGETS; i++) {
            targetAquires[i].setVisible(i < found);
            targetPlaceholders[i].setVisible(i >= found);
        }
    }

    void gotHit() {
        if(lives == 0) {
            ggj2017.setScreen(new FinishedScreen(ggj2017, false));
        }
        lives--;
        bar.remove();
        if(lives == 1) {
            bar = new Image(ggj2017.assets.getTexture(Assets.bar2));
        } else if(lives == 0) {
            bar = new Image(ggj2017.assets.getTexture(Assets.bar3));
        }
        bar.setPosition(60, 1350);
        addActor(bar);
    }

    int getLives() {
        return lives;
    }

    enum RUN{STOP, SLOW, FAST}
}
