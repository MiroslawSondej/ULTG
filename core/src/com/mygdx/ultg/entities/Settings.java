package com.mygdx.ultg.entities;

public class Settings {
    int _volume;
    String _language;

    //-----------------------------------
    public Settings() {
        _volume = 100;
        _language = "english";
    }
    //-----------------------------------

    public int getVolume() {
        return _volume;
    }
    public void setVolume(int value) {
        _volume = value;
    }

    public String getLanguage() {
        return _language;
    }
    public void setLanguage(String value) {
        _language = value;
    }

}
