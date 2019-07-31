package com.d954mas.engine.alias;

import com.badlogic.gdx.Gdx;

public class Display {
    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }
    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }
    public static float getDensity() {
        return Gdx.graphics.getDensity();
    }
}
