package com.mygdx.ultg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ItemTexturesManager {

    private static ItemTexturesManager instance = null;

    Texture _itemTexture;
    public enum Item {
        BOOK,
        PAPER
    }


    protected ItemTexturesManager() {
        _itemTexture = new Texture(Gdx.files.internal("gameworld/sprites/item_tileset.png"));
    }

    public static ItemTexturesManager getInstance() {
        if(instance == null) {
            instance = new ItemTexturesManager();
        }
        return instance;
    }

    public TextureRegion getTextureForItem(Item item) {
        TextureRegion region = null;

        switch(item) {
            case BOOK: {
                region = new TextureRegion(_itemTexture, 0, 0, 32, 32);
                break;
            }
            case PAPER: {
                region = new TextureRegion(_itemTexture, 32, 0, 32, 32);
                break;
            }
        }

        return region;
    }
}
