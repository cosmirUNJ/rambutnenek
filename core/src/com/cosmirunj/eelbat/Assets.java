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

    static AssetDescriptor<Texture> mf1;
    static AssetDescriptor<Texture> mf2;
    static AssetDescriptor<Texture> mf3;

    static AssetDescriptor<Texture> fruit1;
    static AssetDescriptor<Texture> fruit2;
    static AssetDescriptor<Texture> fruit3;
    static AssetDescriptor<Texture> fruit4;
    static AssetDescriptor<Texture> fruit5;
    static AssetDescriptor<Texture> fruit6;

    public static AssetDescriptor<Texture> shadow, eelbatpic;

    static AssetDescriptor<Texture> menu;
    static AssetDescriptor<Texture> levelComplete, gameOver;

    static AssetDescriptor<BitmapFont> bitmapFontSmall, bitmapFontMedium, bitmapFontLarge;
    static AssetDescriptor<Texture> bar1, bar2, bar3, bar4, bar5, ability, damage;
    static AssetDescriptor<Texture> btnHome, btnAlas, btnResume, btnReplay, btnSetting, btnExit;

    static AssetDescriptor<Texture> enemyJelly,enemyFish,shadowEnemy,manyherringfish, enemySeaHorse;


    static AssetDescriptor<Texture> btnPlay, btnEasy, btnMedium, btnHard, btnSoundActive, btnSoundMute, btnReplayPlayScreen;
    static AssetDescriptor<Texture> btnSkill, btnSonar, btnSkillActive, btnSonarActive, btnSonarCooldown;
    static AssetDescriptor<Texture> smallHole, bigHole;


    static AssetDescriptor<Texture>[] textureBawah;
    static AssetDescriptor<Texture>[] textureAtas;
    static AssetDescriptor<Texture>[] gelembung;
    static AssetDescriptor<Texture>[] rumput;
    static AssetDescriptor<Texture>[] tilesGelombang;

    static AssetDescriptor<Texture> mapTest;

    static Music mainmenumusic;
    static Music playmusic;
    static Sound waveOut;
    static Sound pick;
    static Sound pick2;
    static Sound hit;
    static Sound fail;
    static Sound success;

    int jumlahTextureBawah = 10;
    int jumlahTextureAtas = 20;
    int jumlahGelembung = 4;
    int jumlahRumput = 3;
    int jumlahTilesGelombang = 3;

    public void load() {
        manager = new AssetManager();

        //gambar
        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        textureParameter.minFilter = Texture.TextureFilter.Linear;

        menu = new AssetDescriptor<Texture>("menu.png", Texture.class, textureParameter);
        shadow = new AssetDescriptor<Texture>("shadow-eelbat.png", Texture.class, textureParameter);
        eelbatpic = new AssetDescriptor<Texture>("eelbat/eelbatpic.png", Texture.class, textureParameter);
        enemyJelly = new AssetDescriptor<Texture>("enemigo/jellyfish.png", Texture.class, textureParameter);
        enemyFish = new AssetDescriptor<Texture>("enemigo/anglerfish.png", Texture.class, textureParameter);
        shadowEnemy = new AssetDescriptor<Texture>("shadow-enemy.png",Texture.class,textureParameter);
        mf1 = new AssetDescriptor<Texture>("target/mfapple.png", Texture.class, textureParameter);
        mf2 = new AssetDescriptor<Texture>("target/mforange.png", Texture.class, textureParameter);
        mf3 = new AssetDescriptor<Texture>("target/mfstrawberry.png", Texture.class, textureParameter);
        fruit1 = new AssetDescriptor<Texture>("target/apple.png", Texture.class, textureParameter);
        fruit2 = new AssetDescriptor<Texture>("target/cherry.png", Texture.class, textureParameter);
        fruit3 = new AssetDescriptor<Texture>("target/mango.png", Texture.class, textureParameter);
        fruit4 = new AssetDescriptor<Texture>("target/orange.png", Texture.class, textureParameter);
        fruit5 = new AssetDescriptor<Texture>("target/strawberry.png", Texture.class, textureParameter);
        fruit6 = new AssetDescriptor<Texture>("target/watermelon.png", Texture.class, textureParameter);
        manyherringfish = new AssetDescriptor<Texture>("enemigo/manyherringfish.png", Texture.class, textureParameter);
        enemySeaHorse = new AssetDescriptor<Texture>("enemigo/seahorse.png", Texture.class, textureParameter);
        smallHole = new AssetDescriptor<Texture>("small-hole.png", Texture.class, textureParameter);
        bigHole = new AssetDescriptor<Texture>("big-hole.png", Texture.class, textureParameter);

        //texture atas dan bawah ground nya
        textureAtas = new AssetDescriptor[jumlahTextureAtas];
        for (int i=0;i<20;i++){
            String namatexturenya = String.format("tilesAtas/%d.png",(i+1));//karena angkanya dimulai dari 1 bukan 0, jd i+1
            textureAtas[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }

        textureBawah = new AssetDescriptor[jumlahTextureBawah];
        for (int i=0;i<jumlahTextureBawah;i++){
            String namatexturenya = String.format("tilesBawah/tile0%d.png",(i));
            textureBawah[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }

        gelembung = new AssetDescriptor[jumlahGelembung];
        for (int i=0;i<4;i++){
            String namatexturenya = String.format("tilesAtas/gelembung/bubbleeffect-%d.png",(i));
            gelembung[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }

        rumput = new AssetDescriptor[jumlahRumput];
        for (int i=0;i<3;i++){
            String namatexturenya = String.format("tilesAtas/rumput/watergrass-%d.png",(i));
            rumput[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
        }

        tilesGelombang = new AssetDescriptor[jumlahTilesGelombang];
        for (int i=0;i<3;i++){
            String namatexturenya = String.format("tilesGelombang/watereffect%d.png",(i+1));
            tilesGelombang[i] = new AssetDescriptor<Texture>(namatexturenya, Texture.class,textureParameter);
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
        playmusic = Gdx.audio.newMusic(Gdx.files.internal("music/playingfix.wav"));
        //playmusic = Gdx.audio.newMusic(Gdx.files.internal("music/playingmusic.wav"));

        //bar
        bar1 = new AssetDescriptor<Texture>("bar/bb1.png", Texture.class, textureParameter);
        bar2 = new AssetDescriptor<Texture>("bar/bb2.png", Texture.class, textureParameter);
        bar3 = new AssetDescriptor<Texture>("bar/bb3.png", Texture.class, textureParameter);
        bar4 = new AssetDescriptor<Texture>("bar/bb4.png", Texture.class, textureParameter);
        bar5 = new AssetDescriptor<Texture>("bar/bb5.png", Texture.class, textureParameter);

        ability = new AssetDescriptor<Texture>("ability.png", Texture.class, textureParameter);
        damage = new AssetDescriptor<Texture>("damage.png", Texture.class, textureParameter);


        levelComplete = new AssetDescriptor<Texture>("level-complete.png", Texture.class, textureParameter);
        gameOver = new AssetDescriptor<Texture>("gameover.png", Texture.class, textureParameter);

        waveOut = Gdx.audio.newSound(Gdx.files.internal("sfx/ultrasonic.wav"));
        pick = Gdx.audio.newSound(Gdx.files.internal("sfx/eatbuahajaib.wav"));
        pick2 = Gdx.audio.newSound(Gdx.files.internal("sfx/eatbuahlaut.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sfx/die1.wav"));
        fail = Gdx.audio.newSound(Gdx.files.internal("sfx/death.wav"));
        success = Gdx.audio.newSound(Gdx.files.internal("sfx/wincry.wav"));

        //home
        btnHome = new AssetDescriptor<Texture>("buttons/home-btn.png", Texture.class, textureParameter);
        btnAlas = new AssetDescriptor<Texture>("buttons/alas-btn.png", Texture.class, textureParameter);
        btnResume = new AssetDescriptor<Texture>("buttons/resume-button.png", Texture.class, textureParameter);
        btnReplay = new AssetDescriptor<Texture>("buttons/replay-button.png", Texture.class, textureParameter);
        btnSetting = new AssetDescriptor<Texture>("buttons/setting-button.png", Texture.class, textureParameter);
        btnExit = new AssetDescriptor<Texture>("buttons/exit-button.png", Texture.class, textureParameter);
        btnReplayPlayScreen = new AssetDescriptor<Texture>("buttons/replayicon.png", Texture.class, textureParameter);

        //button Button Stage
        btnPlay = new AssetDescriptor<Texture>("buttons/play-button.png", Texture.class, textureParameter);
        btnEasy = new AssetDescriptor<Texture>("buttons/easy-btn.png", Texture.class, textureParameter);
        btnMedium = new AssetDescriptor<Texture>("buttons/medium-btn.png", Texture.class, textureParameter);
        btnHard = new AssetDescriptor<Texture>("buttons/hard-btn.png", Texture.class, textureParameter);
        btnSoundActive = new AssetDescriptor<Texture>("buttons/musicon.png", Texture.class, textureParameter);
        btnSoundMute = new AssetDescriptor<Texture>("buttons/mute-btn.png", Texture.class, textureParameter);

        //button Skill & Wave
        btnSkill = new AssetDescriptor<Texture>("buttons/skill-button.png", Texture.class, textureParameter);
        btnSkillActive = new AssetDescriptor<Texture>("buttons/skill-active.png", Texture.class, textureParameter);
        btnSonar = new AssetDescriptor<Texture>("buttons/sonar-button.png", Texture.class, textureParameter);
        btnSonarActive = new AssetDescriptor<Texture>("buttons/sonar-active.png", Texture.class, textureParameter);
        btnSonarCooldown = new AssetDescriptor<Texture>("buttons/sonar-cooldown.png", Texture.class, textureParameter);

        //load font nya
        manager.load(bitmapFontSmall);
        manager.load(bitmapFontMedium);
        manager.load(bitmapFontLarge);

        //load gambar
        manager.load(menu);
        manager.load(shadow);
        manager.load(eelbatpic);
        manager.load(enemyJelly);
        manager.load(enemyFish);
        manager.load(shadowEnemy);
        manager.load(mf1);
        manager.load(mf2);
        manager.load(mf3);
        manager.load(fruit1);
        manager.load(fruit2);
        manager.load(fruit3);
        manager.load(fruit4);
        manager.load(fruit5);
        manager.load(fruit6);
        manager.load(smallHole);
        manager.load(bigHole);
        manager.load(manyherringfish);
        manager.load(enemySeaHorse);

        //load texture tile atas dan bawh
        for (int i=0;i<jumlahTextureBawah;i++){
            manager.load(textureBawah[i]);
        }
        for (int i=0;i<jumlahTextureAtas;i++){
            manager.load(textureAtas[i]);
        }

        //rumput dan gelembung
        for (int i=0;i<jumlahGelembung;i++){
            manager.load(gelembung[i]);
        }
        for (int i=0;i<jumlahRumput;i++){
            manager.load(rumput[i]);
        }

        for (int i=0;i<jumlahTilesGelombang;i++){
            manager.load(tilesGelombang[i]);
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

        manager.load(ability);
        manager.load(damage);

        manager.load(btnReplayPlayScreen);


        //load button skill & wave
        manager.load(btnSkill);
        manager.load(btnSonar);
        manager.load(btnSkillActive);
        manager.load(btnSonarActive);
        manager.load(btnSonarCooldown);

        manager.load(levelComplete);
        manager.load(gameOver);


    }
    BitmapFont getBitmapFont(AssetDescriptor<BitmapFont> bitmapFontAssetDescriptor){
        return manager.get(bitmapFontAssetDescriptor);
    }
    Texture getTexture(AssetDescriptor<Texture> textureAssetDescriptor){
        return  manager.get(textureAssetDescriptor);
    }
}
