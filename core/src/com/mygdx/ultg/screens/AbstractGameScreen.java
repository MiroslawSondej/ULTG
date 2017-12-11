package com.mygdx.ultg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.ultg.MyGdxGame;

public abstract class AbstractGameScreen implements Screen {
    protected MyGdxGame _game;
    protected Stage _stage;

    public AbstractGameScreen (MyGdxGame game) {
        this._game = game;
        this._stage = new Stage();
        _stage.setViewport(new ScreenViewport());
    }
    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _stage.act(deltaTime);
        _stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        _stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    @Override
    public void dispose() {
        _stage.dispose();
    }
    @Override
    public void pause () {
    }
    @Override
    public void resume () {
    }
}