package com.mygdx.ultg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

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

        for(int sy = 0; sy < 3; sy++) {
            for(int sx = 0; sx < 3; sx++) {
                _skybox[sy][sx].translate(offsetX, offsetY);
            }
        }

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
        if(PhysicsUtility.convertPixelsToMeters(_skybox[1][0].getX()) + PhysicsUtility.convertPixelsToMeters(_skybox[1][0].getWidth()) < _lastX) {
            Gdx.app.log("SWAP","LeftToRight" );
            Gdx.app.log("SWAP_", "PosX:" + _skybox[1][0].getX());
            Gdx.app.log("SWAP_", "Offset:" + PhysicsUtility.convertPixelsToMeters(_skybox[1][0].getWidth()));
            Gdx.app.log("SWAP_", "LastX:" + _lastX);

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

            //newSkybox[0][2].setX(_skybox[0][0].getX() + 2 * (PhysicsUtility.convertPixelsToMeters(_skybox[0][0].getWidth())));

            newSkybox[1][2].setX(_skybox[1][2].getX() + (PhysicsUtility.convertPixelsToMeters(_skybox[1][0].getWidth())));

            //newSkybox[2][2].setX(_skybox[2][0].getX() + 2 * (PhysicsUtility.convertPixelsToMeters(_skybox[2][0].getWidth())));

            Gdx.app.log("NEWPOS", "x=" + newSkybox[1][2].getX());
            _skybox = newSkybox;

            return;
        }
    }


}
