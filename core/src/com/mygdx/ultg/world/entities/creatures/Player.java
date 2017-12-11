package com.mygdx.ultg.world.entities.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor {

    boolean _isControllable;
    Sprite _playerSprite;
    float _playerSpeed = 8f;

    public Player() {
        _isControllable = true;
        _playerSprite = new Sprite( new Texture("sprites/player.png"));
    }

    @Override
    public void act(float delta) {

        float dx = 0f;
        boolean djump = false;

        // Check keyboard

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx = -1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx = 1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            djump |= true;
        }

        // Check gamepads
        Array<Controller> controllerArray = Controllers.getControllers();
        for(Controller c : controllerArray) {
            // check axis
            float axis_l_h = c.getAxis(1); // LEFT AXIS - HORIZONTAL
            axis_l_h = Math.round(axis_l_h * 10f) / 10f;
            if(dx == 0f && axis_l_h != 0f && Math.abs(axis_l_h) > 0.3f) {
                dx = axis_l_h;
            }

            // check button
            boolean buttonA = c.getButton(0);
            djump |= buttonA;
        }
        // Perform player actions
        move(dx * delta);
        if(djump) jump();
    }
    public void move(float dx) {
        float x = getX();
        float y = getY();
        setPosition(x + (dx * _playerSpeed), y);
    }
    public void jump() {

    }
    public boolean isControllable() {
        return _isControllable;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        _playerSprite.setPosition(getX(), getY());
        _playerSprite.draw(batch);
    }

}
