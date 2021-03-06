/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cwclan.gdxtest.core.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
    private TweenManager tweenManager;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        tweenManager.update(delta);
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

        Label heading = new Label("Strange Stuff", skin, "big");

        TextButton exitButton = new TextButton("schluss", skin);
        exitButton.pad(5);
        exitButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                Timeline.createParallel().beginParallel()
                        .push(Tween.to(table, ActorAccessor.ALPHA, .75f).target(0))
                        .push(Tween.to(table, ActorAccessor.Y, .75f).target(table.getY() - 50))
                        .setCallback((int type, BaseTween<?> source) -> {
                            Gdx.app.exit();
                        }).end().start(tweenManager);

            }

        });

        TextButton startButton = new TextButton("los", skin);
        startButton.pad(5);
        startButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
            }

        });

        TextButton testButton = new TextButton("test", skin);
        testButton.pad(5);
        testButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SkinTest());
            }

        });

        TextButton settingsButton = new TextButton("Settings", skin, "small");
        settingsButton.pad(5);
        settingsButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
            }

        });

        /*
         *setting up the table
         */
        table = new Table(skin);
        table.setFillParent(true);
        table.add(heading).spaceBottom(100).row();
        table.add(startButton).spaceBottom(15).row();
        table.add(settingsButton).spaceBottom(15).row();
        table.add(testButton).spaceBottom(15).row();
        table.add(exitButton);
//        table.debug();
        stage.addActor(table);

        /*
         *Animations
         */
        tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        //animate the heading
        Timeline.createSequence().beginSequence()
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
                .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
                .end().repeat(Tween.INFINITY, 0).start(tweenManager);

        //headings and buttons fade in
        Timeline.createSequence().beginSequence()
                .push(Tween.set(startButton, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(testButton, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(exitButton, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
                .push(Tween.to(startButton, ActorAccessor.ALPHA, 0.25f).target(1))
                .push(Tween.to(testButton, ActorAccessor.ALPHA, 0.25f).target(1))
                .push(Tween.to(exitButton, ActorAccessor.ALPHA, 0.25f).target(1))
                .end().start(tweenManager);

        //table fade in
        Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tweenManager);
        Tween.from(table, ActorAccessor.Y, .5f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

        tweenManager.update(Gdx.graphics.getDeltaTime());
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
