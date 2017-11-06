package com.mygdx.ultg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.ultg.screens.menu.MainMenuScreen;
import com.mygdx.ultg.screens.menu.OptionsMenuScreen;

import java.util.Stack;

public class MyGdxGame extends Game {

	Stack<Screen> screenStack = new Stack<Screen>();

	public enum EGameScreen {
		MAINMENU,
		OPTIONSMENU,
		PLAYMENU
	}

	@Override
	public void create () {
		this.enterScreen(EGameScreen.MAINMENU);
	}

	@Override
	public void dispose () {
	}

	private Screen createScreen(EGameScreen gameScreen) {
		switch (gameScreen) {
			case PLAYMENU: {
				return null;
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
			screenStack.push(this.getScreen());
		}

		Screen newScreen = createScreen(gameScreen);
		if(newScreen != null) {
			this.setScreen(newScreen);
		}
	}
	public void leaveCurrentScreen() {
		if(!screenStack.empty()) {
			this.setScreen(screenStack.pop());
		}
	}
}
