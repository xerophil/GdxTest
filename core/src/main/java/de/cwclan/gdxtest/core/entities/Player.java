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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author simon
 */
public class Player extends InputAdapter {

    private final Body body;
    private final Fixture fixture;
    public final float width, height;
    private final Vector2 velocity = new Vector2();
    private final float movementForce;

    public Player(World world, float x, float y, float width) {
        this.movementForce = 500;
        this.width = width;
        height = width * 2;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation=true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        fixtureDef.friction = .8f;
        fixtureDef.density = 3;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);

    }

    public void update() {
        body.applyForceToCenter(velocity, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.A:
            case Keys.D:
                velocity.x = 0;
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.A:
                velocity.x = -movementForce;
                break;
            case Keys.D:
                velocity.x = movementForce;
                break;
            default:
                return false;
        }
        return true;

    }

    public float getRestitution() {
        return fixture.getRestitution();
    }

    public void setRestitution(float r) {
        fixture.setRestitution(r);
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }

}
