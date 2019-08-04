package com.d954mas.game.indicator2019.speech.model.enemies;

import com.generated.ResGame;

public class SpudiEnemy extends Enemy {
    public SpudiEnemy(int hp, int minAtk, int maxAtk,
                      int minShield, int maxShield) { super(ResGame.res.spudi_atlas.sprite1,
            hp, minAtk, maxAtk, minShield, maxShield); }
}
