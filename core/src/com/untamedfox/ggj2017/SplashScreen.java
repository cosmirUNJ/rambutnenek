package com.untamedfox.ggj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {

    private final String TAG = "SplashScreen";

    private SpriteBatch batch;
    private Texture splashTexture;
    //private final int PADDING_HORIZONTAL, PADDING_VERTICAL;
    //private final int WIDTH, HEIGHT;

    public SplashScreen() {
        batch = new SpriteBatch();
        splashTexture = new Texture("splash.png");
        splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        /*
        WIDTH = splashTexture.getWidth();
        HEIGHT = splashTexture.getHeight();
        PADDING_HORIZONTAL = (Gdx.graphics.getWidth() - splashTexture.getWidth())/2;
        PADDING_VERTICAL = (Gdx.graphics.getHeight() - splashTexture.getHeight())/2;
        */
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(28f/255, 37f/255, 43f/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //batch.draw(splashTexture, PADDING_HORIZONTAL, PADDING_VERTICAL, WIDTH, HEIGHT);
        batch.draw(splashTexture, 0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void hide() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void dispose() {
        splashTexture.dispose();
        batch.dispose();
    }
}
