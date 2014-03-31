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

 

    
    public MapPlayer(Sprite sprite) {
        super(sprite);
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

        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);
    }

    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }
    

}
