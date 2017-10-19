package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Inovatif on 10/18/2017.
 */

public class Assets {

    public static AssetDescriptor<Texture> shadow, eelbatpic;

    AssetManager manager;

    static AssetDescriptor<Texture> menu;
    static AssetDescriptor<BitmapFont> bitmapFontSmall;

    static AssetDescriptor<Texture>[] textureBawah;
    static AssetDescriptor<Texture>[] textureAtas;

    static Music mainmenumusic;
    static Sound playmusic;

    public void load() {
        manager = new AssetManager();

        //gambar
        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        textureParameter.minFilter = Texture.TextureFilter.Linear;

        menu = new AssetDescriptor<Texture>("menu.png", Texture.class, textureParameter);
        shadow = new AssetDescriptor<Texture>("shadow-batcat.png", Texture.class, textureParameter);
        eelbatpic = new AssetDescriptor<Texture>("eelbat/eelbatpic.png", Texture.class, textureParameter);

        //texture atas dan bawah ground nya
        textureAtas = new AssetDescriptor[15];
        for (int i=0;i<15;i++){
            String namatexturenya = String.format("tilesAtas/%d.png",(i+1));
            textureAtas[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }

        textureBawah = new AssetDescriptor[8];
        for (int i=0;i<8;i++){
            String namatexturenya = String.format("tilesBawah/%d.png",(i+1));//karena angkanya dimulai dari 1 bukan 0, jd i+1
            textureBawah[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }





        //font
        BitmapFontLoader.BitmapFontParameter parameter = new BitmapFontLoader.BitmapFontParameter();
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;

        bitmapFontSmall = new AssetDescriptor<BitmapFont>("fonts/bangers-small.fnt", BitmapFont.class, parameter);

        //music
        mainmenumusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainmenu.wav"));
        //playmusic = Gdx.audio.newSound(Gdx.files.internal("music/playing_music.wav"));
        //playmusic = Gdx.audio.newMusic(Gdx.files.internal("music/playing_music.wav"));

        //load font nya
        manager.load(bitmapFontSmall);

        //load gambar
        manager.load(menu);
        manager.load(shadow);
        manager.load(eelbatpic);

        //load texture tile atas dan bawh
        for (int i=0;i<textureBawah.length;i++){
            manager.load(textureBawah[i]);
        }
        for (int i=0;i<textureAtas.length;i++){
            manager.load(textureAtas[i]);
        }

    }
    BitmapFont getBitmapFont(AssetDescriptor<BitmapFont> bitmapFontAssetDescriptor){
        return manager.get(bitmapFontAssetDescriptor);
    }
    Texture getTexture(AssetDescriptor<Texture> textureAssetDescriptor){
        return  manager.get(textureAssetDescriptor);
    }
}
