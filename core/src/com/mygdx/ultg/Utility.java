package com.mygdx.ultg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Utility {
    private final static String MENUUI_TEXTURE_ATLAS_PATH = "skins/menuui.atlas";
    private final static String MENUUI_SKIN_PATH = "skins/menuui.json";

    public static TextureAtlas MENUUI_TEXTUREATLAS = new TextureAtlas(MENUUI_TEXTURE_ATLAS_PATH);
    public static Skin MENUUI_SKIN = new Skin(Gdx.files.internal(MENUUI_SKIN_PATH), MENUUI_TEXTUREATLAS);

    public final static String SETTINGS_FILE_NAME = "settings.xml";

    public static boolean DEBUG_MODE = false;
}
