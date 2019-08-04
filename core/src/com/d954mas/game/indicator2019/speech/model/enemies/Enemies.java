package com.d954mas.game.indicator2019.speech.model.enemies;

import com.d954mas.engine.utils.Cs;

import java.util.List;

public class Enemies {
    public static List<Enemy> enemyList;
    public static void init(){
        enemyList = Cs.of(
                new BatEnemy(20,7,9,5,7),
                new RedGhostEnemy(30,10,12,6,8),
                new SlugEnemy(30,12,15,4,8),
                new SpudiEnemy(18,12,15,6,8),
                new TrollEnemy(25,9,12,6,8));
    }

    public static void dispose(){
        enemyList.clear();
    }
}
