/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

/**
 *
 * @author simon
 */
public class Car extends InputAdapter {

    private Body chassis, leftWheel, rightWheel;
    private WheelJoint leftAxis, rightAxis;
    private World world;
    private float motorSpeed = 75;

    public Car(World world, FixtureDef chassisFixtureDef, FixtureDef wheelFixtureDef, float x, float y, float width, float height) {
        this.world = world;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        //chassis
        PolygonShape chassisShape = new PolygonShape();
        chassisShape.set(new float[]{-width / 2, -height / 2, width / 2, -height / 2, width / 2 * .4f, height / 2, -width / 2 * .8f, height / 2 * .8f});
        chassisFixtureDef.shape = chassisShape;
        chassis = world.createBody(bodyDef);
        chassis.createFixture(chassisFixtureDef);

        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(height / 3.5f);
        wheelFixtureDef.shape = wheelShape;

        //left wheel
        leftWheel = world.createBody(bodyDef);
        leftWheel.createFixture(wheelFixtureDef);

        //right wheel
        rightWheel = world.createBody(bodyDef);
        rightWheel.createFixture(wheelFixtureDef);

        //left axis
        WheelJointDef axisDef = new WheelJointDef();
        axisDef.bodyA = chassis;
        axisDef.bodyB = leftWheel;
        axisDef.localAnchorA.set(-width / 2 * .75f + wheelShape.getRadius(), -height / 2 * 1.25f);
        axisDef.frequencyHz = chassisFixtureDef.density;
        axisDef.localAxisA.set(Vector2.Y);
        axisDef.maxMotorTorque=chassisFixtureDef.density*10;

        leftAxis = (WheelJoint) world.createJoint(axisDef);

        //right axis
        axisDef.bodyB = rightWheel;
        axisDef.localAnchorA.x *= -1;

        rightAxis = (WheelJoint) world.createJoint(axisDef);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
                leftAxis.enableMotor(true);
                leftAxis.setMotorSpeed(-motorSpeed);
                break;
            case Keys.S:
                leftAxis.enableMotor(true);
                leftAxis.setMotorSpeed(motorSpeed);

                break;
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.S:
                leftAxis.enableMotor(false);
                break;
        }
        return true;
    }

    public Body getChassis() {
        return chassis;
    }

}
