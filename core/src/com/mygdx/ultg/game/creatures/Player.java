package com.mygdx.ultg.game.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.ultg.game.PhysicsUtility;

public class Player extends Actor {

    private enum BodyPart {
      HEAD ,
      TORSO,
      FEET
    };

    World _world;
    Body _body[];

    boolean _isControllable;
    Sprite _sprite;

    PlayerContactListener _contactListener;
    float _jumpCooldown = 0f;

    public Player(World world, float x, float y) {
        _isControllable = true;
        _sprite = new Sprite( new Texture("gameworld/sprites/creatures/player.png"));
        _sprite.setScale(1/ PhysicsUtility.PIXELS_PER_METER);
        _world = world;

        // Setup physical object
        float w = PhysicsUtility.convertPixelsToMeters(_sprite.getWidth());
        float h = PhysicsUtility.convertPixelsToMeters(_sprite.getHeight());

        _body = new Body[3];

        //Head
        BodyDef headBodyDef = new BodyDef();
        headBodyDef.type = BodyDef.BodyType.DynamicBody;
        headBodyDef.position.set(x, y);
        headBodyDef.fixedRotation = true;

        _body[BodyPart.HEAD.ordinal()] = world.createBody(headBodyDef);
        CircleShape headShape = new CircleShape();
        headShape.setRadius(w / 2);

        FixtureDef headFixtureDef = new FixtureDef();
        headFixtureDef.shape = headShape;
        headFixtureDef.density = 1f;
        Fixture headFixture = _body[BodyPart.HEAD.ordinal()].createFixture(headFixtureDef);


        //Torso
        BodyDef torsoBodyDef = new BodyDef();
        torsoBodyDef.type = BodyDef.BodyType.DynamicBody;
        torsoBodyDef.position.set(x, y);
        torsoBodyDef.fixedRotation = true;

        _body[BodyPart.TORSO.ordinal()] = world.createBody(headBodyDef);
        PolygonShape torsoShape = new PolygonShape();
        torsoShape.setAsBox(w / 4, h / 4);

        FixtureDef torsoFixtureDef = new FixtureDef();
        torsoFixtureDef.shape = torsoShape;
        torsoFixtureDef.density = 1f;
        Fixture torsoFixture = _body[BodyPart.TORSO.ordinal()].createFixture(torsoFixtureDef);


        //Feet
        BodyDef feetBodyDef = new BodyDef();
        feetBodyDef.type = BodyDef.BodyType.DynamicBody;
        feetBodyDef.position.set(x, y);
        feetBodyDef.fixedRotation = true;

        _body[BodyPart.FEET.ordinal()] = world.createBody(feetBodyDef);
        CircleShape feetShape = new CircleShape();
        feetShape.setRadius(w / 4);

        FixtureDef feetFixtureDef = new FixtureDef();
        feetFixtureDef.shape = feetShape;
        feetFixtureDef.density = 400f;
        feetFixtureDef.friction = 0.2f;
        Fixture feetFixture  = _body[BodyPart.FEET.ordinal()].createFixture(feetFixtureDef);

        Gdx.app.log("MASS", "= " + feetFixture.getBody().getMass());


        RevoluteJointDef headToTorsoJointDef = new RevoluteJointDef();
        headToTorsoJointDef.bodyA = _body[BodyPart.HEAD.ordinal()];
        headToTorsoJointDef.bodyB = _body[BodyPart.TORSO.ordinal()];
        headToTorsoJointDef.localAnchorA.set(0f, 0f);
        headToTorsoJointDef.localAnchorB.set(0f, 0.2f);
        headToTorsoJointDef.collideConnected = false;
        _world.createJoint(headToTorsoJointDef);


        RevoluteJointDef feetToTorsoJointDef = new RevoluteJointDef();
        feetToTorsoJointDef.bodyA = _body[BodyPart.TORSO.ordinal()];
        feetToTorsoJointDef.bodyB = _body[BodyPart.FEET.ordinal()];
        feetToTorsoJointDef.localAnchorA.set(0f, 0f);
        feetToTorsoJointDef.localAnchorB.set(0f, 0.7f);
        feetToTorsoJointDef.collideConnected = false;
        _world.createJoint(feetToTorsoJointDef);

        headShape.dispose();
        torsoShape.dispose();
        feetShape.dispose();

        // Listeners
        _contactListener = new PlayerContactListener(this);
        _world.setContactListener(_contactListener);
    }
    public Body getHead() {
        return _body[BodyPart.HEAD.ordinal()];
    }
    public Body getTorso() {
        return _body[BodyPart.TORSO.ordinal()];
    }
    public Body getFeet() {
        return _body[BodyPart.FEET.ordinal()];
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
        move(dx);
        if(djump) jump();
    }
    /*public void move(float dx) {
        float x = getX();
        float y = getY();
        setPosition(x + (dx * _playerSpeed), y);
    }*/
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(_body != null) {
            setPosition(_body[BodyPart.TORSO.ordinal()].getPosition().x, _body[BodyPart.TORSO.ordinal()].getPosition().y);

            /*if(_isTouchingGround) {
                //if(Math.abs(_body[BodyPart.TORSO.ordinal()].getLinearVelocity().y) <= 0.1f) {
                if(_body[BodyPart.FEET.ordinal()].)
                    _isJumping = false;
                }
            }*/
        }

        if(_sprite != null) {
            _sprite.setPosition(getX() - _sprite.getWidth() / 2f, getY() - _sprite.getHeight() / 2f);
            _sprite.draw(batch);
        }

        if(_jumpCooldown > 0) {
            _jumpCooldown -= Gdx.graphics.getDeltaTime();
        }
    }

    public void move(float direction) {
        //_body.applyForceToCenter(50f * direction, 0f, true);
       // _body[BodyPart.TORSO.ordinal()].applyLinearImpulse(new Vector2(direction / 10f, 0), _body[BodyPart.TORSO.ordinal()].getWorldCenter(), true);
        _body[BodyPart.TORSO.ordinal()].applyForceToCenter(new Vector2(direction * 600f, 0), true);
    }

    public void jump() {
        if(_contactListener.isTouchingGround() && _jumpCooldown <= 0f) {
            //_body.applyForceToCenter(0f, 1000f, true);
            _body[BodyPart.TORSO.ordinal()].applyLinearImpulse(new Vector2(0, 400f), _body[BodyPart.TORSO.ordinal()].getWorldCenter(), true);
            _jumpCooldown = 0.25f;
        }
    }
    public boolean isControllable() {
        return _isControllable;
    }

}
