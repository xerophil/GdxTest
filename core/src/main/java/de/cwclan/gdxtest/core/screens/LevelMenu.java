/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author simon
 */
public class LevelMenu implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Table.drawDebug(stage);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, true);
        table.invalidateHierarchy();
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

        List list = new List(new String[]{"de.cwclan.gdxtest.core.games.JumperGame"}, skin);
        list.setSelectedIndex(0);
        ScrollPane scrollPane = new ScrollPane(list, skin);
        scrollPane.setWidth(30);

        TextButton playButton = new TextButton("Druf da", skin);
        playButton.pad(5);
        playButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                try {
                    Class<? extends Screen> clazz = (Class<? extends Screen>) Class.forName(list.getSelection());
                    Screen screen = clazz.newInstance();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                    System.err.println(ex.getMessage());
                }

            }

        });

        TextButton backButton = new TextButton("zurück", skin, "small");
        backButton.pad(5);
        backButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });

        table = new Table(skin);
        table.setFillParent(true);
        table.debug();
        table.setFillParent(true);
        table.add("Select Level", "big").colspan(3).expandX().spaceBottom(50).row();
        table.add(scrollPane).uniformX().left().expandY();
        table.add(playButton).uniformX();
        table.add(backButton).uniformY().bottom().right();
        stage.addActor(table);

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
