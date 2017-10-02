package com.untamedfox.ggj2017;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundTilesActor extends Actor {

    private final String TAG = "BackgroundTilesActor";

    final static int TILE_WIDTH = 164;
    final static int TILE_HEIGHT = 164;

    private final int W = GGJ2017.WIDTH/TILE_WIDTH + 6;
    private final int H = GGJ2017.HEIGHT/TILE_HEIGHT + 6;

    //private final int TEXTURES = 10;

    private GGJ2017 ggj2017;

    private Texture[][] texturesBack;
    private Texture[][] texturesFront;
    private int tilesLowerLeftX, tilesLowerLeftY;

    public BackgroundTilesActor(GGJ2017 ggj2017) {
        this.ggj2017 = ggj2017;
        texturesBack = new Texture[W][H];
        texturesFront = new Texture[W][H];
        tilesLowerLeftX = -(W/2 + 1)*TILE_WIDTH;
        tilesLowerLeftY = -(H/2 + 1)*TILE_HEIGHT;
        for(int i = 0; i < W; i++) {
            for(int j = 0; j < H; j++) {
                texturesBack[i][j] = getRandomBackTexture();
                texturesFront[i][j] = getRandomFrontTexture();
            }
        }
    }

    void update(float centerX, float centerY) {
        int centerXTile = (int)centerX;
        centerXTile -= (centerXTile % TILE_WIDTH);
        int centerYTile = (int)centerY;
        centerYTile -= (centerYTile % TILE_HEIGHT);
        int newTilesLowerLeftX = centerXTile - (W/2 + 1)*TILE_WIDTH;
        int newTilesLowerLeftY = centerYTile - (H/2 + 1)*TILE_HEIGHT;
        if(Math.abs(newTilesLowerLeftX - tilesLowerLeftX) > 0 ||
                Math.abs(newTilesLowerLeftY - tilesLowerLeftY) > 0) {
            int k = (newTilesLowerLeftX - tilesLowerLeftX)/TILE_WIDTH;
            int l = (newTilesLowerLeftY - tilesLowerLeftY)/TILE_HEIGHT;
            if(k > 0) {
                for(int i = k; i < W; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBack[i - k][j] = texturesBack[i][j];
                        texturesFront[i - k][j] = texturesFront[i][j];
                    }
                }
                for(int i = W - k; i < W; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBack[i][j] = getRandomBackTexture();
                        texturesFront[i][j] = getRandomFrontTexture();
                    }
                }
            } else if(k < 0) {
                for(int i = W - 1 + k; i >= 0; i--) {
                    for(int j = 0; j < H; j++) {
                        texturesBack[i - k][j] = texturesBack[i][j];
                        texturesFront[i - k][j] = texturesFront[i][j];
                    }
                }
                for(int i = 0; i < -k; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBack[i][j] = getRandomBackTexture();
                        texturesFront[i][j] = getRandomFrontTexture();
                    }
                }
            }
            if(l > 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = l; j < H; j++) {
                        texturesBack[i][j - l] = texturesBack[i][j];
                        texturesFront[i][j - l] = texturesFront[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = H - l; j < H; j++) {
                        texturesBack[i][j] = getRandomBackTexture();
                        texturesFront[i][j] = getRandomFrontTexture();
                    }
                }
            } else if(l < 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = H - 1 + l; j >= 0; j--) {
                        texturesBack[i][j - l] = texturesBack[i][j];
                        texturesFront[i][j - l] = texturesFront[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = 0; j < -l; j++) {
                        texturesBack[i][j] = getRandomBackTexture();
                        texturesFront[i][j] = getRandomFrontTexture();
                    }
                }
            }
            tilesLowerLeftX = newTilesLowerLeftX;
            tilesLowerLeftY = newTilesLowerLeftY;
        }
    }

    private Texture getRandomBackTexture() {
        return ggj2017.assets.getTexture(Assets.back[GGJ2017.random.nextInt(Assets.back.length)]);
    }

    private Texture getRandomFrontTexture() {
        return ggj2017.assets.getTexture(Assets.front[GGJ2017.random.nextInt(Assets.front.length)]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(int i = 0; i < W; i++) {
            for(int j = 0; j < H; j++) {
                float x = tilesLowerLeftX + i*TILE_WIDTH;
                float y = tilesLowerLeftY + j*TILE_HEIGHT;
                batch.draw(texturesBack[i][j], x, y);
                batch.draw(texturesFront[i][j], x, y);
            }
        }
    }
}
