package com.mygdx.ultg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.ultg.screens.menu.MainMenuScreen;

public class MyGdxGame extends Game {

	@Override
	public void create () {
		MainMenuScreen mainMenuScreen = new MainMenuScreen(this);
		this.setScreen(mainMenuScreen);
	}

	@Override
	public void dispose () {
	}
}
