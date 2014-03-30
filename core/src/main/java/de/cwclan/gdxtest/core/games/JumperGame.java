/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.games;

import com.badlogic.gdx.Application;
import de.cwclan.gdxtest.core.entities.Player;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.cwclan.gdxtest.core.LevelGenerator;
import de.cwclan.gdxtest.core.screens.LevelMenu;

/**
 *
 * @author simon
 */
public class JumperGame implements Screen {

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATION = 8, POSITIONITERATION = 3;
    private SpriteBatch batch;
    private Vector3 buttomRight;
    private Vector3 buttomLeft;
    private Body ball;

    private LevelGenerator levelGenerator;
    private Player player;
    private final Array<Body> tmpBodies = new Array<>();

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.getBody().getPosition().x < buttomLeft.x) {
            player.getBody().setTransform(buttomRight.x, player.getBody().getPosition().y, player.getBody().getAngle());
        } else if (player.getBody().getPosition().x > buttomRight.x) {
            player.getBody().setTransform(buttomLeft.x, player.getBody().getPosition().y, player.getBody().getAngle());
        }

        player.update();
        world.step(TIMESTEP, VELOCITYITERATION, POSITIONITERATION);
        camera.position.y = Math.max(player.getBody().getPosition().y, camera.position.y);
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
        levelGenerator.generate(camera.position.y+camera.viewportHeight /2);
        
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 25;
        camera.viewportHeight = height / 25;
    }

    @Override
    public void show() {

        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            Gdx.graphics.setDisplayMode((int) (Gdx.graphics.getHeight() / 1.5), Gdx.graphics.getHeight(), false);
        }

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
        player = new Player(world, 0, 1, 1);
        world.setContactFilter(player);
        world.setContactListener(player);

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

        }, player));

        /*
         * the ground
         */
        ChainShape groundShape = new ChainShape();
        buttomLeft = new Vector3(0, Gdx.graphics.getHeight(), 0);
        buttomRight = new Vector3(Gdx.graphics.getWidth(), buttomLeft.y, 0);
        camera.unproject(buttomLeft);
        camera.unproject(buttomRight);

        groundShape.createChain(new float[]{
            buttomLeft.x, buttomLeft.y,
            buttomRight.x, buttomRight.y});

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);
        fixtureDef.friction = .5f;
        fixtureDef.restitution = 0;
        fixtureDef.shape = groundShape;

        Body ground
                = world.createBody(bodyDef);
        ground.createFixture(fixtureDef);

        groundShape.dispose();

//        //a test platform
//        groundShape = new ChainShape();
//        groundShape.createChain(new float[]{buttomRight.x / 10, 3, buttomRight.x / 4, 3});
//        world.createBody(bodyDef).createFixture(fixtureDef);
//        
        levelGenerator = new LevelGenerator(ground,
                buttomLeft.x, buttomRight.x,
                player.HEIGHT / 2, player.HEIGHT * 3, 
                player.WIDTH * 1.5f, player.WIDTH * 4f, 
                player.WIDTH / 3, 0);//10*MathUtils.degRad);
        
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
