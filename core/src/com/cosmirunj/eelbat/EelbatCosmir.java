package com.cosmirunj.eelbat;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class EelbatCosmir extends Game {
	public  final String TAG = "EelBat";

	public final static int WIDTH = 2560;
	public final static int HEIGHT = 1600;

	PolygonSpriteBatch batch;

	static Random random;

	Assets assets;

	private boolean startScreen = false;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batch = new PolygonSpriteBatch();
		random = new Random();
		setScreen(new SplashScreen());
	}

	@Override
	public void render () {
		if(assets == null){
			assets = new Assets();
			assets.load();
		} else if(!startScreen && assets.manager.update()){
			startScreen = true;
			setScreen(new MainMenu(this));
		}super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
