package com.untamedfox.ggj2017;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import java.util.Random;

public class GGJ2017 extends Game {

    private final String TAG = "GGJ2017";

    public final static int WIDTH = 2560;
    public final static int HEIGHT = 1600;

    final static int MAX_IDLE_ANGLE = 7;
    final static int RUN_SPEED = 800;

    PolygonSpriteBatch batch;

    static Random random;

    Assets assets;
    private boolean startScreenLoaded = false;

    @Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new PolygonSpriteBatch();
        random = new Random();
        setScreen(new SplashScreen());
	}

    @Override
    public void render() {
        if(assets == null) {
            assets = new Assets();
            assets.load();
        } else if(!startScreenLoaded && assets.manager.update()) {
            startScreenLoaded = true;
            setScreen(new MainMenuScreen(this));
        }
        super.render();
    }

    @Override
	public void dispose () {
        batch.dispose();
	}
}
