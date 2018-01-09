package com.mygdx.ultg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Queue;

public class ParallaxSkybox {
    Sprite _skybox[][];


    float _lastX;
    float _lastY;
    float _multiplier;

    public ParallaxSkybox(float x, float y, Texture skyboxTexture, float multiplier) {

        _lastX = x;
        _lastY = y;
        _multiplier = multiplier;

        _skybox = new Sprite[3][3];
        for(int sy = 0; sy < 3; sy++) {
            for(int sx = 0; sx < 3; sx++) {
                _skybox[sy][sx] = new Sprite(skyboxTexture);
                _skybox[sy][sx].setScale(1/PhysicsUtility.PIXELS_PER_METER);
            }
        }

        setPosition(x, y);

        //---------------------------------
    }
    public void update(float x, float y) {
        float offsetX = (_lastX - x) * _multiplier;
        float offsetY = (_lastY - y) * _multiplier;

        _lastX = x;
        _lastY = y;

        /*for(int sy = 0; sy < 3; sy++) {
            for(int sx = 0; sx < 3; sx++) {
                _skybox[sy][sx].translate(offsetX, offsetY);
            }
        }*/

        checkIfNearEdge();
    }
    public void render(Batch batch) {
        for(int sy = 0; sy < 3; sy++) {
            for(int sx = 0; sx < 3; sx++) {
                _skybox[sy][sx].draw(batch);
            }
        }
    }

    private void setPosition(float x, float y) {
        //---------------------------------
        // TOP-LEFT
        _skybox[0][0].setPosition(
                (x - _skybox[0][0].getWidth() / 2) - PhysicsUtility.convertPixelsToMeters(_skybox[0][0].getWidth()),
                (y - _skybox[0][0].getHeight() / 2) + PhysicsUtility.convertPixelsToMeters(_skybox[0][0].getHeight())
        );
        // TOP-MID
        _skybox[0][1].setPosition(
                (x - _skybox[0][1].getWidth() / 2),
                (y - _skybox[0][1].getHeight() / 2) - PhysicsUtility.convertPixelsToMeters(_skybox[0][1].getHeight())
        );
        // TOP-RIGHT
        _skybox[0][2].setPosition(
                (x - _skybox[0][2].getWidth() / 2) + PhysicsUtility.convertPixelsToMeters(_skybox[0][2].getWidth()),
                (y - _skybox[0][2].getHeight() / 2) + PhysicsUtility.convertPixelsToMeters(_skybox[0][2].getHeight())
        );

        //=========
        // MID-LEFT
        _skybox[1][0].setPosition(
                (x - _skybox[1][0].getWidth() / 2) - PhysicsUtility.convertPixelsToMeters(_skybox[1][0].getWidth()),
                (y - _skybox[1][0].getHeight() / 2)
        );
        // MID-MID
        _skybox[1][1].setPosition(
                (x - _skybox[1][1].getWidth() / 2),
                (y - _skybox[1][1].getHeight() / 2)

        );
        // MID-RIGHT
        _skybox[1][2].setPosition(
                (x - _skybox[1][2].getWidth() / 2) + PhysicsUtility.convertPixelsToMeters(_skybox[1][2].getWidth()),
                (y - _skybox[1][2].getHeight() / 2)
        );

        Gdx.app.log("DISTANCE", "" + Vector2.dst(_skybox[1][0].getX(), 0f, _skybox[1][1].getX(), 0f));
        Gdx.app.log("DISTANCE", "" + Vector2.dst(_skybox[1][1].getX(), 0f, _skybox[1][2].getX(), 0f));

        //============
        // BOTTOM-LEFT
        _skybox[2][0].setPosition(
                (x - _skybox[2][0].getWidth() / 2) - PhysicsUtility.convertPixelsToMeters(_skybox[2][0].getWidth()),
                (y - _skybox[2][0].getHeight() / 2) - PhysicsUtility.convertPixelsToMeters(_skybox[2][0].getHeight())
        );
        // BOTTOM-MID
        _skybox[2][1].setPosition(
                (x - _skybox[2][1].getWidth() / 2),
                (y - _skybox[2][1].getHeight() / 2) - PhysicsUtility.convertPixelsToMeters(_skybox[2][1].getHeight())
        );
        // BOTTOM-RIGHT
        _skybox[2][2].setPosition(
                (x - _skybox[2][2].getWidth() / 2) + PhysicsUtility.convertPixelsToMeters(_skybox[2][2].getWidth()),
                (y - _skybox[2][2].getHeight() / 2) - PhysicsUtility.convertPixelsToMeters(_skybox[2][2].getHeight())
        );
    }

    private void checkIfNearEdge() {
        Gdx.app.log("DISTANCE_TEST0","" + Vector2.dst(_skybox[1][0].getX(), 0f, PhysicsUtility.convertMetersToPixels(_lastX), 0f));
        Gdx.app.log("DISTANCE_TEST1","" + Vector2.dst(_skybox[1][1].getX(), 0f, PhysicsUtility.convertMetersToPixels(_lastX), 0f));
        Gdx.app.log("DISTANCE_TEST2","" + Vector2.dst(_skybox[1][2].getX(), 0f, PhysicsUtility.convertMetersToPixels(_lastX), 0f));

        Gdx.graphics.setTitle(Vector2.dst(_skybox[1][0].getX(), 0f, PhysicsUtility.convertMetersToPixels(_lastX), 0f) + "|" + Vector2.dst(_skybox[1][1].getX(), 0f, PhysicsUtility.convertMetersToPixels(_lastX), 0f) + "|" + Vector2.dst(_skybox[1][2].getX(), 0f, PhysicsUtility.convertMetersToPixels(_lastX), 0f));

        if(Vector2.dst(_skybox[1][0].getX(), 0f, PhysicsUtility.convertMetersToPixels(_lastX), 0f) > 1024f) {
            Gdx.app.log("DEBUG","SWAP" );

            Sprite newSkybox[][] = new Sprite[3][3];

            newSkybox[0][0] = _skybox[0][1];
            newSkybox[1][0] = _skybox[1][1];
            newSkybox[2][0] = _skybox[2][1];

            newSkybox[0][1] = _skybox[0][2];
            newSkybox[1][1] = _skybox[1][2];
            newSkybox[2][1] = _skybox[2][2];

            newSkybox[0][2] = _skybox[0][0];
            newSkybox[1][2] = _skybox[1][0];
            newSkybox[2][2] = _skybox[2][0];

            newSkybox[0][2].setX(newSkybox[0][1].getX() + PhysicsUtility.convertPixelsToMeters(newSkybox[0][1].getWidth()));
            newSkybox[1][2].setX(newSkybox[1][1].getX() + PhysicsUtility.convertPixelsToMeters(newSkybox[1][1].getWidth()));
            newSkybox[2][2].setX(newSkybox[2][1].getX() + PhysicsUtility.convertPixelsToMeters(newSkybox[2][1].getWidth()));

            _skybox = null;
            _skybox = newSkybox.clone();
             return;
        }
    }


}
