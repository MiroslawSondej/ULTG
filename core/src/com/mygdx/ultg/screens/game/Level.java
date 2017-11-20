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

public class Level extends ScreenAdapter {
    MyGdxGame _game;
    SpriteBatch _batch;
    Stage _stage;

    TiledMap _tiledMap;
    OrthographicCamera _camera;
    TiledMapRenderer _tiledMapRenderer;


    public Level(MyGdxGame game) {
        _game = game;
        _batch = new SpriteBatch();
        _stage = new Stage();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        _camera = new OrthographicCamera();
        _camera.setToOrtho(false,w,h);
        _camera.update();
        _tiledMap = new TmxMapLoader().load("levels/demo_scene.tmx");
        _tiledMapRenderer = new OrthogonalTiledMapRenderer(_tiledMap);
    }

    private void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            _camera.translate(-Gdx.graphics.getDeltaTime() * 60f, 0);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            _camera.translate(Gdx.graphics.getDeltaTime() * 60f, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            _camera.translate(0, Gdx.graphics.getDeltaTime() * 60f);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            _camera.translate(0, -Gdx.graphics.getDeltaTime() * 60f);
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


}
