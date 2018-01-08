package com.mygdx.ultg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import java.util.ArrayList;

public class ParallaxSkyboxController {

    ArrayList<ParallaxSkybox> _skybox;

    public ParallaxSkyboxController() {
        _skybox = new ArrayList<ParallaxSkybox>();
    }

    public void addParallaxSkybox(ParallaxSkybox parallaxSkybox) {
        _skybox.add(parallaxSkybox);
    }

    public void update(float x, float y) {
        for(int i = 0; i < _skybox.size(); i++) {
            _skybox.get(i).update(x, y);
        }
    }

    public void render(Batch batch) {
        for(int i = 0; i < _skybox.size(); i++) {
            _skybox.get(i).render(batch);
        }
    }
}
