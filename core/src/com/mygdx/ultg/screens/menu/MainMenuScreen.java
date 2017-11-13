package com.mygdx.ultg.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;

public class MainMenuScreen extends MenuScreen {

    public MainMenuScreen(MyGdxGame game) {
        super(game);

        // Buttons
        TextButton playButton = new TextButton("Play", Utility.MENUUI_SKIN);
        playButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                Gdx.app.log("Example", "entered PLAY");
            }
        });
        _menuButton.add(playButton);
        _menuButton.add(new TextButton("Options", Utility.MENUUI_SKIN));
        _menuButton.add(new TextButton("Exit", Utility.MENUUI_SKIN));

        _menuButton.get(_checkedButtonIndex).setChecked(true);

        for (TextButton button : _menuButton) {
            _layoutGroup.addActor(button);
        }
    }

    @Override
    public void updateUIPosition() {
        super.updateUIPosition();
        _layoutGroup.setPosition( Gdx.graphics.getWidth()/2 - 64, Gdx.graphics.getHeight()/2 - 64);
    }
}
