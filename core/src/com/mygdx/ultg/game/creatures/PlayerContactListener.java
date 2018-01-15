package com.mygdx.ultg.game.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.ultg.game.items.Paper;

import java.util.HashMap;
import java.util.Map;

public class PlayerContactListener implements ContactListener {
    Player _player;

    float feetContactPoints;

    public PlayerContactListener(Player player) {
        this._player = player;
        feetContactPoints = 0;
    }

    @Override
    public void beginContact(Contact contact) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        boolean fixtureAIsPlayers = false;
        boolean fixtureBIsPlayers = false;

        // Ground
        if(fixtureA.getBody() == _player.getFeet()) {
            feetContactPoints++;
        }
        if(fixtureB.getBody() == _player.getFeet()) {
            feetContactPoints++;
        }
        // End ground

        if(fixtureA.getBody() == _player.getFeet()
                || fixtureA.getBody() == _player.getHead()
                || fixtureA.getBody() == _player.getTorso()) {
            fixtureAIsPlayers = true;
        }
        else if(fixtureB.getBody() == _player.getFeet()
                || fixtureB.getBody() == _player.getHead()
                || fixtureB.getBody() == _player.getTorso()) {
            fixtureBIsPlayers = false;

        }


        //Check if paper
        if(fixtureAIsPlayers || fixtureBIsPlayers) {
            Fixture nFixture = (!fixtureAIsPlayers) ? fixtureA : fixtureB;

            HashMap<String, Object> userData = (HashMap<String, Object>) nFixture.getBody().getUserData();

            if(userData != null && userData.size() > 0) {
                if(userData.containsKey("name")) {
                    if(userData.get("name").equals("paper")) {
                        contact.setEnabled(false);
                        Paper paper = (Paper)userData.get("object");
                        if(paper != null) {
                            paper.onPickUp(_player);
                            return;
                        }
                    }
                }
            }

        }
        //End check
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // Ground
        if(fixtureA.getBody() == _player.getFeet()) {
            feetContactPoints--;
        }
        if(fixtureB.getBody() == _player.getFeet()) {
            feetContactPoints--;
        }

        if(feetContactPoints < 0) {
            feetContactPoints = 0;
        }
        // End Ground

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
