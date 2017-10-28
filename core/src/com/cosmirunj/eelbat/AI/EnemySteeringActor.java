package com.cosmirunj.eelbat.AI;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.cosmirunj.eelbat.AI.Scene2dLocation;
import com.cosmirunj.eelbat.AI.Scene2dSteeringUtils;

/**
 * Created by Inovatif on 10/28/2017.
 */

public class EnemySteeringActor extends Actor implements Steerable<Vector2>{

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
    TextureRegion region;
    Vector2 position;
    Vector2 linearVelocity;
    float angularVelocity;
    float boundingRadius;
    boolean tagged;

    float maxLinearSpeed = 100;
    float maxLinearAcceleration = 200;
    float maxAngularSpeed = 5;
    float maxAngularAcceleration = 10;

    boolean independentFacing;

    SteeringBehavior<Vector2> steeringBehavior;

    public EnemySteeringActor (TextureRegion region) {
        this(region, false);
    }

    public EnemySteeringActor(TextureRegion region, boolean independentFacing) {
        this.independentFacing = independentFacing;
        this.region = region;
        this.position = new Vector2();
        this.linearVelocity = new Vector2();
        this.setBounds(0,0, region.getRegionWidth(), region.getRegionHeight());
        this.boundingRadius = (region.getRegionWidth()+region.getRegionHeight())/4f;
        this.setOrigin(region.getRegionWidth()*.5f, region.getRegionHeight()*.5f);
    }

    public TextureRegion getRegion(){
        return region;
    }
    public void setRegion (TextureRegion region) {
        this.region = region;
    }



    @Override
    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity){
        this.angularVelocity = angularVelocity;
    }
    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxAngularAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxLinearSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getOrientation() {
        return getRotation()* MathUtils.degreesToRadians;
    }

    @Override
    public void setOrientation(float orientation) {
        setRotation(orientation * MathUtils.radiansToDegrees);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return Scene2dSteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return Scene2dSteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Scene2dLocation();
    }

    public boolean isIndependentFacing(){
        return independentFacing;
    }

    public void setIndependentFacing(boolean independentFacing){
        this.independentFacing = independentFacing;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior(){
        return steeringBehavior;
    }

    public void setSteeringBehavior (SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    @Override
    public void act (float delta) {
        position.set(getX(Align.center), getY(Align.center));
        if (steeringBehavior != null) {

            steeringBehavior.calculateSteering(steeringOutput);


            applySteering(steeringOutput, delta);

            wrapAround(position, getParent().getWidth(), getParent().getHeight());
            setPosition(position.x, position.y, Align.center);
        }
        super.act(delta);
    }

    // the display area is considered to wrap around from top to bottom
    // and from left to right
    protected static void wrapAround (Vector2 pos, float maxX, float maxY) {
        if (pos.x > maxX) pos.x = 0.0f;

        if (pos.x < 0) pos.x = maxX;

        if (pos.y < 0) pos.y = maxY;

        if (pos.y > maxY) pos.y = 0.0f;
    }

    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        position.mulAdd(linearVelocity, time);
        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());

        if (independentFacing) {
            setRotation(getRotation() + (angularVelocity * time) * MathUtils.radiansToDegrees);
            angularVelocity += steering.angular * time;
        } else {
            if (!linearVelocity.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linearVelocity);
                angularVelocity = (newOrientation - getRotation() * MathUtils.degreesToRadians) * time; // this is superfluous if independentFacing is always true
                setRotation(newOrientation * MathUtils.radiansToDegrees);
            }
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation());
    }
}
