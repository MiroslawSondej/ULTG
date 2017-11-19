package com.mygdx.ultg.ui.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;

public class OptionLabel extends Label {

    OptionWidget optionWidget;

    public OptionLabel (Skin skin, OptionWidget.ValuesType valuesType, ArrayList values) {
        super("", skin);
        optionWidget = new OptionWidget(valuesType, values);
    }
    public OptionLabel (Skin skin, OptionWidget.ValuesType valuesType, int minValue, int maxValue) {
        super("", skin);
        optionWidget = new OptionWidget(valuesType, minValue, maxValue);
    }

    public void previousValue() {
        optionWidget.previousValue();
    }
    public void nextValue() {
        optionWidget.nextValue();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.setText(optionWidget.getCurrentValue().toString());
        super.draw(batch, parentAlpha);
    }
}
