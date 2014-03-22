/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.tween;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author simon
 */
public class ActorAccessor implements TweenAccessor<Actor> {

    public static final int ALPHA = 0, RGB = 1, Y = 2;

    public ActorAccessor() {
    }

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
            case RGB:
                returnValues[0] = target.getColor().r;
                returnValues[1] = target.getColor().g;
                returnValues[2] = target.getColor().b;
                return 3;
            case Y:
                returnValues[0] = target.getY();
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
            case RGB:
                target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
                break;
            case Y:
                target.setY(newValues[0]);
                break;
            default:
                assert false;
        }
    }

}
