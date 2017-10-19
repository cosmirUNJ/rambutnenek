package com.cosmirunj.eelbat;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Inovatif on 10/18/2017.
 */

class PlayHUDStage extends Stage {
    private EelbatCosmir eelbatCosmir;
    private PlayScreen playScreen;
    public PlayHUDStage(PlayScreen playScreen, Viewport hudViewport, EelbatCosmir eelbatCosmir) {
        super(hudViewport, eelbatCosmir.batch);
        this.playScreen = playScreen;
        this.eelbatCosmir = eelbatCosmir;

    }

}
