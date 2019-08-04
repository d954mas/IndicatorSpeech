package com.d954mas.game.indicator2019.speech.model.enemies;

import com.generated.ResGame;

public class TrollEnemy extends Enemy {
    public TrollEnemy(int hp, int minAtk, int maxAtk,
                      int minShield, int maxShield) { super(ResGame.res.troll_atlas.sprite1,
            hp, minAtk, maxAtk, minShield, maxShield); }
}
