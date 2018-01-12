package com.cosmirunj.eelbatcosmir;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Inovatif on 10/18/2017.
 */

class PlayHUDStage extends Stage {
    private com.cosmirunj.eelbatcosmir.EelbatCosmir eelbatCosmir;
    private PlayScreen playScreen;
    private int level;
    private int difficulty;
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

    private float widthScreen = com.cosmirunj.eelbatcosmir.EelbatCosmir.WIDTH;
    private float heightScreen = com.cosmirunj.eelbatcosmir.EelbatCosmir.HEIGHT;
    private Image smallHole, bigHole;

    public PlayHUDStage(PlayScreen playScreen, Viewport hudViewport, com.cosmirunj.eelbatcosmir.EelbatCosmir eelbatCosmir, int level, int difficulty) {
        super(hudViewport, eelbatCosmir.batch);
        this.playScreen = playScreen;
        this.eelbatCosmir = eelbatCosmir;
        this.level = level;
        this.difficulty = difficulty;

        if (level == 1){
            smallHole = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.smallHole));
            smallHole.setOrigin(smallHole.getWidth()/2, smallHole.getHeight()/2);
            addActor(smallHole);
            //smallHoleTime = 0;

            bigHole = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bigHole));
            addActor(bigHole);
        }


        timeLabelStyle = new Label.LabelStyle();
        timeLabelStyle.font = eelbatCosmir.assets.getBitmapFont(com.cosmirunj.eelbatcosmir.Assets.bitmapFontMedium);
        timeLabel = new Label("", timeLabelStyle);
        timeLabel.setPosition(widthScreen*0.05F, heightScreen*0.9F);
        addActor(timeLabel);

        lastSeconds = 5;
        lives = 4;
        score = 0;

        scoreLabelStyle = new Label.LabelStyle();
        scoreLabelStyle.font = eelbatCosmir.assets.getBitmapFont(com.cosmirunj.eelbatcosmir.Assets.bitmapFontMedium);
        scoreLabel = new Label("Score: ", scoreLabelStyle);
        scoreLabel.setText("Score : "+String.format("%d", score));
        scoreLabel.setPosition(widthScreen*0.75F, heightScreen*0.86F);

        addActor(scoreLabel);

        bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar1));
        bar.setSize(bar.getWidth(),bar.getHeight());
        bar.setPosition((widthScreen/2)-(bar.getWidth()/2), heightScreen-(2*bar.getHeight()));
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

//    @Override
//    public void act(float delta){
//        smallHoleTime += 6*delta;
//        if (smallHoleTime > 2*Math.PI)
//            smallHoleTime -= 2*Math.PI;
//        float scale = 1.01f + (float) Math.sin(smallHoleTime)*0.01f;
//        smallHole.setScale(scale);
//    }

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
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar2));
        } else if(lives == 2) {
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar3));
        } else if(lives == 1) {
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar4));
        } else if(lives == 4) {
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar1));
        }
        bar.setPosition(900, 1460);
        addActor(bar);


    }

    void gotHit() {
        if(lives == 0) {
            eelbatCosmir.setScreen(new com.cosmirunj.eelbatcosmir.FinishedScreen(eelbatCosmir, false, level, difficulty));
        }
        lives--;
        bar.remove();
        if(lives == 3) {
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar2));
        } else if(lives == 2) {
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar3));
        } else if(lives == 1) {
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar4));
        } else if(lives == 0) {
            bar = new Image(eelbatCosmir.assets.getTexture(com.cosmirunj.eelbatcosmir.Assets.bar5));
        }
        bar.setPosition(900, 1460);
        addActor(bar);
    }

    int getLives() {
        return lives;
    }

    enum RUN{STOP, SLOW, FAST}

}
