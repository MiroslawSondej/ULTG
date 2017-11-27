package com.mygdx.ultg.world.managers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

import java.util.LinkedList;

public class SkyboxManager {
    OrthographicCamera _camera;
    TiledMap _tiledMap;

    MapGroupLayer _skyLayerContainer;

    TextureRegion _cloudTextureRegionPrefab;
    MapGroupLayer _cloudLayerContainer;
    LinkedList<TiledMapImageLayer> _cloudsList;

    TextureRegion _sky1TextureRegionPrefab;
    TextureRegion _sky2TextureRegionPrefab;
    TextureRegion _sky3TextureRegionPrefab;

    float _cloudsSpeed = 1f;

    public SkyboxManager(OrthographicCamera camera, TiledMap tiledMap) {
        this._camera = camera;
        this._tiledMap = tiledMap;

        TiledMapTileSet skyboxTileSet =  this._tiledMap.getTileSets().getTileSet("Skybox");

        //clouds
        _skyLayerContainer = (MapGroupLayer) _tiledMap.getLayers().get("Sky");
        if(_skyLayerContainer != null) {
            _cloudLayerContainer = (MapGroupLayer) _tiledMap.getLayers().get("Clods");
            if (skyboxTileSet != null) {
                TiledMapTile cloudTile = skyboxTileSet.getTile(0);
                if (cloudTile != null) {
                    _cloudTextureRegionPrefab = cloudTile.getTextureRegion();
                    _cloudLayerContainer.getLayers().add(new TiledMapImageLayer(_cloudTextureRegionPrefab, 0, 0));
                }
                else {
                    Gdx.app.log("Skybox", "CloudTile tileset: no");
                }
            }
            else {
                Gdx.app.log("Skybox", "Clouds: no");
            }
        }
        else {
            Gdx.app.log("Skybox", "Sky: no");
        }

        /*_cloudLayerPrefab = (TiledMapImageLayer) this._tiledMap.getLayers().get("Clouds");
        _cloudLayerPrefab.setVisible(false);

        _cloudLayerContainer = new MapLayer();
        this._tiledMap.getLayers().add(_cloudLayerContainer);


        this._tiledMap.getLayers().add(new TiledMapImageLayer(_cloudLayerPrefab.getTextureRegion(), 0, 0));*/
    }

    public void render(float deltaTime) {

    }


}
