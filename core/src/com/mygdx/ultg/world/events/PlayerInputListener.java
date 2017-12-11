package com.mygdx.ultg.world.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.ultg.world.entities.creatures.Player;

public class PlayerInputListener extends InputListener implements ControllerListener {
    Player _player;

    public PlayerInputListener(Player player) {
        this._player = player;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        Gdx.app.log("KEYDOWN", "" + keycode);
        boolean inputHandled = false;

        if(_player.isControllable()) {
            if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
                this._player.move(-1f);
                inputHandled = true;
            }
            if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
                this._player.move(1f);
                inputHandled = true;
            }
            if(keycode == Input.Keys.SPACE) {
                this._player.jump();
                inputHandled = true;
            }
        }

        return inputHandled;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        boolean inputHandled = false;

        if(_player.isControllable()) {
            if(buttonCode == Xbox.A) {
                this._player.jump();
                inputHandled = true;
            }
        }

        return inputHandled;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        boolean inputHandled = false;

        if(_player.isControllable()) {
            if(axisCode == 1) { // XBOX_L_STICK | Libgdx XBOX mapping is broken for some reasons
                _player.move(Math.round(value * 10f)/10f);
                inputHandled = true;
            }
        }

        return inputHandled;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        boolean inputHandled = false;

        if(_player.isControllable()) {
            if(povCode == Xbox.DPAD_LEFT || povCode == Xbox.DPAD_RIGHT) {
                if(value == PovDirection.west || value == PovDirection.northWest || value == PovDirection.southWest) {
                    _player.move(-1f);
                    inputHandled = true;
                }
                if(value == PovDirection.east || value == PovDirection.northEast || value == PovDirection.southEast) {
                    _player.move(1f);
                    inputHandled = true;
                }
            }
        }

        return inputHandled;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    @Override
    public boolean handle(Event e) {
        Gdx.app.log("handle", "key down: " + e.toString());
        return super.handle(e);
    }
}
