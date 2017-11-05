package com.mygdx.ultg.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;

public class MainMenuScreen extends ScreenAdapter {
    MyGdxGame _game;
    SpriteBatch _batch;
    Stage _stage;

    Texture _backgroundTexture;

    // UI
    Image _backgroundImage;

    HorizontalGroup _layoutGroup;

    TextButton _playButton;
    TextButton _optionsButton;
    TextButton _exitButton;

    public MainMenuScreen(MyGdxGame game) {
        _game = game;
        _batch = new SpriteBatch();
        _stage = new Stage();

        // UI
        _layoutGroup = new HorizontalGroup();

        // Background
        _backgroundTexture = new Texture("menu/background.png");
        _backgroundImage = new Image(_backgroundTexture);

        // Buttons

        _playButton = new TextButton("Play", Utility.MENUUI_SKIN);
        _optionsButton = new TextButton("Options", Utility.MENUUI_SKIN);
        _exitButton = new TextButton("Options", Utility.MENUUI_SKIN);

        _layoutGroup.addActor(_playButton);
        _layoutGroup.addActor(_optionsButton);
        _layoutGroup.addActor(_exitButton);

        _layoutGroup.wrap(true);
        _layoutGroup.wrapSpace(5);
        _layoutGroup.setPosition( Gdx.graphics.getWidth()/2 - 128, Gdx.graphics.getHeight()/2 - 64);

        _stage.addActor(_backgroundImage);
        _stage.addActor(_layoutGroup);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void dispose () {
        _batch.dispose();
        _backgroundTexture.dispose();
    }
}
