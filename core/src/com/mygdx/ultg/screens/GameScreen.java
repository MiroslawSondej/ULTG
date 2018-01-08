package com.mygdx.ultg.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.ultg.MyGdxGame;
import com.mygdx.ultg.Utility;
import com.mygdx.ultg.game.ParallaxSkybox;
import com.mygdx.ultg.game.ParallaxSkyboxController;
import com.mygdx.ultg.game.PhysicsUtility;
import com.mygdx.ultg.game.creatures.Player;

import java.io.File;

public class GameScreen implements Screen {
    MyGdxGame _game;
    String _levelName;

    // Input
    InputMultiplexer _multiplexer;

    // Game scene
    TiledMap _tiledMap;
    TiledMapRenderer _tiledMapRenderer;
    OrthographicCamera _camera;
    boolean _isCameraAttachedToPlayer = false;

    World _world;
    Stage _stage;

    // Player
    Player _player;

    // Debug
    Box2DDebugRenderer _box2DDebugRenderer;

    ParallaxSkyboxController _parallaxSkyboxController;

    //------------------------------------------------------------------

    public GameScreen(MyGdxGame game, String levelName) {
        _game = game;
        _levelName = levelName;

        // Setup stage & camera
        _stage = new Stage();

        float viewportWidth = PhysicsUtility.convertPixelsToMeters(Gdx.graphics.getWidth());
        float viewportHeight = PhysicsUtility.convertPixelsToMeters(Gdx.graphics.getHeight());
        _camera = (OrthographicCamera) _stage.getCamera();
        _camera.setToOrtho(false, viewportWidth, viewportHeight);
        _camera.update();

        // Setup physics
        _world = new World(new Vector2(0, -9.81f), true);
        _box2DDebugRenderer = new Box2DDebugRenderer();

        // Setup parallax controller
        _parallaxSkyboxController = new ParallaxSkyboxController();

        // Load tiled map
        if(!loadMap(levelName)) {
            if(Utility.DEBUG_MODE) {
                Gdx.app.log("GAMESCREEN_SETUP", "Level '" + levelName + "' not found!");
            }
            _game.leaveCurrentScreen();
        }
        _tiledMapRenderer = new OrthogonalTiledMapRenderer(_tiledMap, 1f/PhysicsUtility.PIXELS_PER_METER, _stage.getBatch());

        // Setup inputs
        _multiplexer = new InputMultiplexer();
        _multiplexer.addProcessor(_stage);
    }
    private boolean loadMap(String levelName) {
        _tiledMap = new TmxMapLoader().load("gameworld/levels/level_1.tmx");

        if(_tiledMap == null) {
            return false;
        }
        // --- Start parsing ---

        // Create colliders for tiledmap
        MapLayer solidColliders = (MapLayer)_tiledMap.getLayers().get("EnvColls");
        if(solidColliders != null) {
            for(MapObject mapObject : solidColliders.getObjects()) {
                createSolidCollider(mapObject);
            }
        }

        // Create player
        MapLayer spawnPoints = (MapLayer)_tiledMap.getLayers().get("Spawners");
        MapObject playerObject = spawnPoints.getObjects().get("Player");

        float playerX = 0.0f;
        float playerY = 0.0f;

        if(playerObject != null) {
            playerX = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(playerObject.getProperties().get("x").toString()));
            playerY = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(playerObject.getProperties().get("y").toString()));
            _player = new Player(_world, playerX, playerY);
            _stage.addActor(_player);

            _isCameraAttachedToPlayer = true;
        }
        // --- End parsing ---


        Texture skyboxTextureBackground = new Texture("gameworld/sprites/skyboxes/skybox1.png");
        ParallaxSkybox parallaxSkyboxBackground = new ParallaxSkybox(playerX, playerY, skyboxTextureBackground, 0.01f);
        _parallaxSkyboxController.addParallaxSkybox(parallaxSkyboxBackground);

        Texture skyboxTextureClouds = new Texture("gameworld/sprites/skyboxes/skybox2.png");
        ParallaxSkybox parallaxSkyboxClouds = new ParallaxSkybox(playerX, playerY, skyboxTextureClouds, 0.1f);
        _parallaxSkyboxController.addParallaxSkybox(parallaxSkyboxClouds);

        return true;
    }

    private void createSolidCollider(MapObject mapObject) {
        String objectType = mapObject.getProperties().get("type").toString();
        if(objectType.equals("BoxCollider")) {
            float x = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(mapObject.getProperties().get("x").toString()));
            float y = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(mapObject.getProperties().get("y").toString()));
            float w = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(mapObject.getProperties().get("width").toString()));
            float h = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(mapObject.getProperties().get("height").toString()));

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(x + w / 2f, y + h / 2f);

            Body objectBody = _world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(w / 2f, h / 2f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            fixtureDef.friction = 0.8f;

            Fixture fixture = objectBody.createFixture(fixtureDef);
            shape.dispose();

        }
        else if(objectType.equals("PolygonCollider")) {
            float x = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(mapObject.getProperties().get("x").toString()));
            float y = PhysicsUtility.convertPixelsToMeters(Float.parseFloat(mapObject.getProperties().get("y").toString()));

            PolygonMapObject polygon = (PolygonMapObject) mapObject;
            float point[] = polygon.getPolygon().getVertices();
            for(int i = 0; i < point.length; i++) {
                point[i] = PhysicsUtility.convertPixelsToMeters(point[i]);
            }

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(x, y);

            Body objectBody = _world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.set(point);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            fixtureDef.friction = 0.8f;

            Fixture fixture = objectBody.createFixture(fixtureDef);
            shape.dispose();
        }
    }
    @Override
    public void render(float delta) {
        // Update
        _stage.act();
        _world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if(_player != null && _isCameraAttachedToPlayer) {
            _camera.position.x = _player.getX();
            _camera.position.y = _player.getY();
        }
        _camera.update();
        _parallaxSkyboxController.update(_camera.position.x, _camera.position.y);

        // Draw
        Gdx.gl.glClearColor(0.14f, 0.12f, 0.09f, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _tiledMapRenderer.setView(_camera);
        //_tiledMapRenderer.render();

        _stage.getBatch().begin();
        _parallaxSkyboxController.render(_stage.getBatch());
        _tiledMapRenderer.renderTileLayer((TiledMapTileLayer)_tiledMap.getLayers().get("FarBackground"));
        _tiledMapRenderer.renderTileLayer((TiledMapTileLayer)_tiledMap.getLayers().get("Background"));
        _stage.getBatch().end();

        _stage.draw();

        _stage.getBatch().begin();
        _tiledMapRenderer.renderTileLayer((TiledMapTileLayer)_tiledMap.getLayers().get("Foreground"));
        _stage.getBatch().end();

        if(Utility.DEBUG_MODE) {
            _box2DDebugRenderer.render(_world, _camera.combined.cpy().scale(1, 1, 1));
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_multiplexer);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        _stage.dispose();
        _world.dispose();
    }
}
