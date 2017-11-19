package com.mygdx.ultg.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;
import com.mygdx.ultg.ui.widgets.TextButton;

public class MainMenuScreen extends ScreenAdapter  {
    MyGdxGame _game;
    SpriteBatch _batch;
    Stage _stage;

    Texture _logoTexture;

    // UI
    Image _logoImage;

    HorizontalGroup _layoutGroup;

    TextButton[] _menuButton = new TextButton[3];
    int _checkedButtonIndex = 0;

    public MainMenuScreen(MyGdxGame game) {
        _game = game;
        _batch = new SpriteBatch();
        _stage = new Stage();


        // UI
        _layoutGroup = new HorizontalGroup();

        // Logo
        _logoTexture = new Texture("menu/logo.png");
        _logoImage = new Image(_logoTexture);
        _logoImage.setPosition( Gdx.graphics.getWidth()/2 - _logoImage.getWidth()/2, Gdx.graphics.getHeight()/2 + 128);

        // Buttons
        _menuButton[0] = new TextButton("Play", Utility.MENUUI_SKIN);
        _menuButton[1] = new TextButton("Options", Utility.MENUUI_SKIN);
        _menuButton[2] = new TextButton("Exit", Utility.MENUUI_SKIN);

        _menuButton[_checkedButtonIndex].setOver(true);

        _layoutGroup.addActor(_menuButton[0]);
        _layoutGroup.addActor(_menuButton[1]);
        _layoutGroup.addActor(_menuButton[2]);

        _layoutGroup.wrap(true);
        _layoutGroup.wrapSpace(20);
        _layoutGroup.setPosition( Gdx.graphics.getWidth()/2 - 64, Gdx.graphics.getHeight()/2 - 64);

        _stage.addActor(_logoImage);
        _stage.addActor(_layoutGroup);
    }

    private void onUpdate() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            _menuButton[_checkedButtonIndex].setOver(false);

            _checkedButtonIndex++;
            if(_checkedButtonIndex >= _menuButton.length) {
                _checkedButtonIndex = 0;
            }

            _menuButton[_checkedButtonIndex].setOver(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            _menuButton[_checkedButtonIndex].setOver(false);

            _checkedButtonIndex--;
            if(_checkedButtonIndex < 0) {
                _checkedButtonIndex = _menuButton.length - 1;
            }

            _menuButton[_checkedButtonIndex].setOver(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch(_checkedButtonIndex) {
                case 0: { // PLAY BUTTON
                    _game.enterScreen(MyGdxGame.EGameScreen.PLAYMENU);
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
