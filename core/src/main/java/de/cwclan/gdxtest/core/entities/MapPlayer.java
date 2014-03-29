/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.entities;

import com.badlogic.gdx.Gdx;
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

    public MapPlayer(Sprite sprite) {
        super(sprite);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch); //To change body of generated methods, choose Tools | Templates.
    }

    private void update(float deltaTime) {
        velocity.y -= gravity * deltaTime;

        //clamp velocity
        if (velocity.y > speed) {
            velocity.y = speed;
        } else if (velocity.y < speed) {
            velocity.y = -speed;
        }

        setX(getX() + velocity.x * deltaTime);
        setY(getY() + velocity.y * deltaTime);
    }

}
