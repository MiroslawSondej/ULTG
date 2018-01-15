package com.mygdx.ultg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Queue;

public class ParallaxSkybox {
    Sprite _skybox[][];
    Vector2 _skyboxVector[][];


    float _lastX;
    float _lastY;
    float _multiplier;

    public ParallaxSkybox(float x, float y, Texture skyboxTexture, float multiplier) {

        _lastX = x;
        _lastY = y;
        _multiplier = multiplier;

        _skybox = new Sprite[3][3];
        _skyboxVector = new Vector2[3][3];

        for(int sy = 0; sy < 3; sy++) {
            for(int sx = 0; sx < 3; sx++) {
                _skybox[sy][sx] = new Sprite(skyboxTexture);
                _skyboxVector[sy][sx] = new Vector2(0f, 0f);

                _skybox[sy][sx].setScale(1f / PhysicsUtility.PIXELS_PER_METER);
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
                _skyboxVector[sy][sx].add(offsetX, offsetY);
            }
        }

        checkIfNearEdge();
    }
    public void render(Batch batch) {
        for(int sy = 0; sy < 3; sy++) {
            for(int sx = 0; sx < 3; sx++) {
                _skybox[sy][sx].setPosition(_skyboxVector[sy][sx].x - _skybox[sy][sx].getWidth()/2f, _skyboxVector[sy][sx].y - _skybox[sy][sx].getHeight()/2f);
                _skybox[sy][sx].draw(batch);
            }
        }
    }

    private void setPosition(float x, float y) {

        //---------- TOP --------------
        // TOP-LEFT
        _skyboxVector[0][0].set(x - PhysicsUtility.convertPixelsToMeters(_skybox[0][0].getWidth()), y + PhysicsUtility.convertPixelsToMeters(_skybox[0][0].getHeight()));
        // TOP-CENTER
        _skyboxVector[0][1].set(x, y + PhysicsUtility.convertPixelsToMeters(_skybox[0][1].getHeight()));
        // TOP-RIGHT
        _skyboxVector[0][2].set(x + PhysicsUtility.convertPixelsToMeters(_skybox[0][2].getWidth()), y + PhysicsUtility.convertPixelsToMeters(_skybox[0][2].getHeight()));
        //-----------------------------

        //--------- MIDDLE ------------
        // MIDDLE-LEFT
        _skyboxVector[1][0].set(x - PhysicsUtility.convertPixelsToMeters(_skybox[1][0].getWidth()), y);
        // MIDDLE-CENTER
        _skyboxVector[1][1].set(x, y);
        // MIDDLE-RIGHT
        _skyboxVector[1][2].set(x + PhysicsUtility.convertPixelsToMeters(_skybox[1][2].getWidth()), y);
        //-----------------------------

        //--------- BOTTOM ------------
        // BOTTOM-LEFT
        _skyboxVector[2][0].set(x - PhysicsUtility.convertPixelsToMeters(_skybox[2][0].getWidth()), y - PhysicsUtility.convertPixelsToMeters(_skybox[2][0].getHeight()));
        // BOTTOM-CENTER
        _skyboxVector[2][1].set(x, y - PhysicsUtility.convertPixelsToMeters(_skybox[2][1].getHeight()));
        // BOTTOM-RIGHT
        _skyboxVector[2][2].set(x + PhysicsUtility.convertPixelsToMeters(_skybox[2][2].getWidth()), y - PhysicsUtility.convertPixelsToMeters(_skybox[2][2].getHeight()));
        //-----------------------------
    }

    private void checkIfNearEdge() {
        if(_lastX > _skyboxVector[1][0].x && Vector2.dst(_lastX, 0f, _skyboxVector[1][0].x, 0f) >  1.5f * PhysicsUtility.convertPixelsToMeters(_skybox[1][0].getWidth())) {

            Sprite newSkybox[][] = new Sprite[3][3];
            Vector2 newSkyboxVector[][] = new Vector2[3][3];

            //----------

            newSkybox[0][0] = _skybox[0][1];
            newSkybox[1][0] = _skybox[1][1];
            newSkybox[2][0] = _skybox[2][1];

            newSkyboxVector[0][0] = _skyboxVector[0][1];
            newSkyboxVector[1][0] = _skyboxVector[1][1];
            newSkyboxVector[2][0] = _skyboxVector[2][1];

            //----------

            newSkybox[0][1] = _skybox[0][2];
            newSkybox[1][1] = _skybox[1][2];
            newSkybox[2][1] = _skybox[2][2];

            newSkyboxVector[0][1] = _skyboxVector[0][2];
            newSkyboxVector[1][1] = _skyboxVector[1][2];
            newSkyboxVector[2][1] = _skyboxVector[2][2];

            //----------

            newSkybox[0][2] = _skybox[0][0];
            newSkybox[1][2] = _skybox[1][0];
            newSkybox[2][2] = _skybox[2][0];

            newSkyboxVector[0][2] = _skyboxVector[0][0];
            newSkyboxVector[1][2] = _skyboxVector[1][0];
            newSkyboxVector[2][2] = _skyboxVector[2][0];

            //----------

            newSkyboxVector[0][2].set((newSkyboxVector[0][1].x + PhysicsUtility.convertPixelsToMeters(newSkybox[0][1].getWidth())), newSkyboxVector[0][2].y);
            newSkyboxVector[1][2].set((newSkyboxVector[1][1].x + PhysicsUtility.convertPixelsToMeters(newSkybox[1][1].getWidth())), newSkyboxVector[1][2].y);
            newSkyboxVector[2][2].set((newSkyboxVector[2][1].x + PhysicsUtility.convertPixelsToMeters(newSkybox[2][1].getWidth())), newSkyboxVector[2][2].y);

            //==========

            _skybox = newSkybox;
            _skyboxVector = newSkyboxVector;

        }
        if(_lastX < _skyboxVector[1][2].x && Vector2.dst(_lastX, 0f, _skyboxVector[1][2].x, 0f) >  1.5f * PhysicsUtility.convertPixelsToMeters(_skybox[1][2].getWidth())) {
            Sprite newSkybox[][] = new Sprite[3][3];
            Vector2 newSkyboxVector[][] = new Vector2[3][3];

            //----------

            newSkybox[0][2] = _skybox[0][1];
            newSkybox[1][2] = _skybox[1][1];
            newSkybox[2][2] = _skybox[2][1];

            newSkyboxVector[0][2] = _skyboxVector[0][1];
            newSkyboxVector[1][2] = _skyboxVector[1][1];
            newSkyboxVector[2][2] = _skyboxVector[2][1];

            //----------

            newSkybox[0][1] = _skybox[0][0];
            newSkybox[1][1] = _skybox[1][0];
            newSkybox[2][1] = _skybox[2][0];

            newSkyboxVector[0][1] = _skyboxVector[0][0];
            newSkyboxVector[1][1] = _skyboxVector[1][0];
            newSkyboxVector[2][1] = _skyboxVector[2][0];

            //----------

            newSkybox[0][0] = _skybox[0][2];
            newSkybox[1][0] = _skybox[1][2];
            newSkybox[2][0] = _skybox[2][2];

            newSkyboxVector[0][0] = _skyboxVector[0][2];
            newSkyboxVector[1][0] = _skyboxVector[1][2];
            newSkyboxVector[2][0] = _skyboxVector[2][2];

            //----------

            newSkyboxVector[0][0].set((newSkyboxVector[0][1].x - PhysicsUtility.convertPixelsToMeters(newSkybox[0][1].getWidth())), newSkyboxVector[0][0].y);
            newSkyboxVector[1][0].set((newSkyboxVector[1][1].x - PhysicsUtility.convertPixelsToMeters(newSkybox[1][1].getWidth())), newSkyboxVector[1][0].y);
            newSkyboxVector[2][0].set((newSkyboxVector[2][1].x - PhysicsUtility.convertPixelsToMeters(newSkybox[2][1].getWidth())), newSkyboxVector[2][0].y);

            //==========

            _skybox = newSkybox;
            _skyboxVector = newSkyboxVector;
        }
        else if(_lastY > _skyboxVector[2][1].y && Vector2.dst(0f, _lastY, 0f, _skyboxVector[2][1].y) >  1.5f * PhysicsUtility.convertPixelsToMeters(_skybox[2][1].getHeight())) {

            Sprite newSkybox[][] = new Sprite[3][3];
            Vector2 newSkyboxVector[][] = new Vector2[3][3];

            //----------

            newSkybox[1][0] = _skybox[0][0];
            newSkybox[1][1] = _skybox[0][1];
            newSkybox[1][2] = _skybox[0][2];

            newSkyboxVector[1][0] = _skyboxVector[0][0];
            newSkyboxVector[1][1] = _skyboxVector[0][1];
            newSkyboxVector[1][2] = _skyboxVector[0][2];

            //----------

            newSkybox[2][0] = _skybox[1][0];
            newSkybox[2][1] = _skybox[1][1];
            newSkybox[2][2] = _skybox[1][2];

            newSkyboxVector[2][0] = _skyboxVector[1][0];
            newSkyboxVector[2][1] = _skyboxVector[1][1];
            newSkyboxVector[2][2] = _skyboxVector[1][2];

            //----------

            newSkybox[0][0] = _skybox[2][0];
            newSkybox[0][1] = _skybox[2][1];
            newSkybox[0][2] = _skybox[2][2];

            newSkyboxVector[0][0] = _skyboxVector[2][0];
            newSkyboxVector[0][1] = _skyboxVector[2][1];
            newSkyboxVector[0][2] = _skyboxVector[2][2];

            //----------

            newSkyboxVector[0][0].set(newSkyboxVector[0][0].x , (newSkyboxVector[1][0].y + PhysicsUtility.convertPixelsToMeters(newSkybox[0][0].getHeight())));
            newSkyboxVector[0][1].set(newSkyboxVector[0][1].x , (newSkyboxVector[1][1].y + PhysicsUtility.convertPixelsToMeters(newSkybox[0][1].getHeight())));
            newSkyboxVector[0][2].set(newSkyboxVector[0][2].x , (newSkyboxVector[1][2].y + PhysicsUtility.convertPixelsToMeters(newSkybox[0][2].getHeight())));

            //==========

            _skybox = newSkybox;
            _skyboxVector = newSkyboxVector;
        }
        else if(_lastY < _skyboxVector[0][1].y && Vector2.dst(0f, _lastY, 0f, _skyboxVector[0][1].y) >  1.5f * PhysicsUtility.convertPixelsToMeters(_skybox[0][1].getHeight())) {
            Sprite newSkybox[][] = new Sprite[3][3];
            Vector2 newSkyboxVector[][] = new Vector2[3][3];

            //----------

            newSkybox[0][0] = _skybox[1][0];
            newSkybox[0][1] = _skybox[1][1];
            newSkybox[0][2] = _skybox[1][2];

            newSkyboxVector[0][0] = _skyboxVector[1][0];
            newSkyboxVector[0][1] = _skyboxVector[1][1];
            newSkyboxVector[0][2] = _skyboxVector[1][2];

            //----------

            newSkybox[1][0] = _skybox[2][0];
            newSkybox[1][1] = _skybox[2][1];
            newSkybox[1][2] = _skybox[2][2];

            newSkyboxVector[1][0] = _skyboxVector[2][0];
            newSkyboxVector[1][1] = _skyboxVector[2][1];
            newSkyboxVector[1][2] = _skyboxVector[2][2];

            //----------

            newSkybox[2][0] = _skybox[0][0];
            newSkybox[2][1] = _skybox[0][1];
            newSkybox[2][2] = _skybox[0][2];

            newSkyboxVector[2][0] = _skyboxVector[0][0];
            newSkyboxVector[2][1] = _skyboxVector[0][1];
            newSkyboxVector[2][2] = _skyboxVector[0][2];

            //----------

            newSkyboxVector[2][0].set(newSkyboxVector[2][0].x, (newSkyboxVector[1][0].y - PhysicsUtility.convertPixelsToMeters(newSkybox[2][0].getHeight())));
            newSkyboxVector[2][1].set(newSkyboxVector[2][1].x, (newSkyboxVector[1][1].y - PhysicsUtility.convertPixelsToMeters(newSkybox[2][1].getHeight())));
            newSkyboxVector[2][2].set(newSkyboxVector[2][2].x, (newSkyboxVector[1][2].y - PhysicsUtility.convertPixelsToMeters(newSkybox[2][2].getHeight())));

            //==========

            _skybox = newSkybox;
            _skyboxVector = newSkyboxVector;
        }
    }
}
