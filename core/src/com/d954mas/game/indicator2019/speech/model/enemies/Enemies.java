package com.d954mas.game.indicator2019.speech.model.enemies;

import com.d954mas.engine.utils.Cs;

import java.util.List;

public class Enemies {
    public static List<Enemy> enemyList;
    public static void init(){
        enemyList = Cs.of(new BatEnemy(),
                new RedGhostEnemy(), new SlugEnemy(),new SpudiEnemy(),new TrollEnemy());
    }

    public static void dispose(){
        enemyList.clear();
    }
}
