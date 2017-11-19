package com.mygdx.ultg.ui.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

public class OptionTextButton extends TextButton {

    OptionWidget optionWidget;
    OptionLabel optionLabel;

    String staticText;

    public OptionTextButton (String text, Skin skin, OptionWidget.ValuesType valuesType, ArrayList values) {
        super(" ", skin);
        this.staticText = text;
        optionWidget = new OptionWidget(valuesType, values);
    }
    public OptionTextButton (String text, Skin skin, OptionWidget.ValuesType valuesType, int minValue, int maxValue) {
        super(" ", skin);
        this.staticText = text;
        optionWidget = new OptionWidget(valuesType, minValue, maxValue);
    }

    public OptionTextButton(String text, Skin skin, OptionLabel label) {
        super(text, skin);
        this.optionLabel = label;
    }
    public void setOptionLabel(OptionLabel label) {
        this.optionLabel = label;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(optionWidget != null) {
            this.setText(this.staticText + "(" + optionWidget.getCurrentValue() + ")");
        }
        super.draw(batch, parentAlpha);
    }
}
