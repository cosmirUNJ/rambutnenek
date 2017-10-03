package com.untamedfox.ggj2017;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayGameStage extends Stage {

    private final String TAG = "PlayGameStage";

    private final int MAX_RADIUS_X = 5*GGJ2017.WIDTH;
    private final int MAX_RADIUS_Y = 5*GGJ2017.HEIGHT;

    private final int ATTACK_RANGE = 200;
    private final int COLLECT_RANGE = 150;

    private final int TOTAL_TIME = 3*60;
    static final int TOTAL_TARGETS = 5;

    private GGJ2017 ggj2017;

    private float x = 0;
    private float y = 0;

    private float time;

    private PlayHUDStage playHUDStage;

    ShapeRenderer shapeRenderer;

    private Vector3 cameraPosition;
    private BackgroundTilesActor backgroundTilesActor;
    private MainCharacter mainCharacter;

    private ArrayList<TargetActor> targets;
    private Map<Integer, Set<EnemyCowActor>> fixedEnemies;
    private Set<EnemyCowActor> freeEnemies;
    private HashSet<Integer> targetsFound;
    private WaveActor mainWave;

    private float touchpadXnya, touchpadYnya;
    private ArrayList<TargetActor> collectedTargets;

    private Image damage1, damage2;
    private float damageTime;
    private boolean doDamage;

    private Touchpad touchpad;

    //public PlayGameStage(Viewport viewport, GGJ2017 ggj2017, PlayHUDStage playHUDStage, float initialPitch, float initialRoll) {
    public PlayGameStage(Viewport viewport, GGJ2017 ggj2017, PlayHUDStage playHUDStage, float touchpadXnya, float touchpadYnya, Touchpad touchpad) {
        super(viewport, ggj2017.batch);
        this.playHUDStage = playHUDStage;
        this.ggj2017 = ggj2017;

        time = TOTAL_TIME;

        shapeRenderer = new ShapeRenderer();

        //tambahan
        this.touchpadXnya = touchpadXnya;
        this.touchpadYnya = touchpadYnya;
        this.touchpad = touchpad;

        cameraPosition = getViewport().getCamera().position;

        backgroundTilesActor = new BackgroundTilesActor(ggj2017);
        addActor(backgroundTilesActor);

        mainCharacter = new MainCharacter(ggj2017);
        mainCharacter.updatePosition(x, y, DIRECTION.NONE);
        addActor(mainCharacter);

        collectedTargets = new ArrayList<TargetActor>();

        targets = new ArrayList<TargetActor>();
        targetsFound = new HashSet<Integer>();
        fixedEnemies = new HashMap<Integer, Set<EnemyCowActor>>();
        freeEnemies = new HashSet<EnemyCowActor>();
        for(int i = 0; i < TOTAL_TARGETS; i++) {
            float x = GGJ2017.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = GGJ2017.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
            TargetActor target = new TargetActor(ggj2017.assets, x, y, i);
            targets.add(target);
            addActor(target);
            Set<EnemyCowActor> enemyGroup = new HashSet<EnemyCowActor>();
            int k = 7 + GGJ2017.random.nextInt(8);
            for(int j = 0; j < k; j++) {
                EnemyCowActor cowEnemy = new EnemyCowActor(ggj2017.assets, x, y, true);
                enemyGroup.add(cowEnemy);
                addActor(cowEnemy);
            }
            fixedEnemies.put(i, enemyGroup);
        }

        for(int i = 0; i < 50; i++) {
            float x = GGJ2017.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = GGJ2017.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
            EnemyCowActor enemyCowActor = new EnemyCowActor(ggj2017.assets, x, y, false);
            freeEnemies.add(enemyCowActor);
            addActor(enemyCowActor);
        }
        damage1 = new Image(ggj2017.assets.getTexture(Assets.damage1));
        damage2 = new Image(ggj2017.assets.getTexture(Assets.damage2));
        damage1.setVisible(false);
        damage2.setVisible(false);
        addActor(damage1);
        addActor(damage2);
        doDamage = false;
    }

    @Override
    public void act(float delta) {
        updateLocation(delta);
        time -= delta;
        if(time < 0) {
            ggj2017.setScreen(new FinishedScreen(ggj2017, false));
        }
        if(doDamage) {
            damageTime += delta;
            if(damageTime > 0.4f) {
                doDamage = false;
            }

            if(damageTime < 0.2f) {
                damage1.setVisible(true);
                damage1.setPosition(x - damage1.getWidth()/2, y - 180);
                damage2.setVisible(false);
            } else if(damageTime < 0.4f) {
                damage2.setVisible(true);
                damage2.setPosition(x - damage2.getWidth()/2, y - 180);
                damage1.setVisible(false);
            } else {
                doDamage = false;
                damage1.setVisible(false);
                damage2.setVisible(false);
            }

        }
        super.act(delta);
    }

    private void updateLocation(float delta) {
        float pitch = 0;
        float roll = 0;
        PlayHUDStage.RUN runX = PlayHUDStage.RUN.STOP;
        PlayHUDStage.RUN runY = PlayHUDStage.RUN.STOP;
        DIRECTION direction;

        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {

            /*
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                direction = DIRECTION.RIGHT_UP;
            } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                direction = DIRECTION.RIGHT_DOWN;
            } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
                direction = DIRECTION.LEFT_UP;
            } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                direction = DIRECTION.LEFT_DOWN;
            } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                direction = DIRECTION.RIGHT;
            } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                direction = DIRECTION.LEFT;
            } else if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                direction = DIRECTION.UP;
            } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                direction = DIRECTION.DOWN;
            } else {
                direction = DIRECTION.NONE;
            }
            */

            float touchpadXnya = touchpad.getKnobPercentX();
            float touchpadYnya = touchpad.getKnobPercentY();

            int align = 0;
            if(touchpadXnya < -.25f){
                align = Align.left;
            }
            else if(touchpadXnya > .25f){
                align = Align.right;
            }
            if(touchpadYnya < -.25f){
                align += Align.bottom;
            } else if (touchpadYnya > .25f){
                align += Align.top;
            }

            switch (align){
                case Align.left:
                    direction = DIRECTION.LEFT;
                    break;
                case Align.right:
                    direction = DIRECTION.RIGHT;
                    break;
                case Align.top:
                    direction = DIRECTION.UP;
                    break;
                case Align.bottom:
                    direction = DIRECTION.DOWN;
                    break;
                case Align.topRight:
                    direction = DIRECTION.RIGHT_UP;
                    break;
                case Align.topLeft:
                    direction = DIRECTION.LEFT_UP;
                    break;
                case Align.bottomRight:
                    direction = DIRECTION.RIGHT_DOWN;
                    break;
                case Align.bottomLeft:
                    direction = DIRECTION.LEFT_DOWN;
                    break;
                default:
                    direction = DIRECTION.NONE;
                    break;
            }
            //selesai tambah

            if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                sendMainWave();
            }

        } else { //kalo android
            /*
            harus baca lagi di https://stackoverflow.com/questions/42057796/move-the-player-only-in-45-steps-with-touchpad-in-libgdx
            dan di http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=25734
             */

            float touchpadXnya = touchpad.getKnobPercentX();
            float touchpadYnya = touchpad.getKnobPercentY();

            int align = 0;
            if(touchpadXnya < -.25f){
                align = Align.left;
            }
            else if(touchpadXnya > .25f){
                align = Align.right;
            }
            if(touchpadYnya < -.25f){
                align += Align.bottom;
            } else if (touchpadYnya > .25f){
                align += Align.top;
            }

            switch (align){
                case Align.left:
                    direction = DIRECTION.LEFT;
                    break;
                case Align.right:
                    direction = DIRECTION.RIGHT;
                    break;
                case Align.top:
                    direction = DIRECTION.UP;
                    break;
                case Align.bottom:
                    direction = DIRECTION.DOWN;
                    break;
                case Align.topRight:
                    direction = DIRECTION.RIGHT_UP;
                    break;
                case Align.topLeft:
                    direction = DIRECTION.LEFT_UP;
                    break;
                case Align.bottomRight:
                    direction = DIRECTION.RIGHT_DOWN;
                    break;
                case Align.bottomLeft:
                    direction = DIRECTION.LEFT_DOWN;
                    break;
                default:
                    direction = DIRECTION.NONE;
                    break;
            }
            /*
            pitch = Gdx.input.getPitch() - initialPitch;
            roll = Gdx.input.getRoll() - initialRoll;

            if(Math.abs(pitch) >= GGJ2017.MAX_IDLE_ANGLE && Math.abs(roll) >= GGJ2017.MAX_IDLE_ANGLE) {
                if(pitch < 0 && roll > 0) {
                    direction = DIRECTION.RIGHT_UP;
                } else if(pitch < 0 && roll < 0) {
                    direction = DIRECTION.RIGHT_DOWN;
                } else if(pitch > 0 && roll > 0) {
                    direction = DIRECTION.LEFT_UP;
                } else {
                    direction = DIRECTION.LEFT_DOWN;
                }
            } else if(Math.abs(pitch) >= GGJ2017.MAX_IDLE_ANGLE) {
                if(pitch < 0) {
                    direction = DIRECTION.RIGHT;
                } else {
                    direction = DIRECTION.LEFT;
                }
            } else if(Math.abs(roll) >= GGJ2017.MAX_IDLE_ANGLE) {
                if(roll > 0) {
                    direction = DIRECTION.UP;
                } else {
                    direction = DIRECTION.DOWN;
                }
            } else {
                direction = DIRECTION.NONE;
            }
            */
        }

        float sq = (float)Math.sqrt(2);
        if(direction == DIRECTION.RIGHT_UP || direction == DIRECTION.RIGHT_DOWN) {
            x += delta*GGJ2017.RUN_SPEED/sq;
        } else if(direction == DIRECTION.RIGHT) {
            x += delta*GGJ2017.RUN_SPEED;
        } else if(direction == DIRECTION.LEFT_UP || direction == DIRECTION.LEFT_DOWN) {
            x -= delta*GGJ2017.RUN_SPEED/sq;
        } else if(direction == DIRECTION.LEFT) {
            x -= delta*GGJ2017.RUN_SPEED;
        }

        if(direction == DIRECTION.RIGHT_UP || direction == DIRECTION.LEFT_UP) {
            y += delta*GGJ2017.RUN_SPEED/sq;
        } else if(direction == DIRECTION.UP) {
            y += delta*GGJ2017.RUN_SPEED;
        } else if(direction == DIRECTION.RIGHT_DOWN || direction == DIRECTION.LEFT_DOWN) {
            y -= delta*GGJ2017.RUN_SPEED/sq;
        } else if(direction == DIRECTION.DOWN) {
            y -= delta*GGJ2017.RUN_SPEED;
        }
        cameraPosition.x = x;
        cameraPosition.y = y;

        mainCharacter.updatePosition(x, y, direction);

        backgroundTilesActor.update(x, y);

        playHUDStage.update(time);

        checkCollisions();
    }

    boolean sendMainWave() {
        boolean canSend = mainWave == null;
        if(canSend) {
            Assets.waveOut.play(1.0f);
            mainWave = new WaveActor(this, cameraPosition.x, cameraPosition.y, true);
            addActor(mainWave);
            playHUDStage.showSonarImage();
        }
        return canSend;
    }

    private void checkCollisions() {
        for(TargetActor target : targets) {
            float x = target.getPositionX();
            float y = target.getPositionY();
            if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2)) {
                collectedTargets.add(target);
            }
        }
        if(collectedTargets.size() > 0) {
            for(TargetActor target : collectedTargets) {
                Assets.pick.play();
                target.deactivate();
                targets.remove(target);
                for(EnemyCowActor enemy : fixedEnemies.get(target.getId())) {
                    enemy.unfix();
                }
            }
            playHUDStage.setTargetsFound(TOTAL_TARGETS - targets.size());
            collectedTargets.clear();
            if(targets.size() == 0) {
                ggj2017.setScreen(new FinishedScreen(ggj2017, true));
            }
        }

        int k = -1;
        EnemyCowActor enemyHit = null;
        for(int i : fixedEnemies.keySet()) {
            if(enemyHit != null) {
                break;
            }
            for(EnemyCowActor enemy : fixedEnemies.get(i)) {
                float x = enemy.getPositionX();
                float y = enemy.getPositionY();
                if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2)) {
                    enemyHit = enemy;
                    k = i;
                    break;
                }
            }
        }
        if(enemyHit != null) {
            if(playHUDStage.getLives() > 0) {
                Assets.hit.play();
            }
            fixedEnemies.get(k).remove(enemyHit);
            enemyHit.remove();
            playHUDStage.gotHit();
            damage();
        } else {
            for(EnemyCowActor enemy : freeEnemies) {
                float x = enemy.getPositionX();
                float y = enemy.getPositionY();
                if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2)) {
                    enemyHit = enemy;
                    break;
                }
            }
            if(enemyHit != null) {
                if(playHUDStage.getLives() > 0) {
                    Assets.hit.play();
                }
                freeEnemies.remove(enemyHit);
                enemyHit.remove();
                playHUDStage.gotHit();
                damage();
            }
        }
    }

    void checkHitsWithOtherObjects(float centerX, float centerY, float radiusX, float radiusY) {
        for(TargetActor target : targets) {
            if(!targetsFound.contains(target.getId())) {
                float x = target.getPositionX();
                float y = target.getPositionY();
                if(Math.pow((x - centerX)/radiusX, 2) + Math.pow((y - centerY)/radiusY, 2) <= 1) {
                    targetsFound.add(target.getId());
                    Assets.waveIn.play();
                    addActor(new WaveActor(this, x, y, false));
                }
            }
        }
        if(targetsFound.size() == targets.size()) {
            targetsFound.clear();
            mainWave.setAlive(false);
            mainWave = null;
            playHUDStage.enableSonarButton();
        }
    }

    void checkHitsWithMainCharacter(WaveActor waveActor, float centerX, float centerY, float radiusX, float radiusY) {
        float x1 = cameraPosition.x - GGJ2017.WIDTH;
        float y1 = cameraPosition.y - GGJ2017.HEIGHT;
        float x2 = cameraPosition.x - GGJ2017.WIDTH;
        float y2 = cameraPosition.y + GGJ2017.HEIGHT;
        float x3 = cameraPosition.x + GGJ2017.WIDTH;
        float y3 = cameraPosition.y - GGJ2017.HEIGHT;
        float x4 = cameraPosition.x + GGJ2017.WIDTH;
        float y4 = cameraPosition.y + GGJ2017.HEIGHT;
        if(Math.pow((x1 - centerX)/radiusX, 2) + Math.pow((y1 - centerY)/radiusY, 2) <= 1 &&
                Math.pow((x2 - centerX)/radiusX, 2) + Math.pow((y2 - centerY)/radiusY, 2) <= 1 &&
                Math.pow((x3 - centerX)/radiusX, 2) + Math.pow((y3 - centerY)/radiusY, 2) <= 1 &&
                Math.pow((x4 - centerX)/radiusX, 2) + Math.pow((y4 - centerY)/radiusY, 2) <= 1) {
            waveActor.setAlive(false);
            waveActor.remove();
        }
    }

    private void damage() {
        damage1.setVisible(true);
        //mainCharacter.doDamage();
        doDamage = true;
        damageTime = 0;
    }



    enum DIRECTION{RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN, NONE}
}
