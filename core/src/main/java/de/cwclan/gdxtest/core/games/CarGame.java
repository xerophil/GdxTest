/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.games;

import de.cwclan.gdxtest.core.entities.Car;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.cwclan.gdxtest.core.screens.LevelMenu;

/**
 *
 * @author simon
 */
public class CarGame implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATION = 8, POSITIONITERATION = 3;
    private SpriteBatch batch;

    private Car car;
    private final Array<Body> tmpBodies = new Array<>();

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(TIMESTEP, VELOCITYITERATION, POSITIONITERATION);
        camera.position.set(car.getChassis().getPosition().x, car.getChassis().getPosition().y, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {
            if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        batch.end();
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
        batch = new SpriteBatch();

        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        FixtureDef wheelFixtureDef = new FixtureDef();

        //car
        fixtureDef.density = 5;
        fixtureDef.friction = .4f;
        fixtureDef.restitution = .3f;

        wheelFixtureDef.density = fixtureDef.density * 1.5f;
        wheelFixtureDef.friction = 50;
        wheelFixtureDef.restitution = .4f;
        car = new Car(world, fixtureDef, wheelFixtureDef, 0, 3, 3, 1.5f);

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {

                switch (keycode) {
                    case Keys.ESCAPE:
                        ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
                        break;

                }
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                camera.zoom += amount / 25f;
                return true;
            }
        }, car));

        /*
         * the ground
         */
        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-50, 10), new Vector2(-49, 0), new Vector2(-40, 0), new Vector2(-30, -1), new Vector2(-20, 0), new Vector2(-10, -1), new Vector2(0, 1), new Vector2(10, 2), new Vector2(20, 0), new Vector2(30, -1), new Vector2(40, -2), new Vector2(50, 0)});

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;
        fixtureDef.shape = groundShape;
        world.createBody(bodyDef).createFixture(fixtureDef);
        groundShape.dispose();

        /*
         * another ground 
         */
        
        groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(40, -5), new Vector2(50, -3), new Vector2(60, 0), new Vector2(70, -1), new Vector2(80, 3), new Vector2(90, -1), new Vector2(100, 1), new Vector2(110, 2), new Vector2(120, 0), new Vector2(130, -1), new Vector2(140, -2), new Vector2(150, 0)});
        world.createBody(bodyDef).createFixture(fixtureDef);
        groundShape.dispose();


    }
    private Body ball;

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
