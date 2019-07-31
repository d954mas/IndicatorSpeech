package com.d954mas.engine.utils.ui;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;


public class UIUtils {
    //TODO compare values
    public static Array<TextureAtlas.AtlasRegion> getRegionsByPrefix(String prefix, TextureAtlas atlas) {
        Array<TextureAtlas.AtlasRegion> toRet = new Array<>();
        for (TextureAtlas.AtlasRegion atlasRegion : atlas.getRegions()) {
            if (atlasRegion.name.startsWith(prefix)) {
                toRet.add(atlasRegion);
            }
        }
       // toRet.sort((o1, o2) -> -Objects.compare(o1.name, o2.name));
        return toRet;
    }


    /**
     * Resizes component proportionally by its new width
     */
    public static Actor setSizeByWidth(Actor actor, float newWidth) {
        float scaleHeight=actor.getHeight()/actor.getWidth();
        actor.setSize(newWidth,newWidth*scaleHeight);
        return actor;
    }

    /**
     * Resizes component proportionally by its new height
     */
    public static Actor setSizeByHeight(Actor actor, float newHeight) {
        float scaleWidth= actor.getWidth() /  actor.getHeight();
        actor.setSize(newHeight*scaleWidth, newHeight);
        return actor;
    }

    /**
     * Resizes cell proportionally by its new height according to actor inside
     */
    public static Cell setSizeByWidth(Cell cell, float newWidth) {
        Actor actor=setSizeByWidth(cell.getActor(),newWidth);
        cell.width(actor.getWidth()).height(actor.getHeight());
        return cell;
    }

    /**
     * Resizes cell proportionally by its new height according to actor inside
     */
    public static Cell setSizeByHeight(Cell cell, float newHeight) {
        Actor actor=setSizeByHeight(cell.getActor(),newHeight);
        cell.width(actor.getWidth()).height(actor.getHeight());
        return cell;
    }


}
