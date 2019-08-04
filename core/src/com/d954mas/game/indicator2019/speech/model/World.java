package com.d954mas.game.indicator2019.speech.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.d954mas.engine.services.Services;
import com.d954mas.engine.utils.Cs;
import com.d954mas.game.indicator2019.speech.model.enemies.Enemies;
import com.d954mas.game.indicator2019.speech.model.enemies.Enemy;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class World {
    private static String TAG = "World";
    private static World instance;

    public static void init() {
        instance = new World();
    }

    public static World get(){ return instance; }

    public static void dispose(){
        instance = null;
    }

    public LuceneMorphology luceneMorph;
    public final List<MagicWord> allWords;
    public final List<MagicWord> handsWords;
    public final List<MagicWord> removedWords;
    public Enemy currentEnemy;
    public int currentEnemyIdx;

    enum  States {PLAYER_TURN,ENEMY_TURN};

    public States state;

    private World() {
        try {
            luceneMorph = new RussianLuceneMorphology();
        } catch (IOException e) {
            e.printStackTrace();
        }
        allWords = new ArrayList<>();
        handsWords = new ArrayList<>();
        removedWords = new ArrayList<>();
        state = States.PLAYER_TURN;
    }

    void setStartWords(){
        allWords.addAll(Cs.of(MagicWords.attack,MagicWords.attack,MagicWords.defence,
                MagicWords.defence,MagicWords.fire,MagicWords.ice));
    }

    public void prepareBattle(){
        handsWords.clear();
        removedWords.clear();
        handsWords.addAll(allWords);
        Collections.shuffle(handsWords);
        state = States.PLAYER_TURN;
        currentEnemyIdx = 0;
        currentEnemy = Enemies.enemyList.get(currentEnemyIdx);
    }

    public void nextEnemyDebug(){
        currentEnemyIdx = currentEnemyIdx + 1;
        currentEnemyIdx = Math.max(0, Math.min(Enemies.enemyList.size()-1, currentEnemyIdx));
        currentEnemy = Enemies.enemyList.get(currentEnemyIdx);
    }

    public void prevEnemyDebug(){
        currentEnemyIdx = currentEnemyIdx -1;
        currentEnemyIdx = Math.max(0, Math.min(Enemies.enemyList.size(), currentEnemyIdx));
        currentEnemy = Enemies.enemyList.get(currentEnemyIdx);
    }

    public boolean playWords(List<MagicWord> words){
        if(words.size()>1 && state == States.PLAYER_TURN) {
            for (MagicWord word : words) {
                if (!handsWords.remove(word)) {
                    Gdx.app.log(TAG, "can't play word not from hand");
                }
                removedWords.add(word);
            }
            state = States.ENEMY_TURN;
            check(100);
            Services.get(SpeechService.class).recognitionStop();
            Timer.instance().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    state = States.PLAYER_TURN;
                }
            }, 3f);
            return true;
        }
        return  false;
    }

    //Проверка на то что в руке есть карты
    public void check(int number){
        if (handsWords.size()<number){
            handsWords.addAll(removedWords);
            removedWords.clear();
        }

    }




}
