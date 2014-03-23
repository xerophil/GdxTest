/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cwclan.gdxtest.core.GdxTest;

/**
 *
 * @author simon
 */
public class Settings implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    /**
     * @return the directory the levels will be saved to and read from
     */
    public static FileHandle levelDirectory() {
        String prefsDir = Gdx.app.getPreferences(GdxTest.TITLE).getString("leveldirectory").trim();
        if (prefsDir != null && !prefsDir.equals("")) {
            return Gdx.files.absolute(prefsDir);
        } else {
            return Gdx.files.absolute(Gdx.files.external(GdxTest.TITLE + "/levels").path()); // return default level directory
        }
    }

    /**
     * @return if vSync is enabled
     */
    public static boolean vSync() {
        return Gdx.app.getPreferences(GdxTest.TITLE).getBoolean("vsync");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, false);
        table.invalidateHierarchy();
    }

    @Override
    public void show() {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        table = new Table(skin);
        table.setFillParent(true);

        final CheckBox vSyncCheckBox = new CheckBox("vSync", skin);
        vSyncCheckBox.setChecked(vSync());

        final TextField levelDirectoryInput = new TextField(levelDirectory().path(), skin); // creating a new TextField with the current level directory already written in it
        levelDirectoryInput.setMessageText("level directory"); // set the text to be shown when nothing is in the TextField

        final TextButton back = new TextButton("BACK", skin);
        back.pad(10);

        ClickListener buttonHandler = new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // event.getListenerActor() returns the source of the event, e.g. a button that was clicked
                if (event.getListenerActor() == vSyncCheckBox) {
                    // save vSync
                    Gdx.app.getPreferences(GdxTest.TITLE).putBoolean("vsync", vSyncCheckBox.isChecked());

                    // set vSync
                    Gdx.graphics.setVSync(vSync());

                    Gdx.app.log(GdxTest.TITLE, "vSync " + (vSync() ? "enabled" : "disabled"));
                } else if (event.getListenerActor() == back) {
                    // save level directory
                    String actualLevelDirectory = levelDirectoryInput.getText().trim().equals("") ? Gdx.files.getExternalStoragePath() + GdxTest.TITLE + "/levels" : levelDirectoryInput.getText().trim(); // shortened form of an if-statement: [boolean] ? [if true] : [else] // String#trim() removes spaces on both sides of the string
                    Gdx.app.getPreferences(GdxTest.TITLE).putString("leveldirectory", actualLevelDirectory);

                    // save the settings to preferences file (Preferences#flush() writes the preferences in memory to the file)
                    Gdx.app.getPreferences(GdxTest.TITLE).flush();

                    Gdx.app.log(GdxTest.TITLE, "settings saved");

                    stage.addAction(sequence(Actions.moveTo(0, stage.getHeight(), .5f), run(() -> {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    })));
                }
            }
        };

        vSyncCheckBox.addListener(buttonHandler);

        back.addListener(buttonHandler);

        // putting everything in the table
        table.add(new Label("SETTINGS", skin, "big")).spaceBottom(50).colspan(3).expandX().row();
        table.add();
        table.add("level directory");
        table.add().row();
        table.add(vSyncCheckBox).top().expandY();
        table.add(levelDirectoryInput).top().fillX();
        table.add(back).bottom().right();

        stage.addActor(table);

        stage.addAction(sequence(Actions.moveTo(0, stage.getHeight()), Actions.moveTo(0, 0, .5f))); // coming in from top animation
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
        stage.dispose();
        skin.dispose();
    }

}
