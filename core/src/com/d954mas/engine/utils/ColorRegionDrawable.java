package com.d954mas.engine.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ColorRegionDrawable extends TextureRegionDrawable {

    private Color tintColor;
    public ColorRegionDrawable(Color tintColor, TextureRegion textureRegion) {
        super();
        this.tintColor = tintColor;
        this.setRegion(textureRegion);
    }

    public void setTintColor(Color color) {
        this.tintColor = color;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        Color oldColor = batch.getColor();
       // float tintOld=tintColor.a;
      //  tintColor.a*=oldColor.a;
        batch.setColor(tintColor);
        super.draw(batch, x, y, width, height);
        //tintColor.a=tintOld;
        batch.setColor(oldColor);
    }

    public Color getColor() {
        return tintColor;
    }
}
