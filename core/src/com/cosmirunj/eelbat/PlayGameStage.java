package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private final int CLOSE_RADIUS = 800;
    //private EnemyList enemyList;


    private ArrayList<Fruits> targets, mapBuff;
    private Map<Integer, Set<Enemy>> fixedEnemies;
    private Set<Aksesoris> freeEnemies;
    private HashSet<Integer> targetsFound;
    private ArrayList<Fruits> collectedTargets, collectedBuffs;

    static final int TOTAL_MF1 = 1;
    static final int TOTAL_MF2 = 0;
    static final int TOTAL_MF3 = 0;
    static final int TOTAL_MF = 3;
    private final int MAX_RADIUS_X = 5*EelbatCosmir.WIDTH;
    private final int MAX_RADIUS_Y = 5*EelbatCosmir.HEIGHT;


    private PlayHUDStage playHUDStage;
    private EelbatCosmir eelbatCosmir;
    ShapeRenderer shapeRenderer;
    private Sonar mainWave;
    private float touchpadXnya;
    private float touchpadYnya;
    private Touchpad touchpad;
    private int level;
    private int difficulty;
    private Vector3 cameraPosition;
    private BackgroundTiles backgroundTiles;
    private float x = 0;
    private float y = 0;

    private int remainingmf = TOTAL_MF;

    private float time;
    private float respawningTime;
    private final int TOTAL_TIME = 3*60;
    private float abilityDuration;
    private float damageDuration;
    private float coolDown;

    private float score;
    private final int TOTAL_SCORE = 200;

    private CharacterEelBat characterEelBat;

    private Fruits buff;
    private boolean buffPicked;
    private int buffPickedCount;

    private Image ability;
    private Image damage;

    private boolean abilityUsed;
    private boolean isCoolDown;
    private boolean doDamage;
    private int randomNumberAbility;

    FORM form;

    public PlayGameStage(Viewport gameViewport, EelbatCosmir eelbatCosmir, PlayHUDStage playHUDStage, float touchpadXnya, float touchpadYnya, Touchpad touchpad, int level, int difficulty) {
        super(gameViewport, eelbatCosmir.batch);
        this.playHUDStage = playHUDStage;
        this.eelbatCosmir = eelbatCosmir;

        
        this.level = level;
        this.difficulty = difficulty;

        time = TOTAL_TIME;
        score = TOTAL_SCORE;

        respawningTime = 10;
        abilityDuration = 1;
        damageDuration = 1;

        if (difficulty == 1){
            coolDown = 3;
        }else if (difficulty == 2){
            coolDown = 5;
        }else if (difficulty == 3){
            coolDown = 7;
        }

        shapeRenderer = new ShapeRenderer();

        this.touchpadXnya = touchpadXnya;
        this.touchpadYnya = touchpadYnya;
        this.touchpad = touchpad;

        cameraPosition = getViewport().getCamera().position;

        backgroundTiles = new BackgroundTiles(eelbatCosmir, level);
        addActor(backgroundTiles);

        if(level == 1){
            form = FORM.EEL;
        }else if(level == 2){
            form = FORM.EELBAT;
        }else if(level == 3){
            form = FORM.WINGED_EELBAT;
        }

        characterEelBat = new CharacterEelBat(eelbatCosmir);
        //characterEelBat.updatePosition(x,y,DIRECTION.NONE);
        characterEelBat.updatePosition(x,y,DIRECTION.NONE, form);
        addActor(characterEelBat);

        collectedTargets = new ArrayList<Fruits>();
        collectedBuffs = new ArrayList<Fruits>();

        //enemyList = new EnemyList(eelbatCosmir);
        //enemyList.addEnemies();
        mapBuff = new ArrayList<Fruits>();
        targets = new ArrayList<Fruits>();
        targetsFound = new HashSet<Integer>();
        fixedEnemies = new HashMap<Integer, Set<Enemy>>();
        freeEnemies = new HashSet<Aksesoris>();
        for(int i = 0; i < remainingmf; i++) {
            float x = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
            int mf = EelbatCosmir.random.nextInt(2);;
            Fruits fruit = new Fruits(eelbatCosmir.assets, x, y, i, mf);
            targets.add(fruit);
            addActor(fruit);
            //TargetActor target = new TargetActor(ggj2017.assets, x, y, i);
            //targets.add(target);
            //addActor(target);
            Set<Enemy> enemyGroup = new HashSet<Enemy>();
            int k = 7 + EelbatCosmir.random.nextInt(8);
            for(int j = 0; j < k; j++) {
                Enemy enemy = new Enemy(eelbatCosmir.assets, x, y, difficulty, level);
                enemyGroup.add(enemy);
                addActor(enemy);//1
            }
            fixedEnemies.put(i, enemyGroup);
        }

        for(int i = 0; i < 50; i++) {
            float x = EelbatCosmir.random.nextInt(2 * MAX_RADIUS_X) - MAX_RADIUS_X;
            float y = EelbatCosmir.random.nextInt(2 * MAX_RADIUS_Y) - MAX_RADIUS_Y;
            Aksesoris aksesoris = new Aksesoris(eelbatCosmir.assets, x, y, level);
            freeEnemies.add(aksesoris);
            addActor(aksesoris);
        }

        float a = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
        float b = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
        int c = -1;
        int d = 3 + EelbatCosmir.random.nextInt(5);
        buff = new Fruits(eelbatCosmir.assets, a, b, c, d);
        mapBuff.add(buff);
        addActor(buff);

        buffPicked = false;
        abilityUsed = false;
        isCoolDown = false;
        doDamage = false;
        buffPickedCount = 0;

        ability = new Image(eelbatCosmir.assets.getTexture(Assets.ability));
        ability.setVisible(false);
        addActor(ability);

        damage = new Image(eelbatCosmir.assets.getTexture(Assets.damage));
        damage.setVisible(false);
        addActor(damage);

        //backgroundTiles.update(x,y);


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

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            sendMainWave();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            useAbility();
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

        //characterEelBat.updatePosition(x, y, direction);
        characterEelBat.updatePosition(x, y, direction, form);

        backgroundTiles.update(x, y);

        playHUDStage.update(time);
        if(respawningTime < 0){
            respawningBuff();
            buffPicked = false;
            respawningTime = 10*(buffPickedCount+1);
        }

        if(abilityDuration < 0){
            abilityUsed = false;
            abilityDuration = 1;
            ability.setVisible(false);
        }

        if(coolDown < 0){
            isCoolDown = false;
            if (difficulty == 1){
                coolDown = 3;
            }else if (difficulty == 2){
                coolDown = 5;
            }else if (difficulty == 3){
                coolDown = 7;
            }
        }

        if(damageDuration < 0){
            doDamage = false;
            damageDuration = 1;
            damage.setVisible(false);
        }

        checkCollisions();
        checkRadiusEnemy(delta);

    }

    public float getCameraPositionX(){
        return cameraPosition.x;
    }
    public float getCameraPositionY(){
        return cameraPosition.y;
    }

    public boolean checkRadiusEnemy(float delta) {
        /*
        if(Math.pow(x,2)+Math.pow(y,2) <= Math.pow(CLOSE_RADIUS,2)){
            return true;
        } else {
            return false;
        }
        */
        //int zz = -1;
        Enemy enemyRadius = null;
        for (int i: fixedEnemies.keySet()){
            if(enemyRadius != null){
                break;
            }
            for (Enemy enemy : fixedEnemies.get(i)){
                float x = enemy.getEnemyPositionX();
                float y = enemy.getEnemyPositionY();
                if(Math.pow(x-cameraPosition.x,2)+Math.pow(y-cameraPosition.y,2) <= Math.pow(CLOSE_RADIUS,2)){

//                    Vector2 awal = new Vector2();
//                    Vector2 akhir = new Vector2();
//                    Vector2 velocity = new Vector2();
//                    Vector2 movemnt = new Vector2();
//                    Vector2 arah = new Vector2();
//
//                    akhir.set(cameraPosition.x,cameraPosition.y);
//                    awal.set(enemy.getEnemyPositionX(),enemy.getEnemyPositionY());
//                    arah.set(akhir).sub(awal).nor();
//                    velocity.set(arah).scl(enemy.getSpeedEnemy());
//                    movemnt.set(velocity).scl(delta);
//                    if(awal.dst2(akhir) > movemnt.len2()){
//                        awal.add(movemnt);
//                    } else {
//                        awal.set(akhir);
//                    }
//                    x = awal.x;
//                    y = awal.y;

                    //enemyRadius = enemy;
                    //zz = i;
                    //double radiusnya = Math.sqrt(Math.pow(x-characterEelBat.getPositionEelbatX(),2)+Math.pow(y-characterEelBat.getPositionEelbatY(),2));
                    //double fixAngle = Math.atan2(y-cameraPosition.y,x-cameraPosition.x);
                    //enemy.setStateEnemy(Enemy.KEJARGAK.KEJAR, characterEelBat.getPositionEelbatX(), characterEelBat.getPositionEelbatY(), (float) radiusnya, fixAngle);
                    enemy.setStateEnemy(Enemy.KEJARGAK.KEJAR, cameraPosition.x, cameraPosition.y);
                    //fixedEnemies.get(zz).
                    //Gdx.app.log("myTag","kenakenaknea"+fixedEnemies.get(i));
                    //break;
                } else {
                    enemy.setStateEnemy(Enemy.KEJARGAK.GAK, 0, 0);
                }
            }
        }
        if(enemyRadius != null){
            return true;
        } else {
            return false;
        }
    }

    /*private void checkTarget() {
        //kalo buahnya == 2, nambah buah baru dan nambah enemny
        if(remainingmf == TOTAL_MF2+TOTAL_MF3){
            form = FORM.EELBAT;
            for(int i = 0; i < TOTAL_MF2; i++) {
                float x = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
                float y = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
                int mf = 1;
                Fruits fruit = new Fruits(eelbatCosmir.assets, x, y, i, mf);
                targets.add(fruit);
                addActor(fruit);
                Set<Enemy> enemyGroup = new HashSet<Enemy>();
                int k = 7 + EelbatCosmir.random.nextInt(8);
                for(int j = 0; j < k; j++) {
                    Enemy enemy = new Enemy(eelbatCosmir.assets, x, y, difficulty, level);
                    enemyGroup.add(enemy);
                    addActor(enemy);//2
                }
                fixedEnemies.put(TOTAL_MF1+i, enemyGroup);
            }
        }

        if(remainingmf == TOTAL_MF3){
            form = FORM.WINGED_EELBAT;
            for(int i = 0; i < TOTAL_MF3; i++) {
                float x = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
                float y = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
                int mf = 2;
                Fruits fruit = new Fruits(eelbatCosmir.assets, x, y, i, mf);
                targets.add(fruit);
                addActor(fruit);
                Set<Enemy> enemyGroup = new HashSet<Enemy>();
                int k = 7 + EelbatCosmir.random.nextInt(8);
                for(int j = 0; j < k; j++) {
                    Enemy enemy = new Enemy(eelbatCosmir.assets, x, y, difficulty, level);
                    enemyGroup.add(enemy);
                    addActor(enemy);//3
                }
                fixedEnemies.put(TOTAL_MF1+TOTAL_MF2+i, enemyGroup);
            }
        }
    }*/

    public boolean sendMainWave(){
        boolean canSend = mainWave == null;
        if(canSend) {
            Assets.waveOut.play(1.0f);
            mainWave = new Sonar(this, cameraPosition.x, cameraPosition.y, true);
            addActor(mainWave);
            //playHUDStage.showSonarImage();
        }
//        Assets.waveOut.play(1.0f);
//        mainWave = new Sonar(this, cameraPosition.x, cameraPosition.y, true);
//        addActor(mainWave);
        return canSend;
    }

    void useAbility(){
//        boolean canSend = mainWave == null;
//        if(canSend) {
//            Assets.waveOut.play(1.0f);
//            ability = new Ability(this, cameraPosition.x, cameraPosition.y, true);
//            addActor(mainWave);
//        }
//        return canSend;
        if(!isCoolDown){
            Assets.waveOut.play(1.0f);
            setRandomAbility(eelbatCosmir.random.nextInt(100));
            //Ability ability = new Ability(this, cameraPosition.x, cameraPosition.y);
            //addActor(ability);
            abilityUsed = true;
            isCoolDown = true;
        }
    }

    @Override
    public void act(float delta) {
        updateLocation(delta);
        time -= delta;
        if(time < 0) {
            eelbatCosmir.setScreen(new FinishedScreen(eelbatCosmir, false, level, difficulty));
        }
        if(buffPicked){
            respawningTime -= delta;
        }
        if(abilityUsed){
            abilityDuration -= delta;
            ability.setVisible(true);
            ability.setPosition(x - ability.getWidth()/2, y - ability.getHeight()/2);
        }
        if(isCoolDown){
            coolDown -= delta;
        }
        if(doDamage){
            damageDuration -= delta;
            damage.setVisible(true);
            damage.setPosition(x - damage.getWidth()/2, y - damage.getHeight()/2);
        }
        super.act(delta);
    }

    private void checkCollisions() {
        //COLLISION BUAH AJAIB
        for(Fruits fruit : targets) {
            float x = fruit.getPositionX();
            float y = fruit.getPositionY();
            if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2)) {
                collectedTargets.add(fruit);
            }
        }

        if(collectedTargets.size() > 0) {
            for(Fruits fruit : collectedTargets) {
                Assets.pick.play();
                fruit.deactivate();
                targets.remove(fruit);
            }
            //playHUDStage.setTargetsFound(TOTAL_MF1 - targets.size());
            time += 30;
            remainingmf -= 1;
            //checkTarget();
            collectedTargets.clear();
            playHUDStage.updateScore(500);
            playHUDStage.healthRestored();
            if(targets.size() == 0) {
                //ggj2017.setScreen(new FinishedScreen(ggj2017, true));
                eelbatCosmir.setScreen(new FinishedScreen(eelbatCosmir, true, level, difficulty));
            }
        }

        //COLLISION MUSUH
        int k = -1;
        Enemy enemyHit = null;
        Aksesoris aksesorisHit = null;
        for(int i : fixedEnemies.keySet()) {
            if(enemyHit != null) {
                break;
            }
            for(Enemy enemy : fixedEnemies.get(i)) {
                float x = enemy.getEnemyPositionX();
                float y = enemy.getEnemyPositionY();
                if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2)) {
                    //dapetin nyawa enemy
                    int NYAWA_ENEMY = enemy.getNyawaEnemy();
                    int random1 = getRandomAbility();

                    //cek nyawanya tinggal 0 atau gak?
                    //kalo nol, set enemyhit buat di remove;
                    //kalo bukan, nyawa -  1
                    switch (NYAWA_ENEMY){
                        case 0:
                            enemyHit = enemy;
                            k = i;
                            break;
                        default:
                            enemy.setNyawaEnemy(NYAWA_ENEMY-1);
                            break;
                    }

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
            if(!abilityUsed){
                time -= 30;
                playHUDStage.gotHit();
                doDamage = true;
            }
            //damage();
        }else{
            if(level == 2){
                for(Aksesoris aksesoris : freeEnemies) {
                    float x = aksesoris.getPositionX();
                    float y = aksesoris.floatgetPositionY();
                    if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2)) {
                        aksesorisHit = aksesoris;
                        break;
                    }
                }
                if(aksesorisHit != null) {
                    if(playHUDStage.getLives() > 0) {
                        Assets.hit.play();
                    }
                    freeEnemies.remove(aksesorisHit);
                    aksesorisHit.remove();
                    if(!abilityUsed){
                        time -= 30;
                        playHUDStage.gotHit();
                        doDamage = true;
                    }
                }
            }
        }

        //COLLISION BUAH LAUT
        for(Fruits buff : mapBuff) {
            float x = buff.getPositionX();
            float y = buff.getPositionY();
            if(Math.pow(x - cameraPosition.x, 2) + Math.pow(y - cameraPosition.y, 2) <= Math.pow(COLLECT_RANGE, 2)) {
                collectedBuffs.add(buff);
            }
        }

        if(collectedBuffs.size() > 0) {
            for(Fruits buff : collectedBuffs) {
                Assets.pick2.play();
                buff.deactivate();
                mapBuff.remove(buff);
            }
            time += 10;
            collectedBuffs.clear();
            buffPicked = true;
            buffPickedCount += 1;
            playHUDStage.updateScore(200);
            playHUDStage.healthRestored();
        }


    }

    void checkHitsWithOtherObjects(float centerX, float centerY, float radiusX, float radiusY) {
        for(Fruits fruit : targets) {
            if(!targetsFound.contains(fruit.getId())) {
                float x = fruit.getPositionX();
                float y = fruit.getPositionY();
                if(Math.pow((x - centerX)/radiusX, 2) + Math.pow((y - centerY)/radiusY, 2) <= 1) {
                    targetsFound.add(fruit.getId());
                    //Assets.waveIn.play();
                    addActor(new Sonar(this, x, y, false));
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

    void checkHitsWithMainCharacter(Sonar sonar, float centerX, float centerY, float radiusX, float radiusY) {
        float x1 = cameraPosition.x - EelbatCosmir.WIDTH;
        float y1 = cameraPosition.y - EelbatCosmir.HEIGHT;
        float x2 = cameraPosition.x - EelbatCosmir.WIDTH;
        float y2 = cameraPosition.y + EelbatCosmir.HEIGHT;
        float x3 = cameraPosition.x + EelbatCosmir.WIDTH;
        float y3 = cameraPosition.y - EelbatCosmir.HEIGHT;
        float x4 = cameraPosition.x + EelbatCosmir.WIDTH;
        float y4 = cameraPosition.y + EelbatCosmir.HEIGHT;
        if(Math.pow((x1 - centerX)/radiusX, 2) + Math.pow((y1 - centerY)/radiusY, 2) <= 1 &&
                Math.pow((x2 - centerX)/radiusX, 2) + Math.pow((y2 - centerY)/radiusY, 2) <= 1 &&
                Math.pow((x3 - centerX)/radiusX, 2) + Math.pow((y3 - centerY)/radiusY, 2) <= 1 &&
                Math.pow((x4 - centerX)/radiusX, 2) + Math.pow((y4 - centerY)/radiusY, 2) <= 1) {
            sonar.setAlive(false);
            sonar.remove();
        }
    }

    private void respawningBuff() {
        float a = EelbatCosmir.random.nextInt(2*MAX_RADIUS_X) - MAX_RADIUS_X;
        float b = EelbatCosmir.random.nextInt(2*MAX_RADIUS_Y) - MAX_RADIUS_Y;
        int c = -1;
        int d = 3 + EelbatCosmir.random.nextInt(5);
        buff = new Fruits(eelbatCosmir.assets, a, b, c, d);
        mapBuff.add(buff);
        addActor(buff);
    }

    public void setRandomAbility(int randomNumberAbility){
        this.randomNumberAbility = randomNumberAbility;
    }
    public int getRandomAbility(){
        return randomNumberAbility;
    }

    enum DIRECTION{RIGHT, LEFT, UP, DOWN, RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN, NONE}

    enum FORM{EEL, EELBAT, WINGED_EELBAT}
}
