package com.d954mas.engine.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ColorDrawable extends TextureRegionDrawable {
    private Color tintColor;

   //public ColorDrawable(Color color){
     //  this(color, ResDebug.res.atlas_atlas.white);
  // }

    public ColorDrawable(Color tintColor,TextureRegion region) {
        super();
        this.tintColor = tintColor;
        this.setRegion(region);
    }

    public void changeColor(Color color) {
        this.tintColor = color;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        Color oldColor = batch.getColor();
        batch.setColor(tintColor);
        super.draw(batch, x, y, width, height);
        batch.setColor(oldColor);
    }

    public Color getColor() {
        return tintColor;
    }
}
