package com.mygdx.ultg.game;

public class PhysicsUtility {
    public static float PIXELS_PER_METER = 32f;

    public static float convertPixelsToMeters(float value) {
        return value / PIXELS_PER_METER;
    }
    public static float convertMetersToPixels(float value) {
        return value * PIXELS_PER_METER;
    }
}
