package com.mygdx.ultg.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton {
    protected boolean isOver;

    public TextButton(String text, Skin skin) {
        super(text, skin);
        this.isOver = false;
    }

    public void setOver(boolean isOver) {
        this.isOver = isOver;
    }

    @Override
    public boolean isOver() {
        return this.isOver;
    }
}
