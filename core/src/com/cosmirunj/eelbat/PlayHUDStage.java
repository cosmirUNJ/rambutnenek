package com.cosmirunj.eelbat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
        bar.setPosition(900, 1250);
        addActor(bar);
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
    }

    enum RUN{STOP, SLOW, FAST}

}
