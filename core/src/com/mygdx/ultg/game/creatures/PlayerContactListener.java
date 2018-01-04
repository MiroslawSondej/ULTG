package com.mygdx.ultg.game.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PlayerContactListener implements ContactListener {
    Player _player;

    float feetContactPoints;

    public PlayerContactListener(Player player) {
        this._player = player;
        feetContactPoints = 0;
    }

    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getBody() == _player.getFeet()) {
            feetContactPoints++;
        }
        if(contact.getFixtureB().getBody() == _player.getFeet()) {
            feetContactPoints++;
        }

        Gdx.app.log("PlayerContactListener", "points: " + feetContactPoints);
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().getBody() == _player.getFeet()) {
            feetContactPoints--;
        }
        if(contact.getFixtureB().getBody() == _player.getFeet()) {
            feetContactPoints--;
        }

        if(feetContactPoints < 0) {
            feetContactPoints = 0;
        }
        Gdx.app.log("PlayerContactListener", "points: " + feetContactPoints);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isTouchingGround() {
        Gdx.app.log("PlayerContactListener", "is touching ground? " + feetContactPoints);
        if(feetContactPoints > 0) {
            return  true;
        }
        return false;
    }
}
