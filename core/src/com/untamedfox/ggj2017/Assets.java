package com.untamedfox.ggj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    private final String TAG = "Assets";

    AssetManager manager;
    static AssetDescriptor<Texture> targetIdle, targetAnim1, targetAnim2, targetVanish;
    static AssetDescriptor<Texture> enemy;
    static AssetDescriptor<Texture> sonarIdle, sonarActive, sonarCooldown;
    static AssetDescriptor<Texture>[] back;
    static AssetDescriptor<Texture>[] front;
    static AssetDescriptor<Texture> bigHole, smallHole, shadow, shadowSkull;
    static AssetDescriptor<Texture> win, gameOver;
    static AssetDescriptor<Texture> bar1, bar2, bar3, bar4, menu, damage1, damage2;
    static AssetDescriptor<Texture> targetAquire, targetPlaceholder;
    static AssetDescriptor<TextureAtlas> atlasFront, atlasBack, atlasSide;
    static AssetDescriptor<BitmapFont> bitmapFontSmall, bitmapFontMedium, bitmapFontLarge;
    static Sound waveOut;
    static Music steps;
    static Sound beep;
    static Music music1;
    static Sound pick;
    static Sound waveIn;
    static Sound hit;
    static Sound death;
    static Sound wincry;

    void load() {
        manager = new AssetManager();

        TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();
        textureParameter.magFilter = Texture.TextureFilter.Linear;
        textureParameter.minFilter = Texture.TextureFilter.Linear;

        back = new AssetDescriptor[8];
        back[0] = new AssetDescriptor<Texture>("tiles/a.png", Texture.class, textureParameter);
        back[1] = new AssetDescriptor<Texture>("tiles/a.png", Texture.class, textureParameter);
        back[2] = new AssetDescriptor<Texture>("tiles/b.png", Texture.class, textureParameter);
        back[3] = new AssetDescriptor<Texture>("tiles/c.png", Texture.class, textureParameter);
        back[4] = new AssetDescriptor<Texture>("tiles/e.png", Texture.class, textureParameter);
        back[5] = new AssetDescriptor<Texture>("tiles/f.png", Texture.class, textureParameter);
        back[6] = new AssetDescriptor<Texture>("tiles/g.png", Texture.class, textureParameter);
        back[7] = new AssetDescriptor<Texture>("tiles/h.png", Texture.class, textureParameter);
        front = new AssetDescriptor[11];
        for(int i = 0; i < 11; i++) {
            String filename = String.format("tiles/%d.png", (i + 1));
            front[i] = new AssetDescriptor<Texture>(filename, Texture.class, textureParameter);
        }
        targetIdle = new AssetDescriptor<Texture>("target/target-closed-idle.png", Texture.class, textureParameter);
        targetAnim1 = new AssetDescriptor<Texture>("target/target-open-anim1.png", Texture.class, textureParameter);
        targetAnim2 = new AssetDescriptor<Texture>("target/target-open-anim2.png", Texture.class, textureParameter);
        targetVanish = new AssetDescriptor<Texture>("target/targer-dissapear.png", Texture.class, textureParameter);
        enemy = new AssetDescriptor<Texture>("enemigo/cabeza.png", Texture.class, textureParameter);
        sonarIdle = new AssetDescriptor<Texture>("buttons/sonar-idle.png", Texture.class, textureParameter);
        sonarActive = new AssetDescriptor<Texture>("buttons/sonar-active.png", Texture.class, textureParameter);
        sonarCooldown = new AssetDescriptor<Texture>("buttons/sonar-cooldown.png", Texture.class, textureParameter);
        bigHole = new AssetDescriptor<Texture>("big-hole.png", Texture.class, textureParameter);
        smallHole = new AssetDescriptor<Texture>("little-hole-animated.png", Texture.class, textureParameter);
        shadow = new AssetDescriptor<Texture>("shadow-batcat.png", Texture.class, textureParameter);
        shadowSkull = new AssetDescriptor<Texture>("shadow-skull.png", Texture.class, textureParameter);
        menu = new AssetDescriptor<Texture>("menu.png", Texture.class, textureParameter);
        damage1 = new AssetDescriptor<Texture>("damage/1.png", Texture.class, textureParameter);
        damage2 = new AssetDescriptor<Texture>("damage/2.png", Texture.class, textureParameter);
        bar1 = new AssetDescriptor<Texture>("bar/heart1.png", Texture.class, textureParameter);
        bar2 = new AssetDescriptor<Texture>("bar/heart2.png", Texture.class, textureParameter);
        bar3 = new AssetDescriptor<Texture>("bar/heart3.png", Texture.class, textureParameter);
        bar4 = new AssetDescriptor<Texture>("bar/heart4.png", Texture.class, textureParameter);
        targetAquire = new AssetDescriptor<Texture>("target-aquire.png", Texture.class, textureParameter);
        targetPlaceholder = new AssetDescriptor<Texture>("target-placeholder.png", Texture.class, textureParameter);
        win = new AssetDescriptor<Texture>("win.png", Texture.class, textureParameter);
        gameOver = new AssetDescriptor<Texture>("gameover.png", Texture.class, textureParameter);
        atlasFront = new AssetDescriptor<TextureAtlas>("spines/bat-front/skeleton-front.atlas", TextureAtlas.class);
        atlasBack = new AssetDescriptor<TextureAtlas>("spines/bat-back/skeleton-back.atlas", TextureAtlas.class);
        atlasSide = new AssetDescriptor<TextureAtlas>("spines/bat-side/skeleton.atlas", TextureAtlas.class);

        waveOut = Gdx.audio.newSound(Gdx.files.internal("sfx/waveOut.wav"));
        steps = Gdx.audio.newMusic(Gdx.files.internal("sfx/steps.wav"));
        beep = Gdx.audio.newSound(Gdx.files.internal("sfx/beep.wav"));
        music1 = Gdx.audio.newMusic(Gdx.files.internal("music/music1.mp3"));
        pick = Gdx.audio.newSound(Gdx.files.internal("sfx/pick.wav"));
        waveIn = Gdx.audio.newSound(Gdx.files.internal("sfx/waveIn.wav"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sfx/hit.wav"));
        death = Gdx.audio.newSound(Gdx.files.internal("sfx/death.wav"));
        wincry = Gdx.audio.newSound(Gdx.files.internal("sfx/wincry.wav"));

        BitmapFontLoader.BitmapFontParameter parameter = new BitmapFontLoader.BitmapFontParameter();
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        bitmapFontSmall = new AssetDescriptor<BitmapFont>("fonts/bangers-small.fnt", BitmapFont.class, parameter);
        bitmapFontMedium = new AssetDescriptor<BitmapFont>("fonts/bangers-medium.fnt", BitmapFont.class, parameter);
        bitmapFontLarge = new AssetDescriptor<BitmapFont>("fonts/bangers-large.fnt", BitmapFont.class, parameter);

        manager.load(bitmapFontSmall);
        manager.load(bitmapFontMedium);
        manager.load(bitmapFontLarge);
        for(int i = 0; i < back.length; i++) {
            manager.load(back[i]);
        }
        for(int i = 0; i < front.length; i++) {
            manager.load(front[i]);
        }
        manager.load(targetIdle);
        manager.load(targetAnim1);
        manager.load(targetAnim2);
        manager.load(targetVanish);
        manager.load(enemy);
        manager.load(sonarIdle);
        manager.load(sonarActive);
        manager.load(sonarCooldown);
        manager.load(bigHole);
        manager.load(smallHole);
        manager.load(shadow);
        manager.load(shadowSkull);
        manager.load(menu);
        manager.load(damage1);
        manager.load(damage2);
        manager.load(bar1);
        manager.load(bar2);
        manager.load(bar3);
        manager.load(bar4);
        manager.load(win);
        manager.load(gameOver);
        manager.load(atlasFront);
        manager.load(atlasBack);
        manager.load(atlasSide);
        manager.load(targetAquire);
        manager.load(targetPlaceholder);
    }

    BitmapFont getBitmapFont(AssetDescriptor<BitmapFont> bitmapFontAssetDescriptor) {
        return manager.get(bitmapFontAssetDescriptor);
    }

    Texture getTexture(AssetDescriptor<Texture> textureAssetDescriptor) {
        return manager.get(textureAssetDescriptor);
    }
}
