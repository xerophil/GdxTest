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

    public ActorAccessor() {
    }

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
