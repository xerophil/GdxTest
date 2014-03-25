/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author simon
 */
public class Game implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATION = 8, POSITIONITERATION = 3;
    private Body box;
    private float speed = 500;
    private Vector2 movement = new Vector2();

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(TIMESTEP, VELOCITYITERATION, POSITIONITERATION);
        box.applyForceToCenter(movement, true);
        camera.position.set(box.getPosition().x, box.getPosition().y, 0);
        camera.update();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {

                switch (keycode) {
                    case Keys.ESCAPE:

                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
                        break;
                    case Keys.W:
                        movement.y = speed;
                        break;
                    case Keys.A:
                        movement.x = -speed;
                        break;
                    case Keys.S:
                        movement.y = -speed;
                        break;
                    case Keys.D:
                        movement.x = speed;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Keys.W:
                    case Keys.S:
                        movement.y = 0;
                        break;
                    case Keys.A:
                    case Keys.D:
                        movement.x = 0;
                        break;
                }
                return true;
            }

            @Override
            public boolean scrolled(int amount) {
                camera.zoom+=amount/25f;
                return true;
            }

            
        });

        /*
         * the ball
         */
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 1);

        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = ballShape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = .75f;

        Body ball = world.createBody(bodyDef);
        ball.createFixture(fixtureDef);

        ballShape.dispose();

        /*
         * the ground
         */
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-50, -10), new Vector2(50, -10)});

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;
        fixtureDef.shape = groundShape;

        world.createBody(bodyDef).createFixture(fixtureDef);

        groundShape.dispose();

        /*
         * the box
         */
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(2.25f, 10);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(.5f, 1);

        fixtureDef.shape = boxShape;
        fixtureDef.friction = .75f;
        fixtureDef.restitution = .1f;
        fixtureDef.density = 5;

        box = world.createBody(bodyDef);
        box.createFixture(fixtureDef);

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

}
