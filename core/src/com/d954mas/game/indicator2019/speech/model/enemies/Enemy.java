package com.d954mas.game.indicator2019.speech.model.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
    public final Animation<TextureRegion> animation;
    public final int maxHp;
    public int hp;
    public final int minAtk, maxAtk;
    public final int minShield, maxShield;
    public Enemy(Animation<TextureRegion> animation,int hp, int minAtk, int maxAtk,
                 int minShield, int maxShield){
        this.animation = animation;
        this.hp = hp;
        this.maxHp = hp;
        this.minAtk = minAtk;
        this.maxAtk = maxAtk;
        this.minShield = minShield;
        this.maxShield = maxShield;
    }

    public void reset(){

    }


}
