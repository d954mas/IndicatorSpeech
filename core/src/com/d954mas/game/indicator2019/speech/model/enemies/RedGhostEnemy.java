package com.d954mas.game.indicator2019.speech.model.enemies;

import com.generated.ResGame;

public class RedGhostEnemy extends Enemy {
    public RedGhostEnemy(int hp, int minAtk, int maxAtk,
                         int minShield, int maxShield) { super(ResGame.res.reg_ghost_atlas.sprite1,
            hp, minAtk, maxAtk, minShield, maxShield); }
}
