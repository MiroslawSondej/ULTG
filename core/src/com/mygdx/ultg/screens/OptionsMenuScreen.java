package com.mygdx.ultg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;

public class OptionsMenuScreen extends AbstractGameScreen {
    Table _table;

    public OptionsMenuScreen(MyGdxGame game) {
        super(game);

        _table = new Table();
        _table.setFillParent(true);

        Image title = new Image(new Texture("menu/logo.png"));
        TextButton volumeButton = new TextButton("Volume", Utility.MENUUI_SKIN);
        TextButton languageButton = new TextButton("Language", Utility.MENUUI_SKIN);
        TextButton returnButton = new TextButton("Return",Utility.MENUUI_SKIN);

        // Layout
        _table.add(title).spaceBottom(100).row();
        _table.add(volumeButton).spaceBottom(25).row();
        _table.add(languageButton).spaceBottom(25).row();
        _table.add(returnButton).spaceBottom(25).row();

        // Inputs

        volumeButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        languageButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        returnButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                _game.leaveCurrentScreen();
            }
        });

        _stage.addActor(_table);
    }
}
