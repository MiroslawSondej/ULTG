package com.mygdx.ultg.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.ultg.screens.GameScreen;
import com.mygdx.ultg.world.entities.creatures.Player;
import com.mygdx.ultg.world.events.PlayerInputListener;

public class WorldManager {
    GameScreen _gameScreen;
    GameWorld _world;
    World physicWorld;

    PlayerInputListener _playerInputListener;

    public WorldManager(GameScreen gameScreen) {
        this._gameScreen = gameScreen;
    }

    public boolean createWorld(String worldName) {

        // Setup world
        _world = new GameWorld();

        TiledMap map = new TmxMapLoader().load("levels/" + worldName + ".tmx");
        if(map == null) {
            return false;
        }
        _world.setTiledMap(map);

        // Setup player
        _world.setPlayer(createPlayer(0f, 0f));
        setupPlayerController();
        _gameScreen.getStage().addActor(_world.getPlayer());

        // Setup physic
        physicWorld = new World(new Vector2(0, -98f), true);

        return true;
    }
    private Player createPlayer(float x, float y) {
        Player player = new Player();
        player.setPosition(x, y);
        return player;
    }
    private void setupPlayerController() {
        /*Player player = _world.getPlayer();
        if(player != null) {
            _playerInputListener = new PlayerInputListener(player);
            _gameScreen.getStage().addListener(_playerInputListener);
        }*/
    }


    public TiledMap getTiledMap() {
        return _world.getTiledMap();
    }
}
