package com.untamedfox.ggj2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonMeshRenderer;

public class MainCharacter extends Actor {

    private final String TAG = "MainCharacter";

    private SkeletonMeshRenderer renderer;
    //private SkeletonRendererDebug debugRenderer;

    private Texture shadow;
    private float x, y;

    private TextureAtlas atlasFront;
    private Skeleton skeletonFront;
    private AnimationState stateFront;

    private TextureAtlas atlasBack;
    private Skeleton skeletonBack;
    private AnimationState stateBack;

    private TextureAtlas atlasSide;
    private Skeleton skeletonSide;
    private AnimationState stateSide;

    private STATE moveState = STATE.IDLE;
    private FACING facing = FACING.FRONT;

    private final int VERTICAL_OFFSET = 120;
    private final int SHADOW_VERTICAL_OFFSET = 180;

    public MainCharacter(GGJ2017 ggj2017) {
        renderer = new SkeletonMeshRenderer();

        shadow = ggj2017.assets.getTexture(Assets.shadow);

        atlasFront = ggj2017.assets.manager.get(Assets.atlasFront);
        atlasBack = ggj2017.assets.manager.get(Assets.atlasBack);
        atlasSide = ggj2017.assets.manager.get(Assets.atlasSide);

        SkeletonJson jsonFront = new SkeletonJson(atlasFront);
        jsonFront.setScale(0.7f);
        SkeletonData skeletonDataFront = jsonFront.readSkeletonData(Gdx.files.internal("spines/bat-front/skeleton-front.json"));
        skeletonFront = new Skeleton(skeletonDataFront);

        SkeletonJson jsonBack = new SkeletonJson(atlasBack);
        jsonBack.setScale(0.7f);
        SkeletonData skeletonDataBack = jsonBack.readSkeletonData(Gdx.files.internal("spines/bat-back/skeleton-back.json"));
        skeletonBack = new Skeleton(skeletonDataBack);

        SkeletonJson jsonSide = new SkeletonJson(atlasSide);
        jsonSide.setScale(0.7f);
        SkeletonData skeletonDataSide = jsonSide.readSkeletonData(Gdx.files.internal("spines/bat-side/skeleton.json"));
        skeletonSide = new Skeleton(skeletonDataSide);

        AnimationStateData stateDataFront = new AnimationStateData(skeletonDataFront);
        //stateDataFront.setMix("run", "jump", 0.2f);
        //stateDataFront.setMix("jump", "run", 0.2f);
        stateFront = new AnimationState(stateDataFront);
        //stateFront.setTimeScale(2); // Double the speed.
        stateFront.setAnimation(0, moveState.toString(), true);

        AnimationStateData stateDataBack = new AnimationStateData(skeletonDataBack);
        stateBack = new AnimationState(stateDataBack);
        //stateBack.setAnimation(0, moveState.toString(), true);

        AnimationStateData stateDataSide = new AnimationStateData(skeletonDataSide);
        stateSide = new AnimationState(stateDataSide);
        //stateSide.setAnimation(0, moveState.toString(), true);
    }

    void updatePosition(float x, float y, PlayGameStage.DIRECTION direction) {
        this.x = x;
        this.y = y;
        boolean changed = false;
        if(direction != PlayGameStage.DIRECTION.NONE){
            Assets.steps.setLooping(true);
            Assets.steps.play();
        }
        else{
            Assets.steps.pause();
        }
        if(facing != FACING.BACK &&
                (direction == PlayGameStage.DIRECTION.UP ||
                        direction == PlayGameStage.DIRECTION.RIGHT_UP ||
                        direction == PlayGameStage.DIRECTION.LEFT_UP)) {
            facing = FACING.BACK;
            changed = true;
        } else if(facing != FACING.FRONT &&
                (direction == PlayGameStage.DIRECTION.DOWN ||
                        direction == PlayGameStage.DIRECTION.RIGHT_DOWN ||
                        direction == PlayGameStage.DIRECTION.LEFT_DOWN)) {
            facing = FACING.FRONT;
            changed = true;
        } else if(facing != FACING.RIGHT && direction == PlayGameStage.DIRECTION.RIGHT) {
            facing = FACING.RIGHT;
            changed = true;
        } else if(facing != FACING.LEFT && direction == PlayGameStage.DIRECTION.LEFT) {
            facing = FACING.LEFT;
            changed = true;
        }
        if(direction == PlayGameStage.DIRECTION.NONE) {
            if(moveState == STATE.RUN) {
                changed = true;
                moveState = STATE.IDLE;
            }
            if(facing == FACING.RIGHT || facing == FACING.LEFT) {
                facing = FACING.FRONT;
            }
        } else {
            if(moveState == STATE.IDLE || changed) {
                changed = true;
                moveState = STATE.RUN;
            }
        }
        if(changed) {
            if(facing == FACING.FRONT) {
                stateFront.setAnimation(0, moveState.toString(), true);
                stateFront.setTimeScale(moveState == STATE.RUN ? 2 : 1);
            } else if(facing == FACING.BACK) {
                stateBack.setAnimation(0, moveState.toString(), true);
                stateBack.setTimeScale(moveState == STATE.RUN ? 2 : 1);
            } else {
                stateSide.setAnimation(0, moveState.toString(), true);
                stateSide.setTimeScale(moveState == STATE.RUN ? 2 : 1);
            }
        }

        boolean flipped = facing == FACING.FRONT ? skeletonFront.getFlipX() : facing == FACING.BACK ? skeletonBack.getFlipX() : skeletonSide.getFlipX();
        boolean doFlip =
                flipped &&
                        (direction == PlayGameStage.DIRECTION.RIGHT ||
                        direction == PlayGameStage.DIRECTION.RIGHT_UP ||
                        direction == PlayGameStage.DIRECTION.RIGHT_DOWN) ||
                !flipped &&
                        (direction == PlayGameStage.DIRECTION.LEFT ||
                        direction == PlayGameStage.DIRECTION.LEFT_UP ||
                        direction == PlayGameStage.DIRECTION.LEFT_DOWN);

        if(doFlip) {
            if(facing == FACING.FRONT) {
                skeletonFront.setFlipX(!skeletonFront.getFlipX());
            } else if(facing == FACING.BACK) {
                skeletonBack.setFlipX(!skeletonBack.getFlipX());
            } else {
                skeletonSide.setFlipX(!skeletonSide.getFlipX());
            }
        }

        if(facing == FACING.FRONT) {
            skeletonFront.setPosition(x, y - VERTICAL_OFFSET);
        } else if(facing == FACING.BACK) {
            skeletonBack.setPosition(x, y - VERTICAL_OFFSET);
        } else {
            skeletonSide.setPosition(x, y - VERTICAL_OFFSET);
        }
    }

    @Override
    public void act(float delta) {
        if(facing == FACING.FRONT) {
            stateFront.update(delta);
            stateFront.apply(skeletonFront);
            skeletonFront.updateWorldTransform();
        } else if(facing == FACING.BACK) {
            stateBack.update(delta);
            stateBack.apply(skeletonBack);
            skeletonBack.updateWorldTransform();
        } else {
            stateSide.update(delta);
            stateSide.apply(skeletonSide);
            skeletonSide.updateWorldTransform();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(shadow, x - shadow.getWidth()/2, y - SHADOW_VERTICAL_OFFSET);
        if(facing == FACING.FRONT) {
            renderer.draw((PolygonSpriteBatch) batch, skeletonFront);
        } else if(facing == FACING.BACK) {
            renderer.draw((PolygonSpriteBatch) batch, skeletonBack);
        } else {
            renderer.draw((PolygonSpriteBatch) batch, skeletonSide);
        }
    }

    private enum STATE{
        IDLE, RUN;

        @Override
        public String toString() {
            return this == IDLE ? "idle" : "run";
        }
    }

    private enum FACING{FRONT, BACK, RIGHT, LEFT}
}
