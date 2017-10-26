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

    //final static int TILE_BAWAH_WIDTH = 1920;
    //final static int TILE_BAWAH_HEIGHT = 1080;

    //W dan H itu jumlah tilesnya(per width dan height)
    private final int W = EelbatCosmir.WIDTH/TILE_WIDTH + 6; //defaultnya 6 18
    private final int H = EelbatCosmir.HEIGHT/TILE_HEIGHT + 6; //defaultnya 6 15

    //private final int WB = EelbatCosmir.WIDTH/TILE_BAWAH_WIDTH+6;
    //private final int HB = EelbatCosmir.HEIGHT/TILE_BAWAH_HEIGHT+6;

    private EelbatCosmir eelbatCosmir;

    private Texture[][] texturesBawah;
    private Texture[][] texturesAtas;
    private int tilesLowerLeftX, tilesLowerLeftY;
    //private int tilesLowerLeftXX,tilesLowerLeftYY ;

    public BackgroundTiles(EelbatCosmir eelbatCosmir){
        this.eelbatCosmir = eelbatCosmir;
        texturesBawah = new Texture[W][H];
        texturesAtas = new Texture[W][H];

        tilesLowerLeftX = -(W/2+1)*TILE_WIDTH;
        tilesLowerLeftY = -(H/2+1)*TILE_HEIGHT;

        //tilesLowerLeftXX = -(WB/2+1)*TILE_BAWAH_WIDTH;
        //tilesLowerLeftYY = -(HB/2+1)*TILE_BAWAH_HEIGHT;

        /*
        for(int i = 0; i < WB; i++) {
            for(int j = 0; j < HB; j++) {
                texturesBawah[i][j] = getRTextureBawah();
                //texturesAtas[i][j] = getRaTextureAtas();
            }
        }
        */

        for(int i = 0; i < W; i++) {
            for(int j = 0; j < H; j++) {
                texturesBawah[i][j] = getRTextureBawah();
                texturesAtas[i][j] = getRaTextureAtas();
            }
        }
    }

    /*
    public void update(float x, float y) {
        int centerXTile = (int)x;
        int centerXXTile = (int)x;

        centerXXTile -= (centerXXTile % TILE_BAWAH_WIDTH);
        centerXTile -= (centerXTile % TILE_WIDTH);

        int centerYTile = (int)y;
        int centerYYTile = (int) y;

        centerYYTile -= (centerYYTile % TILE_BAWAH_HEIGHT);
        centerYTile -= (centerYTile % TILE_HEIGHT);

        int newTilesLowerLeftXX = centerXXTile - (WB/2 + 1)*TILE_BAWAH_WIDTH;
        int newTilesLowerLeftYY = centerYYTile - (HB/2 + 1)*TILE_BAWAH_HEIGHT;

        int newTilesLowerLeftX = centerXTile - (W/2 + 1)*TILE_WIDTH;
        int newTilesLowerLeftY = centerYTile - (H/2 + 1)*TILE_HEIGHT;

        if(Math.abs(newTilesLowerLeftXX - tilesLowerLeftXX) > 0 || Math.abs(newTilesLowerLeftYY - tilesLowerLeftYY) > 0) {
            int k = (newTilesLowerLeftXX - tilesLowerLeftXX) / TILE_BAWAH_WIDTH;
            int l = (newTilesLowerLeftYY - tilesLowerLeftYY) / TILE_BAWAH_HEIGHT;
            if (k > 0) {
                for (int i = k; i < WB; i++) {
                    for (int j = 0; j < HB; j++) {
                        texturesBawah[i - k][j] = texturesBawah[i][j];
                        //texturesAtas[i - k][j] = texturesAtas[i][j];
                    }
                }
                for (int i = WB - k; i < WB; i++) {
                    for (int j = 0; j < HB; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        //texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if (k < 0) {
                for (int i = WB - 1 + k; i >= 0; i--) {
                    for (int j = 0; j < HB; j++) {
                        texturesBawah[i - k][j] = texturesBawah[i][j];
                        //texturesAtas[i - k][j] = texturesAtas[i][j];
                    }
                }
                for (int i = 0; i < -k; i++) {
                    for (int j = 0; j < HB; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        //texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            }
            if (l > 0) {
                for (int i = 0; i < WB; i++) {
                    for (int j = l; j < HB; j++) {
                        texturesBawah[i][j - l] = texturesBawah[i][j];
                        //texturesAtas[i][j - l] = texturesAtas[i][j];
                    }
                }
                for (int i = 0; i < WB; i++) {
                    for (int j = HB - l; j < HB; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        //texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if (l < 0) {
                for (int i = 0; i < WB; i++) {
                    for (int j = HB - 1 + l; j >= 0; j--) {
                        texturesBawah[i][j - l] = texturesBawah[i][j];
                        //texturesAtas[i][j - l] = texturesAtas[i][j];
                    }
                }
                for (int i = 0; i < WB; i++) {
                    for (int j = 0; j < -l; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        //texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            }
        }

        if(Math.abs(newTilesLowerLeftX - tilesLowerLeftX) > 0 || Math.abs(newTilesLowerLeftY - tilesLowerLeftY) > 0) {
            int m = (newTilesLowerLeftX - tilesLowerLeftX)/TILE_WIDTH;
            int n = (newTilesLowerLeftY - tilesLowerLeftY)/TILE_HEIGHT;
            if(m > 0) {
                for(int i = m; i < W; i++) {
                    for(int j = 0; j < H; j++) {
                        //texturesBawah[i - m][j] = texturesBawah[i][j];
                        texturesAtas[i - m][j] = texturesAtas[i][j];
                    }
                }
                for(int i = W - m; i < W; i++) {
                    for(int j = 0; j < H; j++) {
                        //texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if(m < 0) {
                for(int i = W - 1 + m; i >= 0; i--) {
                    for(int j = 0; j < H; j++) {
                        //texturesBawah[i - m][j] = texturesBawah[i][j];
                        texturesAtas[i - m][j] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < -m; i++) {
                    for(int j = 0; j < H; j++) {
                        //texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            }
            if(n > 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = n; j < H; j++) {
                        //texturesBawah[i][j - n] = texturesBawah[i][j];
                        texturesAtas[i][j - n] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = H - n; j < H; j++) {
                        //texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if(n < 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = H - 1 + n; j >= 0; j--) {
                        //texturesBawah[i][j - n] = texturesBawah[i][j];
                        texturesAtas[i][j - n] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = 0; j < -n; j++) {
                        //texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            }
            tilesLowerLeftXX = newTilesLowerLeftXX;
            tilesLowerLeftYY = newTilesLowerLeftYY;

            tilesLowerLeftX = newTilesLowerLeftX;
            tilesLowerLeftY = newTilesLowerLeftY;

        }

    }
    */

    public void update(float x, float y) {
        int centerXTile = (int)x;

        centerXTile -= (centerXTile % TILE_WIDTH);

        int centerYTile = (int)y;

        centerYTile -= (centerYTile % TILE_HEIGHT);


        int newTilesLowerLeftX = centerXTile - (W/2 + 1)*TILE_WIDTH;
        int newTilesLowerLeftY = centerYTile - (H/2 + 1)*TILE_HEIGHT;


        if(Math.abs(newTilesLowerLeftX - tilesLowerLeftX) > 0 || Math.abs(newTilesLowerLeftY - tilesLowerLeftY) > 0) {
            int m = (newTilesLowerLeftX - tilesLowerLeftX)/TILE_WIDTH;
            int n = (newTilesLowerLeftY - tilesLowerLeftY)/TILE_HEIGHT;
            if(m > 0) {
                for(int i = m; i < W; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBawah[i - m][j] = texturesBawah[i][j];
                        texturesAtas[i - m][j] = texturesAtas[i][j];
                    }
                }
                for(int i = W - m; i < W; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if(m < 0) {
                for(int i = W - 1 + m; i >= 0; i--) {
                    for(int j = 0; j < H; j++) {
                        texturesBawah[i - m][j] = texturesBawah[i][j];
                        texturesAtas[i - m][j] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < -m; i++) {
                    for(int j = 0; j < H; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            }
            if(n > 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = n; j < H; j++) {
                        texturesBawah[i][j - n] = texturesBawah[i][j];
                        texturesAtas[i][j - n] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = H - n; j < H; j++) {
                        texturesBawah[i][j] = getRTextureBawah();
                        texturesAtas[i][j] = getRaTextureAtas();
                    }
                }
            } else if(n < 0) {
                for(int i = 0; i < W; i++) {
                    for(int j = H - 1 + n; j >= 0; j--) {
                        texturesBawah[i][j - n] = texturesBawah[i][j];
                        texturesAtas[i][j - n] = texturesAtas[i][j];
                    }
                }
                for(int i = 0; i < W; i++) {
                    for(int j = 0; j < -n; j++) {
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
        //return eelbatCosmir.assets.getTexture(Assets.mapTest);
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


        /*
        for(int i = 0; i < WB; i++) {
            for(int j = 0; j < HB; j++) {
                float x = tilesLowerLeftXX + i*TILE_BAWAH_WIDTH;
                float y = tilesLowerLeftYY + j*TILE_BAWAH_HEIGHT;
                batch.draw(texturesBawah[i][j], x, y);
                //batch.draw(texturesAtas[i][j], x, y);
            }
        }

        for(int i = 0; i < W; i++) {
            for(int j = 0; j < H; j++) {
                float x = tilesLowerLeftX + i*TILE_WIDTH;
                float y = tilesLowerLeftY + j*TILE_HEIGHT;
                //batch.draw(texturesBawah[i][j], x, y);
                batch.draw(texturesAtas[i][j], x, y);
            }
        }
        */
    }
}
