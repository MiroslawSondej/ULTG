package com.mygdx.ultg.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.world.WorldManager;

public class GameScreen implements Screen {
    MyGdxGame _game;
    Stage _stage;

    OrthographicCamera _camera;
    InputMultiplexer _multiplexer;

    TiledMapRenderer _tiledMapRenderer;
    WorldManager _worldManager;

    public GameScreen(MyGdxGame game) {
        String levelName = "playground";

        this._game = game;
        this._stage = new Stage();

        // Setup camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, w * 0.8f, h * 0.8f);

        _worldManager = new WorldManager(this);
        if(!_worldManager.createWorld(levelName)) {
            Gdx.app.log("Level", "Cannot load level: " + levelName);
            _game.leaveCurrentScreen();
        }

        _tiledMapRenderer = new OrthogonalTiledMapRenderer(_worldManager.getTiledMap());

        _multiplexer = new InputMultiplexer();
        _multiplexer.addProcessor(_stage);
    }

    public TiledMapRenderer getTiledMapRenderer() {
        return _tiledMapRenderer;
    }
    public Stage getStage() {
        return _stage;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _camera.update();
        _tiledMapRenderer.setView(_camera);
        _tiledMapRenderer.render();
        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_multiplexer);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        _camera.setToOrtho(false, width * 0.8f, height * 0.8f);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
