package com.mygdx.ultg.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.world.managers.SkyboxManager;

public class Level extends ScreenAdapter {
    MyGdxGame _game;
    SpriteBatch _batch;
    Stage _stage;

    TiledMap _tiledMap;
    OrthographicCamera _camera;
    TiledMapRenderer _tiledMapRenderer;

    SkyboxManager _skyboxManager;


    public Level(MyGdxGame game) {
        _game = game;
        _batch = new SpriteBatch();
        _stage = new Stage();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, w, h);
        _camera.update();
        _tiledMap = new TmxMapLoader().load("levels/playground.tmx");
        _tiledMapRenderer = new OrthogonalTiledMapRenderer(_tiledMap);

        _skyboxManager = new SkyboxManager(_camera, _tiledMap);

        this.resize((int)w, (int)h);
    }

    private void update(float delta) {
        float boost = 1f;

        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            boost = 60f;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            _camera.translate(-Gdx.graphics.getDeltaTime() * 60f * boost, 0);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            _camera.translate(Gdx.graphics.getDeltaTime() * 60f * boost, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            _camera.translate(0, Gdx.graphics.getDeltaTime() * 60f * boost);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            _camera.translate(0, -Gdx.graphics.getDeltaTime() * 60f * boost);
        }
    }

    @Override
    public void render (float delta) {

        update(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        _camera.update();
        _tiledMapRenderer.setView(_camera);
        _tiledMapRenderer.render();
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        _camera.setToOrtho(false, width, height);
        float zoom = Math.abs(1f - (height/5000f));
        if(zoom < 0.5f) zoom = 0.5f;
        _camera.zoom = zoom;
    }
}
