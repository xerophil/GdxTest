/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.cwclan.gdxtest.core.tween.ActorAccessor;

/**
 *
 * @author simon
 */
public class MainMenu implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton exitButton, startButton;
    private BitmapFont whiteFont;
    private Label heading;
    private TweenManager tweenManager;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);
        
        stage.act(delta);
        stage.draw();
        
        Table.drawDebug(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(width, height, true);
        table.invalidateHierarchy();
        table.setSize(width, height);
    }

    @Override
    public void show() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        whiteFont = new BitmapFont(Gdx.files.internal("font/verdana_white.fnt"), false);

        skin = new Skin(new TextureAtlas("ui/atlas.pack"));

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button.up");
        buttonStyle.down = skin.getDrawable("button.down");
        buttonStyle.pressedOffsetX = 1;
        buttonStyle.pressedOffsetY = -1;
        buttonStyle.font = whiteFont;

        buttonStyle.fontColor = Color.BLACK;

        exitButton = new TextButton("exit", buttonStyle);
        exitButton.pad(5);
        exitButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

        });

        startButton = new TextButton("start", buttonStyle);
        startButton.pad(5);
        startButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Splash());
            }

        });


        heading = new Label("Strange Stuff", new Label.LabelStyle(whiteFont, Color.WHITE));
        heading.setFontScale(1.5f);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(startButton);
        table.getCell(startButton).spaceBottom(15);
        table.row();
        table.add(exitButton);
        table.debug();
        stage.addActor(table);
        
        //animations
        tweenManager = new TweenManager();
 //       Tween.registerAccessor(Actor.class, new ActorAccessor());

    }

    @Override
    public void hide() {
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
        whiteFont.dispose();

    }

}
