package com.d954mas.game.indicator2019.speech.model.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
    public final Animation<TextureRegion> animation;
    public final int maxHp;
    public int hp;
    public final int minAtk, maxAtk;
    public final int minShield, maxShield;
    public boolean attack;
    public int attackValue;
    public int defenceValue;
    public int currentDefence;
    public Enemy(Animation<TextureRegion> animation,int hp, int minAtk, int maxAtk,
                 int minShield, int maxShield){
        this.animation = animation;
        this.hp = hp;
        this.maxHp = hp;
        this.minAtk = minAtk;
        this.maxAtk = maxAtk;
        this.minShield = minShield;
        this.maxShield = maxShield;
        nextTurn();
    }

    private int randomFromTo(int from, int to){
        double randomDouble = Math.random();
        randomDouble = randomDouble * to + from;
        return  (int) randomDouble;
    }

    public void nextTurn(){
        attack = Math.random()>0.5;
        if(attack){
            attackValue = randomFromTo(minAtk,maxAtk);
        }else{
            defenceValue = randomFromTo(minShield,maxShield);
        }
    }



    public void reset(){
        nextTurn();
    }


}
