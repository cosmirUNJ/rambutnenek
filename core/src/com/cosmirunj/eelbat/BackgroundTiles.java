package com.cosmirunj.eelbat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Inovatif on 10/18/2017.
 */

public class BackgroundTiles extends Actor {

    final static int TILE_WIDTH = 164;
    final static int TILE_HEIGHT = 164;

    private final int W = EelbatCosmir.WIDTH/TILE_WIDTH+6;
    private final int H = EelbatCosmir.HEIGHT/TILE_HEIGHT+6;

    private EelbatCosmir eelbatCosmir;

    private Texture[][] texturesBawah;
    private Texture[][] texturesAtas;
    private int tilesLowerLeftX, tilesLowerLeftY;

    public BackgroundTiles(EelbatCosmir eelbatCosmir){
        this.eelbatCosmir = eelbatCosmir;
        texturesBawah = new Texture[W][H];
        texturesAtas = new Texture[W][H];
        tilesLowerLeftX = -(W/2+1)*TILE_WIDTH;
        tilesLowerLeftY = -(H/2+1)*TILE_HEIGHT;
        for(int i = 0; i < W; i++) {
            for(int j = 0; j < H; j++) {
                texturesBawah[i][j] = getRTextureBawah();
                texturesAtas[i][j] = getRaTextureAtas();
            }
        }
    }

    public void update(float x, float y) {
        int centerXTile = (int)x;
        centerXTile -= (centerXTile % TILE_WIDTH);
        int centerYTile = (int)y;
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
                        texturesBawah[i - k][j] = texturesBawah[i][j];
                        texturesAtas[i - k][j] = texturesAtas[i][j];
                    }
                }
                for(int i = W - k; i < W; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if(k < 0) {
                for(int i = W - 1 + k; i >= 0; i--) {
                    for(int j = 0; j < H; j++) {
                        texturesBawah[i - k][j] = texturesBawah[i][j];
                        texturesAtas[i - k][j] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < -k; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            }
            if(l > 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = l; j < H; j++) {
                        texturesBawah[i][j - l] = texturesBawah[i][j];
                        texturesAtas[i][j - l] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = H - l; j < H; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if(l < 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = H - 1 + l; j >= 0; j--) {
                        texturesBawah[i][j - l] = texturesBawah[i][j];
                        texturesAtas[i][j - l] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = 0; j < -l; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            }
            tilesLowerLeftX = newTilesLowerLeftX;
            tilesLowerLeftY = newTilesLowerLeftY;
        }

    }

    private Texture getRaTextureAtas() {
        return eelbatCosmir.assets.getTexture(Assets.textureAtas[EelbatCosmir.random.nextInt(Assets.textureAtas.length)]);
    }

    private Texture getRTextureBawah() {
        return eelbatCosmir.assets.getTexture(Assets.textureBawah[EelbatCosmir.random.nextInt(Assets.textureBawah.length)]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        for(int i = 0; i < W; i++) {
            for(int j = 0; j < H; j++) {
                float x = tilesLowerLeftX + i*TILE_WIDTH;
                float y = tilesLowerLeftY + j*TILE_HEIGHT;
                batch.draw(texturesBawah[i][j], x, y);
                batch.draw(texturesAtas[i][j], x, y);
            }
        }
    }
}
