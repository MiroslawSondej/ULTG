package com.mygdx.ultg.ui.widgets;

import com.badlogic.gdx.Gdx;
import java.util.ArrayList;

public class OptionWidget {
    public enum ValuesType {
        STRINGS,
        NUMBERS,
    }

    ValuesType valuesType;
    ArrayList values;

    int currentValueId;

    public OptionWidget(ValuesType valuesType, int minValue, int maxValue) {
        this(valuesType, generateValuesOfRange(minValue, maxValue));
    }
    public OptionWidget(ValuesType valuesType, ArrayList values) {
        this.valuesType = valuesType;

        if(valuesType == ValuesType.STRINGS) {
            this.values = new ArrayList<String>();
            this.values.addAll(values);
        }
        else if(valuesType == ValuesType.NUMBERS) {
            this.values = new ArrayList<Integer>();
            this.values.addAll(values);
        }

        this.setCurrentValueId(0);
    }

    private static ArrayList generateValuesOfRange(int min, int max, int step) {

        //Check params
        try {
            if (min > max)
                throw new Exception("Minimal value cannot be bigger than maximum value.");

            if (step == 0)
                throw new Exception("Step value cannot be zero.");

        } catch (Exception e) {
            Gdx.app.log("OptionLabel", e.getMessage());
            return null;
        }

        // Generate values
        ArrayList<Integer> values = new ArrayList<Integer>();

        for(int i = min; i <= max; i += step) {
            values.add(i);
        }

        return values;
    }
    private static ArrayList generateValuesOfRange(int min, int max) {
        return generateValuesOfRange(min, max, 1);
    }

    public void setCurrentValueId(int id) {
        if(id >= 0 && id < values.size()) {
            this.currentValueId = id;
        }
    }
    public int getCurrentValueId() {
        return currentValueId;
    }
    public Object getCurrentValue() {
        return this.values.get(currentValueId);
    }

    public void nextValue() {
        if(this.values.size() < (currentValueId + 1))
            setCurrentValueId(currentValueId + 1);
    }
    public void previousValue() {
        if((currentValueId - 1) >= 0)
            setCurrentValueId(currentValueId - 1);
    }
}
