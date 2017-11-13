package com.mygdx.ultg.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;

import java.util.ArrayList;

public class OptionsMenuScreen extends ScreenAdapter {
    MyGdxGame _game;
    SpriteBatch _batch;
    Stage _stage;

    Texture _logoTexture;

    // UI
    Image _logoImage;

    HorizontalGroup _layoutGroup;

    ArrayList<TextButton> _menuButton = new ArrayList<TextButton>();
    int _checkedButtonIndex = 0;

    public OptionsMenuScreen(MyGdxGame game) {
        _game = game;
        _batch = new SpriteBatch();
        _stage = new Stage();

        Gdx.input.setInputProcessor(_stage);

        // UI
        _layoutGroup = new HorizontalGroup();

        // Logo
        _logoTexture = new Texture("menu/logo.png");
        _logoImage = new Image(_logoTexture);
        _logoImage.setPosition(Gdx.graphics.getWidth() / 2 - _logoImage.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 128);

        // Buttons

        // ---Return button---
        TextButton returnButton = new TextButton("Return", Utility.MENUUI_SKIN);
        returnButton.addListener(new ClickListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                Gdx.app.log("Example", "touch started at (" +keycode+ ")");
                return false;
            }

            public boolean keyUp(InputEvent event, int keycode) {
                Gdx.app.log("Example", "touch done at (" +keycode+ ")");
                return false;
            }
        });
        _menuButton.add(returnButton);
        _layoutGroup.addActor(_menuButton.get(_menuButton.size() - 1));
        // ---Return button end---


        _layoutGroup.wrap(true);
        _layoutGroup.wrapSpace(20);
        _layoutGroup.setPosition( Gdx.graphics.getWidth()/2 - 64, Gdx.graphics.getHeight()/2 - 64);

        _menuButton.get(_checkedButtonIndex).setChecked(true);

        _stage.addActor(_logoImage);
        _stage.addActor(_layoutGroup);
    }

    private void onUpdate() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            _menuButton.get(_checkedButtonIndex).setChecked(false);

            _checkedButtonIndex++;
            if(_checkedButtonIndex >= _menuButton.size()) {
                _checkedButtonIndex = 0;
            }

            _menuButton.get(_checkedButtonIndex).setChecked(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            _menuButton.get(_checkedButtonIndex).setChecked(false);

            _checkedButtonIndex--;
            if(_checkedButtonIndex < 0) {
                _checkedButtonIndex = _menuButton.size() - 1;
            }

            _menuButton.get(_checkedButtonIndex).setChecked(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {

            switch(_checkedButtonIndex) {
                case 1: {
                    _game.leaveCurrentScreen();
                    break;
                }
            }
        }
    }


    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        onUpdate();

        _stage.act(delta);
        _stage.draw();
    }
    @Override
    public void dispose () {
        _batch.dispose();
        _logoTexture.dispose();
    }
}
