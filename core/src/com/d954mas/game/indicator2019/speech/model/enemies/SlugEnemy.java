package com.d954mas.game.indicator2019.speech.model.enemies;

import com.generated.ResGame;

public class SlugEnemy extends Enemy {
    public SlugEnemy(int hp, int minAtk, int maxAtk,
                     int minShield, int maxShield) { super(ResGame.res.slug_atlas.sprite1,
            hp, minAtk, maxAtk, minShield, maxShield); }
}
