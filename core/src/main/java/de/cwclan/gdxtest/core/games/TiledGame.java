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
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import de.cwclan.gdxtest.core.screens.LevelMenu;
import java.util.Iterator;

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
        camera.update();
        renderer.setView(camera);
        renderer.render();
        AnimatedTiledMapTile.updateAnimationBaseTime();

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
        player = new MapPlayer(new Sprite(new Texture("img/player.png")), (TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(600, 2500);
        camera.position.set(player.getX(), player.getY(), 0);
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
                return false;
            }

        }, new GestureDetector(new GestureDetector.GestureAdapter() {
            @Override
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                camera.translate(-deltaX, deltaY);
                return false;
            }

        }), player.getInputProcessor()));

        //animated tiles
        Array<StaticTiledMapTile> frameTiles = new Array<>(2);

        //find the tile frames
        for (TiledMapTile tiledMapTile : map.getTileSets().getTileSet("minecraft")) {
            if (tiledMapTile.getProperties().containsKey("animation") && tiledMapTile.getProperties().get("animation", String.class).equals("torch")) {
                frameTiles.add((StaticTiledMapTile) tiledMapTile);
            }
        }
        //create the animated tile
        AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(1 / 3f, frameTiles);
        //add the original properties to the new animated tile
        for (TiledMapTile staticTiledMapTile : frameTiles) {
            animatedTile.getProperties().putAll(staticTiledMapTile.getProperties());
        }
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals("torch")) {
                    cell.setTile(animatedTile);
                }
            }
        }

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
