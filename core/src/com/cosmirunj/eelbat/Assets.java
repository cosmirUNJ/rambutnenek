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



    AssetManager manager;

    static AssetDescriptor<Texture> targetIdle;

    public static AssetDescriptor<Texture> shadow, eelbatpic;

    static AssetDescriptor<Texture> menu;

    static AssetDescriptor<BitmapFont> bitmapFontSmall, bitmapFontMedium, bitmapFontLarge;
    static AssetDescriptor<Texture> bar1, bar2, bar3, bar4, bar5;
    static AssetDescriptor<Texture> btnHome, btnAlas, btnResume, btnReplay, btnSetting, btnExit;

    static AssetDescriptor<Texture> enemyJelly,shadowEnemy;


    static AssetDescriptor<Texture> btnPlay, btnEasy, btnMedium, btnHard, btnSoundActive, btnSoundMute;

    static AssetDescriptor<Texture>[] textureBawah;
    static AssetDescriptor<Texture>[] textureAtas;

    static AssetDescriptor<Texture> mapTest;

    static Music mainmenumusic;
    static Music playmusic;

    public void load() {
        manager = new AssetManager();

        //gambar
        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        textureParameter.minFilter = Texture.TextureFilter.Linear;

        menu = new AssetDescriptor<Texture>("menu.png", Texture.class, textureParameter);
        shadow = new AssetDescriptor<Texture>("shadow-batcat.png", Texture.class, textureParameter);
        eelbatpic = new AssetDescriptor<Texture>("eelbat/eelbatpic.png", Texture.class, textureParameter);
        enemyJelly = new AssetDescriptor<Texture>("enemigo/jellyfish.png", Texture.class, textureParameter);
        shadowEnemy = new AssetDescriptor<Texture>("shadow-skull.png",Texture.class,textureParameter);
        targetIdle = new AssetDescriptor<Texture>("target/target-closed-idle.png", Texture.class, textureParameter);

        //texture atas dan bawah ground nya
        textureAtas = new AssetDescriptor[20];
        for (int i=0;i<20;i++){
            String namatexturenya = String.format("tilesAtas/%d.png",(i+1));
            textureAtas[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }

        textureBawah = new AssetDescriptor[8];
        for (int i=0;i<8;i++){
            String namatexturenya = String.format("tilesBawah/%d.png",(i+1));//karena angkanya dimulai dari 1 bukan 0, jd i+1
            textureBawah[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }

        mapTest = new AssetDescriptor<Texture>("tilesBawah/maptes.png", Texture.class, textureParameter);


        //font
        BitmapFontLoader.BitmapFontParameter parameter = new BitmapFontLoader.BitmapFontParameter();
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;

        bitmapFontSmall = new AssetDescriptor<BitmapFont>("fonts/bangers-small.fnt", BitmapFont.class, parameter);
        bitmapFontMedium = new AssetDescriptor<BitmapFont>("fonts/bangers-medium.fnt", BitmapFont.class, parameter);
        bitmapFontLarge = new AssetDescriptor<BitmapFont>("fonts/bangers-large.fnt", BitmapFont.class, parameter);


        //music
        mainmenumusic = Gdx.audio.newMusic(Gdx.files.internal("music/mainmenu.wav"));
        //playmusic = Gdx.audio.newSound(Gdx.files.internal("music/playing_music.wav"));
        //playmusic = Gdx.audio.newMusic(Gdx.files.internal("music/playingmusic.wav"));

        //bar
        bar1 = new AssetDescriptor<Texture>("bar/bb1.png", Texture.class, textureParameter);
        bar2 = new AssetDescriptor<Texture>("bar/bb2.png", Texture.class, textureParameter);
        bar3 = new AssetDescriptor<Texture>("bar/bb3.png", Texture.class, textureParameter);
        bar4 = new AssetDescriptor<Texture>("bar/bb4.png", Texture.class, textureParameter);
        bar5 = new AssetDescriptor<Texture>("bar/bb5.png", Texture.class, textureParameter);

        //home
        btnHome = new AssetDescriptor<Texture>("buttons/Home.png", Texture.class, textureParameter);
        btnAlas = new AssetDescriptor<Texture>("buttons/Alas.png", Texture.class, textureParameter);
        btnResume = new AssetDescriptor<Texture>("buttons/ResumeButton.png", Texture.class, textureParameter);
        btnReplay = new AssetDescriptor<Texture>("buttons/ReplayButton.png", Texture.class, textureParameter);
        btnSetting = new AssetDescriptor<Texture>("buttons/SettingButton.png", Texture.class, textureParameter);
        btnExit = new AssetDescriptor<Texture>("buttons/ExitButton.png", Texture.class, textureParameter);

        //button Button Stage
        btnPlay = new AssetDescriptor<Texture>("buttons/PlayButton.png", Texture.class, textureParameter);
        btnEasy = new AssetDescriptor<Texture>("buttons/easy.png", Texture.class, textureParameter);
        btnMedium = new AssetDescriptor<Texture>("buttons/Medium.png", Texture.class, textureParameter);
        btnHard = new AssetDescriptor<Texture>("buttons/Hard.png", Texture.class, textureParameter);
        btnSoundActive = new AssetDescriptor<Texture>("buttons/Sound.png", Texture.class, textureParameter);
        btnSoundMute = new AssetDescriptor<Texture>("buttons/Mute.png", Texture.class, textureParameter);


        //load font nya
        manager.load(bitmapFontSmall);
        manager.load(bitmapFontMedium);
        manager.load(bitmapFontLarge);

        //load gambar
        manager.load(menu);
        manager.load(shadow);
        manager.load(eelbatpic);
        manager.load(enemyJelly);
        manager.load(shadowEnemy);
        manager.load(targetIdle);

        //load texture tile atas dan bawh
        for (int i=0;i<textureBawah.length;i++){
            manager.load(textureBawah[i]);
        }
        for (int i=0;i<textureAtas.length;i++){
            manager.load(textureAtas[i]);
        }
        manager.load(mapTest);

        //load bar
        manager.load(bar1);
        manager.load(bar2);
        manager.load(bar3);
        manager.load(bar4);
        manager.load(bar5);

        //load home
        manager.load(btnHome);
        manager.load(btnAlas);
        manager.load(btnResume);
        manager.load(btnReplay);
        manager.load(btnSetting);
        manager.load(btnExit);

        //load button stage
        manager.load(btnPlay);
        manager.load(btnEasy);
        manager.load(btnMedium);
        manager.load(btnHard);
        manager.load(btnSoundActive);
        manager.load(btnSoundMute);

    }
    BitmapFont getBitmapFont(AssetDescriptor<BitmapFont> bitmapFontAssetDescriptor){
        return manager.get(bitmapFontAssetDescriptor);
    }
    Texture getTexture(AssetDescriptor<Texture> textureAssetDescriptor){
        return  manager.get(textureAssetDescriptor);
    }
}
