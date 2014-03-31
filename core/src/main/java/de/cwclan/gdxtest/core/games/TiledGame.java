/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.games;

import de.cwclan.gdxtest.core.entities.MapPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.cwclan.gdxtest.core.screens.LevelMenu;

/**
 *
 * @author simon
 */
public class TiledGame implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    private MapPlayer player;

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      //  camera.position.set(player.getX(), player.getY(), 0);
        // camera.update();
        renderer.setView(camera);
        renderer.render();

        renderer.getSpriteBatch().begin();
        player.draw(renderer.getSpriteBatch());
        renderer.getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show() {
        map = new TmxMapLoader().load("maps/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        player = new MapPlayer(new Sprite(new Texture("img/player.png")));
        player.setPosition(600, 2500);
        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {

            @Override
            public boolean keyDown(int keycode) {

                switch (keycode) {
                    case Input.Keys.ESCAPE:
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

           
        }, new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                camera.translate(-deltaX, deltaY);
                camera.update();
                return false;
            }
        }), player.getInputProcessor()));

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
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }

}
