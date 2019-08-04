package com.d954mas.game.indicator2019.speech.model.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
    public final Animation<TextureRegion> animation;
    public Enemy(Animation<TextureRegion> animation){
        this.animation = animation;
    }

    public void reset(){

    }


}
