package com.mygdx.ultg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;

public class MainMenuScreen extends AbstractGameScreen {
    Table _table;

    public MainMenuScreen(MyGdxGame game) {
        super(game);

        _table = new Table();
        _table.setFillParent(true);

        Image title = new Image(new Texture("menu/logo.png"));
        TextButton playButton = new TextButton("Play", Utility.MENUUI_SKIN);
        TextButton optionsButton = new TextButton("Options", Utility.MENUUI_SKIN);
        TextButton exitButton = new TextButton("Exit",Utility.MENUUI_SKIN);

        // Layout
        _table.add(title).spaceBottom(100).row();
        _table.add(playButton).spaceBottom(25).row();
        _table.add(optionsButton).spaceBottom(25).row();
        _table.add(exitButton).spaceBottom(25).row();

        // Inputs

        playButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                _game.enterScreen(MyGdxGame.EGameScreen.PLAYMENU);
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                _game.enterScreen(MyGdxGame.EGameScreen.OPTIONSMENU);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }
        });

        _stage.addActor(_table);
    }

}
