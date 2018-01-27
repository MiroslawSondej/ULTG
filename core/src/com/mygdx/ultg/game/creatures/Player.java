package com.mygdx.ultg.game.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.ultg.game.PhysicsUtility;

public class Player extends Actor {

    private enum BodyPart {
      HEAD ,
      TORSO,
      FEET
    }

    enum PlayerDirection {
        LEFT,
        RIGHT
    }

    PlayerDirection _currentPlayerDirection;
    boolean _playerDirectionChanged = false;

    World _world;
    Body _body[];

    boolean _isControllable;
    Sprite _sprite;

    PlayerContactListener _contactListener;
    float _jumpCooldown = 0f;

    // Animations
    Animation<TextureRegion> _idleAnimation;
    Animation<TextureRegion> _walkAnimation;
    Animation<TextureRegion> _jumpAnimation;

    float _animationStateTime;
    //------------------------------
    boolean _isWalking;
    boolean _isWalkStopping;
    boolean _isTakingDamage;
    boolean _isPunching;

    // JUMP:
    enum JumpPhase {
        JUMP,
        PEAK,
        FALL,
        LAND
    }
    boolean _isJumping;
    JumpPhase _jumpPhase;
    //------------------------------
    public Player(World world, float x, float y) {
        _world = world;
        //---------[Sprites & animations]-----------
        _sprite = new Sprite( new Texture("gameworld/sprites/creatures/player.png"));
        _sprite.setScale(1/ PhysicsUtility.PIXELS_PER_METER);

        setupPlayerAnimations();
        //-----------[Startup variables]------------
        _isControllable = true;
        _isWalking = false;
        _isWalkStopping = false;
        _isJumping = false;
        _isTakingDamage = false;
        _isPunching = false;
        _currentPlayerDirection = PlayerDirection.RIGHT;
        //----------------[Physics]-----------------
        setupPlayerBody(x, y);
        _contactListener = new PlayerContactListener(this); // Physical events listener
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
    public boolean isControllable() {
        return _isControllable;
    }
    //------------------------------
    @Override
    public void act(float delta) {
        updateAnimation();

        // ---------------------------------------------
        // Inputs
        //
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

        // ---------------------------------------------
        // Perform player actions
        //
        if(dx != 0f) {
            move(dx);
            _isWalkStopping = false;
        }
        else {
            if(_isWalking) {
                _isWalkStopping = true;
            }
        }
        if(djump) jump();
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (_body != null) {
            setPosition(_body[BodyPart.TORSO.ordinal()].getPosition().x, _body[BodyPart.TORSO.ordinal()].getPosition().y);
        }

        if (_sprite != null) {
            _animationStateTime += Gdx.graphics.getDeltaTime();
            TextureRegion animationFrame =  _idleAnimation.getKeyFrame(_animationStateTime, true);

            if(_isJumping) {
                switch(_jumpPhase) {
                    case JUMP:
                        animationFrame = _jumpAnimation.getKeyFrames()[0];
                        break;
                    case PEAK:
                        animationFrame = _jumpAnimation.getKeyFrames()[1];
                        break;
                    case FALL:
                        animationFrame = _jumpAnimation.getKeyFrames()[2];
                        break;
                    case LAND:
                        animationFrame = _idleAnimation.getKeyFrames()[0];
                        break;
                }
            }
            else if(_isTakingDamage) {

            }
            else if(_isPunching) {

            }
            else if(_isWalking) {
                animationFrame = _walkAnimation.getKeyFrame(_animationStateTime, true);
            }
            else {
                animationFrame = _idleAnimation.getKeyFrame(_animationStateTime, true);
            }

            _sprite.set(new Sprite(animationFrame));
            _sprite.setScale(1f/PhysicsUtility.PIXELS_PER_METER);
            _sprite.setPosition(getX() - _sprite.getWidth() / 2f, getY() - _sprite.getHeight() / 2f);

            if(_currentPlayerDirection == PlayerDirection.LEFT) {
                _sprite.setFlip(true, false);
            }

            _sprite.draw(batch);

            if (_jumpCooldown > 0) {
                _jumpCooldown -= Gdx.graphics.getDeltaTime();
            }
        }
    }
    //-------------------------------
    void move(float direction) {
        if(_contactListener.isTouchingGround() && !_isJumping && !_isPunching && !_isTakingDamage) {
            if (direction > 0) {
                if (_currentPlayerDirection == PlayerDirection.LEFT) {
                    _playerDirectionChanged = true;
                }
                _currentPlayerDirection = PlayerDirection.RIGHT;
            } else if (direction < 0 && !_sprite.isFlipX()) {
                if (_currentPlayerDirection == PlayerDirection.RIGHT) {
                    _playerDirectionChanged = true;
                }
                _currentPlayerDirection = PlayerDirection.LEFT;
            }

            if (_playerDirectionChanged) {
                _playerDirectionChanged = false;
            }

            //if(new Vector2(_body[BodyPart.TORSO.ordinal()].getLinearVelocity().x, 0f).len() < 10f) {
            if(Math.abs(_body[BodyPart.TORSO.ordinal()].getLinearVelocity().x) < 10f) {
                _body[BodyPart.TORSO.ordinal()].applyForceToCenter(1200f * direction, 0f, true);
            }

            _isWalking = true;
        }
    }
    void jump() {
        if(_contactListener.isTouchingGround() && _jumpCooldown <= 0f) {
            _body[BodyPart.TORSO.ordinal()].applyLinearImpulse(new Vector2(0, 390f), _body[BodyPart.TORSO.ordinal()].getWorldCenter(), true);
            _jumpCooldown = 0.25f;
            //_currentAnimationPhase = PlayerAnimationPhase.JUMPING;
            _isJumping = true;
            _jumpPhase = JumpPhase.JUMP;
        }
    }
    void setupPlayerBody(float x, float y) {
        float w = PhysicsUtility.convertPixelsToMeters(32);
        float h = PhysicsUtility.convertPixelsToMeters(64);

        _body = new Body[3];

        //Head
        BodyDef headBodyDef = new BodyDef();
        headBodyDef.type = BodyDef.BodyType.DynamicBody;
        headBodyDef.position.set(x, y);
        headBodyDef.fixedRotation = true;

        _body[BodyPart.HEAD.ordinal()] = _world.createBody(headBodyDef);
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

        _body[BodyPart.TORSO.ordinal()] = _world.createBody(headBodyDef);
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

        _body[BodyPart.FEET.ordinal()] = _world.createBody(feetBodyDef);
        CircleShape feetShape = new CircleShape();
        feetShape.setRadius(w / 4);

        FixtureDef feetFixtureDef = new FixtureDef();
        feetFixtureDef.shape = feetShape;
        feetFixtureDef.density = 400f;
        feetFixtureDef.friction = 0.9f;
        Fixture feetFixture  = _body[BodyPart.FEET.ordinal()].createFixture(feetFixtureDef);

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
    }
    void setupPlayerAnimations() {
        _animationStateTime = 0f;

        // Load animations
        Texture playerTexture = new Texture("gameworld/sprites/creatures/player.png");

        //---------
        // --> Idle
        _idleAnimation = new Animation<TextureRegion>(1f, new TextureRegion(playerTexture, 0, 0, 32, 64));

        //---------
        // --> Walk
        TextureRegion[] walkAnimationTexture = new TextureRegion[6];
        for(int i = 0; i < 6; i++) {
            walkAnimationTexture[i] = new TextureRegion(playerTexture, 32 + i*32, 0, 32, 64);
        }
        _walkAnimation = new Animation<TextureRegion>(1f/12f, walkAnimationTexture);
        _walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        //---------
        // --> Jump
        TextureRegion[] jumpAnimationTexture = new TextureRegion[3];
        for(int i = 0; i < 3; i++) {
            jumpAnimationTexture[i] = new TextureRegion(playerTexture, (7*32) + (i * 32), 64, 32, 64);
        }
        _jumpAnimation= new Animation<TextureRegion>(1f, jumpAnimationTexture);
        _jumpAnimation.setPlayMode(Animation.PlayMode.NORMAL);

    }
    void updateAnimation() {
        if(_isJumping) {
            switch(_jumpPhase) {
                case JUMP: {
                    Vector2 playerVelocity = getTorso().getLinearVelocity();
                    if(playerVelocity.y > 0f && playerVelocity.y < 1f) {
                        _jumpPhase = JumpPhase.PEAK;
                    }
                    break;
                }
                case PEAK: {
                    Vector2 playerVelocity = getTorso().getLinearVelocity();
                    if(playerVelocity.y < -1f) {
                        _jumpPhase = JumpPhase.FALL;
                    }
                    break;
                }
                case FALL: {
                    if(_contactListener.isTouchingGround()) {
                        _jumpPhase = JumpPhase.LAND;
                    }
                    break;
                }
                case LAND: {
                    _isJumping = false;
                    break;
                }
            }
        }
        if(_isWalking && _isWalkStopping) {
            Vector2 playerVelocity = getTorso().getLinearVelocity();

            if(_currentPlayerDirection == PlayerDirection.RIGHT && playerVelocity.x > 0 && playerVelocity.x < 1f) {
                getFeet().setLinearVelocity(0f, getFeet().getLinearVelocity().y);
                _isWalking = false;
            }
            else if(_currentPlayerDirection == PlayerDirection.LEFT && playerVelocity.x < 0 && playerVelocity.x > -1f) {
                getFeet().setLinearVelocity(0f, getFeet().getLinearVelocity().y);
                _isWalking = false;
            }

        }
    }

}
