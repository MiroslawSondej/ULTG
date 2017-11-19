package com.mygdx.ultg.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.ultg.ui.widgets.TextButton;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;
import com.mygdx.ultg.ui.widgets.OptionTextButton;
import com.mygdx.ultg.ui.widgets.OptionWidget;

import java.util.ArrayList;

public class OptionsMenuScreen extends ScreenAdapter {
    MyGdxGame _game;
    SpriteBatch _batch;
    Stage _stage;

    Texture _logoTexture;

    enum EButton {
        VOLUME,
        LANGUAGE,

        RETURN // always last
    };

    // UI
    Image _logoImage;

    HorizontalGroup _layoutGroup;

    ArrayList<TextButton> _menuButton = new ArrayList<TextButton>();
    int _checkedButtonIndex = 0;

    public OptionsMenuScreen(MyGdxGame game) {
        _game = game;
        _batch = new SpriteBatch();
        _stage = new Stage();

        // UI
        _layoutGroup = new HorizontalGroup();

        // Logo
        _logoTexture = new Texture("menu/logo.png");
        _logoImage = new Image(_logoTexture);
        _logoImage.setPosition(Gdx.graphics.getWidth() / 2 - _logoImage.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 128);

        // Buttons
        _menuButton.add(new TextButton("Volume", Utility.MENUUI_SKIN));
        _layoutGroup.addActor(_menuButton.get(0));

        _menuButton.add(new OptionTextButton("Volume", Utility.MENUUI_SKIN, OptionWidget.ValuesType.NUMBERS, 0, 100));
        _layoutGroup.addActor(_menuButton.get(1));

        _menuButton.add(new TextButton("Return", Utility.MENUUI_SKIN));
        _layoutGroup.addActor(_menuButton.get(2));

        _layoutGroup.wrap(true);
        _layoutGroup.wrapSpace(20);
        _layoutGroup.setPosition( Gdx.graphics.getWidth()/2 - 160, Gdx.graphics.getHeight()/2 - 64);

        _stage.addActor(_logoImage);
        _stage.addActor(_layoutGroup);
    }

    private void onUpdate() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            _menuButton.get(_checkedButtonIndex).setOver(false);

            _checkedButtonIndex++;
            if(_checkedButtonIndex >= _menuButton.size()) {
                _checkedButtonIndex = 0;
            }

            _menuButton.get(_checkedButtonIndex).setOver(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            _menuButton.get(_checkedButtonIndex).setOver(false);

            _checkedButtonIndex--;
            if(_checkedButtonIndex < 0) {
                _checkedButtonIndex = _menuButton.size() - 1;
            }

            _menuButton.get(_checkedButtonIndex).setOver(true);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(_checkedButtonIndex == EButton.RETURN.ordinal()) {
                _game.leaveCurrentScreen();
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
