package de.cwclan.gdxtest.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import de.cwclan.gdxtest.core.screens.Settings;
import de.cwclan.gdxtest.core.screens.Splash;

public class GdxTest extends Game {

    public static final String TITLE = "StrangeStuff";
    public static final String VERSION = "0.0.0";

    @Override
    public void create() {
        Gdx.graphics.setVSync(Settings.vSync());
        setScreen(new Splash());
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
