package com.mygdx.ultg.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.ultg.world.entities.creatures.Player;

public class GameWorld {
    Player _player;
    TiledMap _tiledMap;

    public GameWorld() {

    }

    public Player getPlayer() {
        return _player;
    }
    public void setPlayer(Player player) {
        this._player = player;
    }

    public TiledMap getTiledMap() {
        return _tiledMap;
    }
    public void setTiledMap(TiledMap tiledMap) {
        this._tiledMap = tiledMap;
    }
}
