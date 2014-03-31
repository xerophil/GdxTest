/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author simon
 */
public class MapPlayer extends Sprite {

    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2, gravity = 60 * 1.8f;
    private final InputProcessor inputProcessor = new InputAdapter() {

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.A:
                    velocity.x = -speed;
                    break;
                case Input.Keys.D:
                    velocity.x = speed;
                    break;
                case Input.Keys.W:
                    velocity.y = speed;
                    break;
                case Input.Keys.S:
                    velocity.y = -speed;
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.A:
                case Input.Keys.D:
                    velocity.x = 0;
                    break;
                case Input.Keys.W:
                case Input.Keys.S:
                    velocity.y = 0;
                    break;
                default:
                    return false;
            }
            return true;
        }

    };

    private final TiledMapTileLayer collisionLayer;

    public MapPlayer(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        tileHeight = collisionLayer.getTileHeight();
        tileWidth = collisionLayer.getTileWidth();
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch); //To change body of generated methods, choose Tools | Templates.
    }

    private void update(float deltaTime) {
//        velocity.y -= gravity * deltaTime;
//
//        //clamp velocity
//        if (velocity.y > speed) {
//            velocity.y = speed;
//        } else if (velocity.y < speed) {
//            velocity.y = -speed;
//        }

        //collision detection
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        setX(getX() + velocity.x * deltaTime);
        //move on x
        if (velocity.x < 0) { //links

            //unten links
            collisionX = isColliding(getX(), getY());
            //oben links
            collisionX |= isColliding(getX(), getY() + getHeight());

        } else if (velocity.x > 0) { //rechts

            //unten rechts
            collisionX = isColliding(getX() + getWidth(), getY());
            //oben rechts
            collisionX |= isColliding(getX() + getWidth(), getY() + getHeight());

        }
        if (collisionX) {
            setX(oldX);
            velocity.x = 0;
        }

        setY(getY() + velocity.y * deltaTime);
        //move on y
        if (velocity.y < 0) { //unten
            //unten links
            collisionY = isColliding(getX(), getY());
            //unten rechts
            collisionY |= isColliding(getX()+getWidth(), getY());
            
        } else if (velocity.y > 0) { //oben
            //oben links
            collisionY = isColliding(getX(), getY() + getHeight());
            //oben rechts
            collisionY |= isColliding(getX()+getWidth(), getY() + getHeight());
        }
        if (collisionY) {
            setY(oldY);
            velocity.y = 0;
        }

    }

    private boolean isColliding(float x, float y) {
        return collisionLayer.getCell((int) (x / tileHeight), (int) (y / tileWidth)).getTile().getProperties().containsKey("blocked");
    }

    private float tileHeight;
    private float tileWidth;

    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

}
