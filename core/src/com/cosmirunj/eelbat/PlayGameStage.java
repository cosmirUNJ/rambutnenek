package com.cosmirunj.eelbat;

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

/**
 * Created by Inovatif on 10/18/2017.
 */

class PlayGameStage extends Stage {

    private final int ATTACK_RANGE = 200;
    private final int COLLECT_RANGE = 150;

    private final int TOTAL_TIME = 3*60;

    private EnemyList enemyList;
    private float time;


    private Map<Integer, Set<Enemy>> fixedEnemies;
    private Set<Enemy> freeEnemies;
    private HashSet<Integer> targetsFound;
    static final int TOTAL_TARGETS = 5;
    private final int MAX_RADIUS_X = 5*EelbatCosmir.WIDTH;
    private final int MAX_RADIUS_Y = 5*EelbatCosmir.HEIGHT;


    private PlayHUDStage playHUDStage;
    private EelbatCosmir eelbatCosmir;
    private ShapeRenderer shapeRenderer;
    private float touchpadXnya;
    private float touchpadYnya;
    private Touchpad touchpad;
    private Vector3 cameraPosition;
    private BackgroundTiles backgroundTiles;
    private float x = 0;
    private float y = 0;

    private float time;
    private final int TOTAL_TIME = 3*60;

    private float score;
    private final int TOTAL_SCORE = 200;


    private CharacterEelBat characterEelBat;

    public PlayGameStage(Viewport gameViewport, EelbatCosmir eelbatCosmir, PlayHUDStage playHUDStage, float touchpadXnya, float touchpadYnya, Touchpad touchpad) {
        super(gameViewport, eelbatCosmir.batch);
        this.playHUDStage = playHUDStage;
        this.eelbatCosmir = eelbatCosmir;

        time = TOTAL_TIME;
        score = TOTAL_SCORE;

        shapeRenderer = new ShapeRenderer();

        this.touchpadXnya = touchpadXnya;
        this.touchpadYnya = touchpadYnya;
        this.touchpad = touchpad;

        cameraPosition = getViewport().getCamera().position;

        backgroundTiles = new BackgroundTiles(eelbatCosmir);
        addActor(backgroundTiles);

        characterEelBat = new CharacterEelBat(eelbatCosmir);
        characterEelBat.updatePosition(x,y,DIRECTION.NONE);
        addActor(characterEelBat);

        //enemyList = new EnemyList(eelbatCosmir);
        //enemyList.addEnemies();
        targetsFound = new HashSet<Integer>();
        fixedEnemies = new HashMap<Integer, Set<Enemy>>();
        freeEnemies = new HashSet<Enemy>();
        for(int i = 0; i < TOTAL_TARGETS; i++) {
            float x = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
            //TargetActor target = new TargetActor(ggj2017.assets, x, y, i);
            //targets.add(target);
            //addActor(target);
            Set<Enemy> enemyGroup = new HashSet<Enemy>();
            int k = 7 + EelbatCosmir.random.nextInt(8);
            for(int j = 0; j < k; j++) {
                Enemy enemy = new Enemy(eelbatCosmir.assets, x, y, true);
                enemyGroup.add(enemy);
                addActor(enemy);
            }
            fixedEnemies.put(i, enemyGroup);
        }

        for(int i = 0; i < 50; i++) {
            float x = EelbatCosmir.random.nextInt(2 * MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = EelbatCosmir.random.nextInt(2 * MAX_RADIUS_Y) - MAX_RADIUS_Y;
            Enemy enemy = new Enemy(eelbatCosmir.assets, x, y, false);
            freeEnemies.add(enemy);
            addActor(enemy);
        }

        backgroundTiles.update(x,y);


    }

    @Override
    public void act(float delta){
        updateLocation(delta);
        super.act(delta);
    }

    private void updateLocation(float delta) {
        DIRECTION direction;

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

        switch (align) {
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

        float sq = (float)Math.sqrt(2);
        if(direction == DIRECTION.RIGHT_UP || direction == DIRECTION.RIGHT_DOWN) {
            x += delta*EelbatCosmir.RUN_SPEED/sq;
        } else if(direction == DIRECTION.RIGHT) {
            x += delta*EelbatCosmir.RUN_SPEED;
        } else if(direction == DIRECTION.LEFT_UP || direction == DIRECTION.LEFT_DOWN) {
            x -= delta*EelbatCosmir.RUN_SPEED/sq;
        } else if(direction == DIRECTION.LEFT) {
            x -= delta*EelbatCosmir.RUN_SPEED;
        }

        if(direction == DIRECTION.RIGHT_UP || direction == DIRECTION.LEFT_UP) {
            y += delta*EelbatCosmir.RUN_SPEED/sq;
        } else if(direction == DIRECTION.UP) {
            y += delta*EelbatCosmir.RUN_SPEED;
        } else if(direction == DIRECTION.RIGHT_DOWN || direction == DIRECTION.LEFT_DOWN) {
            y -= delta*EelbatCosmir.RUN_SPEED/sq;
        } else if(direction == DIRECTION.DOWN) {
            y -= delta*EelbatCosmir.RUN_SPEED;
        }
        cameraPosition.x = x;
        cameraPosition.y = y;

        //mainCharacter.updatePosition(x, y, direction);

        backgroundTiles.update(x, y);

        //playHUDStage.update(time);

    }

    @Override
    public void act(float delta) {
        updateLocation(delta);
        super.act(delta);
    }

    private void updateLocation(float delta) {
        DIRECTION direction;
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            sendMainWave();
        }

        float sq = (float)Math.sqrt(2);
        if(direction == DIRECTION.RIGHT_UP || direction == DIRECTION.RIGHT_DOWN) {
            //x += delta*GGJ2017.RUN_SPEED/sq;
            //x ++;
            x += delta*800/sq;
        } else if(direction == DIRECTION.RIGHT) {
            x += delta*800;
            //x ++;
        } else if(direction == DIRECTION.LEFT_UP || direction == DIRECTION.LEFT_DOWN) {
            //x -= delta*GGJ2017.RUN_SPEED/sq;
            //x --;
            x -= delta*800/sq;
        } else if(direction == DIRECTION.LEFT) {
            //x -= delta*GGJ2017.RUN_SPEED;
            //x --;
            x -= delta*800;
        }

        if(direction == DIRECTION.RIGHT_UP || direction == DIRECTION.LEFT_UP) {
            //y += delta*GGJ2017.RUN_SPEED/sq;
            //y ++;
            y += delta*800/sq;
        } else if(direction == DIRECTION.UP) {
            //y += delta*GGJ2017.RUN_SPEED;
            //y ++;
            y += delta*800;
        } else if(direction == DIRECTION.RIGHT_DOWN || direction == DIRECTION.LEFT_DOWN) {
            //y -= delta*GGJ2017.RUN_SPEED/sq;
            //y --;
            y -= delta*800/sq;
        } else if(direction == DIRECTION.DOWN) {
            //y -= delta*GGJ2017.RUN_SPEED;
            //y --;
            y -= delta*800;
        }
        cameraPosition.x = x;
        cameraPosition.y = y;

        characterEelBat.updatePosition(x, y, direction);

    }

    public boolean sendMainWave() {
        return true;
    }

    @Override
    public void act(float delta) {
        updateLocation(delta);
        time -= delta;
    }

    private void updateLocation(float delta){
        playHUDStage.update(time);

    }


    enum DIRECTION{RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN, NONE}
}
