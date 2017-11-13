package com.mygdx.ultg.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;

import java.io.Console;
import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame _game;
    SpriteBatch _batch;
    Stage _stage;

    OrthographicCamera _camera;
    Viewport _viewport;

    // Logo
    Texture _logoTexture;
    Image _logoImage;

    HorizontalGroup _layoutGroup;
    int _checkedButtonIndex;

    ArrayList<TextButton> _menuButton;

    public MenuScreen(MyGdxGame game) {
        _menuButton = new ArrayList<TextButton>();

        _game = game;
        _batch = new SpriteBatch();

        _stage = new Stage();
        _camera = new OrthographicCamera();
        _camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        _viewport = new ScreenViewport(_camera);

        _stage.setViewport(_viewport);

        Gdx.input.setInputProcessor(_stage);
        //-----------------------------------

        //Logo
        createLogo();

        // UI
        _layoutGroup = new HorizontalGroup();
        _layoutGroup.wrap(true);
        _layoutGroup.wrapSpace(20);

        _checkedButtonIndex = 0;

        _stage.addActor(_layoutGroup);
    }

    void createLogo() {
        // Logo
        _logoTexture = new Texture("menu/logo.png");
        _logoImage = new Image(_logoTexture);
        _stage.addActor(_logoImage);
    }
    public void updateUIPosition() {
        _logoImage.setPosition( Gdx.graphics.getWidth()/2 - _logoImage.getWidth()/2, Gdx.graphics.getHeight()/2 + 128);
        Gdx.app.log("Example", "UPDATED IU");

    }

    @Override
    public void dispose () {
        _batch.dispose();
        _logoTexture.dispose();
    }

    private void onUpdate() {
        Gdx.app.log("IO", "onUpdate");
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            Gdx.app.log("IO", "DOWN");

            _checkedButtonIndex++;
            if(_checkedButtonIndex >= _menuButton.size()) {
                _checkedButtonIndex = 0;
            }
            //_menuButton.get(_checkedButtonIndex).setChecked(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            _menuButton.get(_checkedButtonIndex).setChecked(false);

            _checkedButtonIndex--;
            if(_checkedButtonIndex < 0) {
                _checkedButtonIndex = _menuButton.size() - 1;
            }

            //_menuButton.get(_checkedButtonIndex).setChecked(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch(_checkedButtonIndex) {
                case 0: { // PLAY BUTTON

                    break;
                }
                case 1: { // OPTIONS BUTTON
                    _game.enterScreen(MyGdxGame.EGameScreen.OPTIONSMENU);
                    break;
                }
                case 2: { // EXIT BUTTON
                    Gdx.app.exit();
                    break;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        _camera.setToOrtho(false, width, height);
        updateUIPosition();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        onUpdate();

        _stage.act(delta);
        _stage.draw();
    }


}
