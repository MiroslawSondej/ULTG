package com.mygdx.ultg.game.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.ultg.game.ItemTexturesManager;
import com.mygdx.ultg.game.PhysicsUtility;
import com.mygdx.ultg.game.creatures.Player;

import java.util.HashMap;

public class Paper extends Actor {

    Sprite _sprite;
    World _world;
    Body _body;

    boolean isEnabled = true;

    public Paper(World world, float x, float y) {
        _sprite = new Sprite(ItemTexturesManager.getInstance().getTextureForItem(ItemTexturesManager.Item.PAPER));
        _sprite.setScale(1/ PhysicsUtility.PIXELS_PER_METER);
        _world = world;

        // Setup physical object
        float w = PhysicsUtility.convertPixelsToMeters(_sprite.getWidth());
        float h = PhysicsUtility.convertPixelsToMeters(_sprite.getHeight());

        //Head
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;

        _body = world.createBody(bodyDef);

        HashMap<String, Object> userData = new HashMap<String, Object>();
        userData.put("name", "paper");
        userData.put("object", this);
        _body.setUserData(userData);

        CircleShape shape = new CircleShape();
        shape.setRadius(w / 3f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;
        Fixture fixture = _body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(_body != null) {
            setPosition(_body.getPosition().x, _body.getPosition().y);
        }

        if(_sprite != null) {
            _sprite.setPosition(getX() - _sprite.getWidth() / 2f, getY() - _sprite.getHeight() / 2f);
            _sprite.draw(batch);
        }
    }

    public void onPickUp(Player player) {
        if(isEnabled) {
            this.remove();
            isEnabled = false;
        }
    }
}
