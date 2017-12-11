package com.mygdx.ultg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.ultg.entities.Settings;
import com.mygdx.ultg.screens.GameScreen;
import com.mygdx.ultg.screens.MainMenuScreen;
import com.mygdx.ultg.screens.OptionsMenuScreen;

import java.util.Stack;

public class MyGdxGame extends Game {

	Settings _gameSettings;
	Stack<Screen> _screenStack = new Stack<Screen>();

	public enum EGameScreen {
		MAINMENU,
		OPTIONSMENU,
		PLAYMENU
	}

	@Override
	public void create () {
		// Load settings
		_gameSettings = SettingsManager.load(Utility.SETTINGS_FILE_NAME);
		Gdx.app.log("", "Settings loaded:");
		Gdx.app.log("SETTINGS", "\t Volume: " + _gameSettings.getVolume());
		Gdx.app.log("SETTINGS", "\t Language: " + _gameSettings.getLanguage());

		// Create starting screen
		this.enterScreen(EGameScreen.MAINMENU);
	}

	@Override
	public void dispose () {
	}

	private Screen createScreen(EGameScreen gameScreen) {
		switch (gameScreen) {
			case PLAYMENU: {
				return new GameScreen(this);
			}
			case MAINMENU: {
				return new MainMenuScreen(this);
			}
			case OPTIONSMENU: {
				return new OptionsMenuScreen(this);
			}
			default: {
				return null;
			}
		}
	}

	public void enterScreen(EGameScreen gameScreen) {
		if(this.getScreen() != null) {
			_screenStack.push(this.getScreen());
		}

		Screen newScreen = createScreen(gameScreen);
		if(newScreen != null) {
			this.setScreen(newScreen);
		}
	}
	public void leaveCurrentScreen() {
		if(!_screenStack.empty()) {
			this.setScreen(_screenStack.pop());
		}
	}

	public Settings getGameSettings() {
		return _gameSettings;
	}
}
