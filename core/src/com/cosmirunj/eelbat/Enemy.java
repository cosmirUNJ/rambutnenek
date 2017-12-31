package com.cosmirunj.eelbat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by USER on 28/10/2017.
 */

public class Enemy extends Actor {
    private int SPEED_ENEMY;
    private final int MAX_ANGLE_CHANGE = 300;
    private final int CLOSE_RADIUS = 800;
    private final float TARGET_X;
    private final float TARGET_Y;
    private float x,y;
    private float currentAngle;
    private Texture enemyFish, shadowEnemy;
    private final int SHADOW_OFFSET = 150;
    private int level;
    //private boolean fixed;
    KEJARGAK kejargak;
    private Assets assets;
    private int difficulty;
    private int NYAWA;
    private final int NYAWA1 = 1;
    private final int NYAWA2 = 2;
    private final int NYAWA3 = 3;
    //float eelbatPositionX, eelbatPositionY;
    //float eelbatPositionRadius;
    private float fixAngle;

    Vector2 awal = new Vector2(); //posisi enemynya
    Vector2 akhir = new Vector2(); //posisi eelbat
    Vector2 velocity = new Vector2();
    Vector2 movemnt = new Vector2();
    Vector2 arah = new Vector2();
    Vector2 temp = new Vector2();

//    Vector2[] titikRute;
//    int JUMLAH_TITIK_RUTE = 4;
//    int nextX, nextY;

    boolean punyaRute;
    public Enemy(Assets assets, float x, float y, int difficulty, int level){
        this.difficulty = difficulty;
        //this.fixed = fixed;
        //eelbatPositionX = 0;
        //eelbatPositionY = 0;
        //SPEED_ENEMY = 0;
        //getViewport().getCamera().position;
        if (difficulty == 1){
            SPEED_ENEMY = 100;//100
            this.NYAWA = 3;//NYAWA1;
        }else if (difficulty == 2){
            SPEED_ENEMY = 250;//250
            this.NYAWA = NYAWA2;
        }else if (difficulty == 3){
            SPEED_ENEMY = 400;//400
            this.NYAWA = NYAWA3;
        }


        TARGET_X = x;
        TARGET_Y = y;
        this.x = x + (EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);
        this.y = y + (EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);
//        punyaRute = EelbatCosmir.random.nextBoolean();
//        titikRute = new Vector2[JUMLAH_TITIK_RUTE];
//        titikRute[0].set(x,y);
//        double nextAngle = Math.atan2(y-TARGET_Y,x-TARGET_X)+EelbatCosmir.random.nextInt(100);
//
//        for (int i=1;i<titikRute.length;i++){
//
//            while (nextAngle>360){
//                nextAngle-=360;
//            }
//            while (nextAngle<0){
//                nextAngle+=360;
//            }
//            nextX = (int) Math.cos(nextAngle/180*Math.PI*(EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS));
//            nextY = (int) Math.sin(nextAngle/180*Math.PI)*(EelbatCosmir.random.nextInt(2*CLOSE_RADIUS)-CLOSE_RADIUS);
//            titikRute[i].set(nextX,nextY);
//        }

        //enemyFish = assets.getTexture(Assets.enemyFish);
        shadowEnemy = assets.getTexture(Assets.shadowEnemy);

        kejargak = KEJARGAK.GAK;
        this.level = level;
        this.assets = assets;

        checkLevel();
        fixAngle = 0;
    }


    //public void setStateEnemy(KEJARGAK kejargak, float x, float y, float r, double fixAngle){
    public void setStateEnemy(KEJARGAK kejargak, float eelbatPositionX, float eelbatPositionY){
        this.kejargak = kejargak;

        if (kejargak == KEJARGAK.KEJAR){
            //Gdx.app.log("kenaTag","kenakenaknea");
            //this.fixAngle = fixAngle;
            //eelbatPositionRadius = r;
            //eelbatPositionX = x;
            //eelbatPositionY = y;
            akhir.set(eelbatPositionX,eelbatPositionY);
        }
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(shadowEnemy, x-shadowEnemy.getWidth()/2,y-SHADOW_OFFSET);
        //batch.draw(enemyFish,x-enemyFish.getWidth()/2,y-enemyFish.getHeight()/2);
        batch.draw(enemyFish,x-enemyFish.getWidth()/2,y-enemyFish.getHeight()/2,
                enemyFish.getWidth()/2, enemyFish.getHeight()/2,
                enemyFish.getWidth(), enemyFish.getHeight(),
                1f,1f,getAngleXkeY(),
                0,0,
                enemyFish.getWidth(), enemyFish.getHeight(),
                true,false
                );
    }

    @Override
    public void act(float delta){
        switch (kejargak){
            case KEJAR:

                awal.set(x,y);

                dariXkeY(awal, akhir, delta);

                //currentAngle = (float) Math.cos(eelbatPositionX/eelbatPositionRadius);
                //x -= Math.cos(fixAngle/180*Math.PI)*SPEED_ENEMY*delta;
                //y -= Math.sin(fixAngle/180*Math.PI)*SPEED_ENEMY*delta;
                //Gdx.app.log("kejarTag","delta: "+delta+" x: "+x+" y: "+y);
                //Gdx.app.log("eelbaTag","delta: "+delta+" a: "+eelbatPositionX+" b: "+eelbatPositionY);
                break;
            case GAK:
                if(Math.pow(x - TARGET_X, 2) + Math.pow(y - TARGET_Y, 2) <= Math.pow(CLOSE_RADIUS, 2)) {
                    currentAngle += delta*(EelbatCosmir.random.nextFloat()*2*MAX_ANGLE_CHANGE - MAX_ANGLE_CHANGE);
                } else {
                    if((x - TARGET_X)*Math.sin(currentAngle/180*Math.PI) - (y - TARGET_Y)*Math.cos(currentAngle/180*Math.PI) >= 0) {
                        currentAngle += delta*EelbatCosmir.random.nextFloat()*MAX_ANGLE_CHANGE;
                    } else {
                        currentAngle -= delta*EelbatCosmir.random.nextFloat()*MAX_ANGLE_CHANGE;
                    }
                }
                while(currentAngle > 360) {
                    currentAngle -= 360;
                }
                while(currentAngle < 0) {
                    currentAngle += 360;
                }
                x += delta*Math.cos(currentAngle/180*Math.PI)*SPEED_ENEMY;
                y += delta*Math.sin(currentAngle/180*Math.PI)*SPEED_ENEMY;

                break;
        }
    }

    private void checkLevel() {
        switch (level){
            case 1:
                enemyFish = assets.getTexture(Assets.enemyFish);
                break;
            case 2:
                enemyFish = assets.getTexture(Assets.enemySeaHorse);
                break;
            default:
                enemyFish = assets.getTexture(Assets.manyherringfish);
                break;
        }
    }

    public void dariXkeY(Vector2 mulai, Vector2 finis, float delta ){
        arah.set(finis).sub(awal).nor();
        velocity.set(arah).scl(SPEED_ENEMY);
        movemnt.set(velocity).scl(delta);
        if(mulai.dst2(finis) > movemnt.len2()){
            mulai.add(movemnt);
        } else {
            mulai.set(akhir);
        }
        x = mulai.x;
        y = mulai.y;
        setAngleXkeY(mulai,finis);
    }
    private void setAngleXkeY(Vector2 mulai, Vector2 finis){
        fixAngle = temp.set(finis).sub(mulai).angle();
        Gdx.app.log("angle", String.valueOf(fixAngle));
    }
    private float getAngleXkeY(){
        switch (kejargak){
            case KEJAR:
                return fixAngle;
            default:
                return 0;
        }
    }

    public float getEnemyPositionX(){
        return  x;
    }
    public float getEnemyPositionY(){
        return y;
    }
    public void setNyawaEnemy(int NYAWA){
        this.NYAWA = NYAWA;
    }
    public int getNyawaEnemy(){
        return NYAWA;
    }
    //public int getSpeedEnemy() { return SPEED_ENEMY; }

    enum KEJARGAK{
        GAK, KEJAR
    }
}
