package com.d954mas.game.indicator2019.speech.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.d954mas.engine.services.Services;
import com.d954mas.engine.utils.Cs;
import com.d954mas.game.indicator2019.speech.model.effects.BuffEffect;
import com.d954mas.game.indicator2019.speech.model.effects.DebuffEffect;
import com.d954mas.game.indicator2019.speech.model.effects.Effect;
import com.d954mas.game.indicator2019.speech.model.effects.Effects;
import com.d954mas.game.indicator2019.speech.model.enemies.BatEnemy;
import com.d954mas.game.indicator2019.speech.model.enemies.Enemies;
import com.d954mas.game.indicator2019.speech.model.enemies.Enemy;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.game.indicator2019.speech.sounds.Sounds;
import com.sun.xml.internal.bind.v2.TODO;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

    public final List<Effect> effects;
    public Enemy currentEnemy;
    public int currentEnemyIdx;
    public int heroHp;
    public int heroMaxHp;
    public int heroDefence;
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
        effects = new ArrayList<>();
    }

    void setStartWords(){
        allWords.addAll(Cs.of(
                MagicWords.attack,
                MagicWords.defence,
                MagicWords.fire,
                MagicWords.ice));
    }

    public void prepareBattle(){
        heroMaxHp = 40;
        heroHp = heroMaxHp;
        heroDefence = 0;
        handsWords.clear();
        removedWords.clear();
        handsWords.addAll(allWords);
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
        currentEnemyIdx = currentEnemyIdx - 1;
        currentEnemyIdx = Math.max(0, Math.min(Enemies.enemyList.size(), currentEnemyIdx));
        currentEnemy = Enemies.enemyList.get(currentEnemyIdx);
    }

    public boolean isAttack(List<MagicWord> words){
        return words.contains(MagicWords.attack);
    }

    private void attackEnemy(int damage){
        if(effects.contains(Effects.StormAttackEffect)){
            //ignore defence
        }
        if(effects.contains(Effects.LoveAttackEffect)){
            //reduce enemy defence
        }
        currentEnemy.hp = Math.max(currentEnemy.hp - damage,0);
        if(effects.contains(Effects.LoveAttackEffect)){
            //add hp
        }
        if(currentEnemy.hp<=0){
            Sounds.deaths[currentEnemyIdx].play();
            //TODO: timer 2s
            nextEnemyDebug();
        }
    }

    private void attackHero(int damage){
        if(effects.contains(Effects.LieDefenceEffect)){
            //reduce attack
            damage = Math.max(0,damage-4);
        }
        heroHp = Math.max(0,heroHp=damage);
    }

    private void defenceHero(int defence){
        heroDefence = heroDefence + defence;
    }

    private void addEffect(Effect effect){
        if(effects.contains(effect)){
            effects.remove(effect);
        }
        effect.reset();
        effects.add(effect);
    }

    private void removeBuffEffects(){
        Iterator<Effect> effectIterator = effects.iterator();
        while (effectIterator.hasNext()){
            Effect effect = effectIterator.next();
            if(effect instanceof BuffEffect){
                effect.turns = effect.turns - 1;
                if(effect.turns<=0){
                    effectIterator.remove();
                }
            }
        }
    }

    private void removeDebuffEffects(){
        Iterator<Effect> effectIterator = effects.iterator();
        while (effectIterator.hasNext()){
            Effect effect = effectIterator.next();
            if(effect instanceof DebuffEffect){
                effect.turns = effect.turns - 1;
                if(effect.turns<=0){
                    effectIterator.remove();
                }
            }
        }
    }

    public boolean playWords(List<MagicWord> words){
        if(words.size()>1 && state == States.PLAYER_TURN) {
            for (MagicWord word : words) {
               // if (!handsWords.remove(word)) {
                 //   Gdx.app.log(TAG, "can't play word not from hand");
              //  }
              //  removedWords.add(word);
            }
            state = States.ENEMY_TURN;
            check(100);
            Services.get(SpeechService.class).recognitionStop();
            removeBuffEffects();
            if(words.contains(Effects.StormDefenceEffect)){
                //player add shield 4
            }
            if(isAttack(words)){
                int damage = 5;
                if(words.contains(MagicWords.strength)){
                    damage = damage + 5;
                }
                if(words.contains(MagicWords.fire)){ addEffect(Effects.FireAttackEffect);}
                if(words.contains(MagicWords.storm)){ addEffect(Effects.StormAttackEffect);}
                if(words.contains(MagicWords.love)){ addEffect(Effects.LoveAttackEffect);}
                if(words.contains(MagicWords.ice)){ addEffect(Effects.IceAttackEffect);}
                if(words.contains(MagicWords.lie)){ addEffect(Effects.LieAttackEffect);}
                attackEnemy(damage);
            }else{
                int defence = 5;
                if(words.contains(MagicWords.strength)){
                    defence = defence + 5;
                }
                if(words.contains(MagicWords.fire)){ addEffect(Effects.FireDefenceEffect);}
                if(words.contains(MagicWords.storm)){ addEffect(Effects.StormDefenceEffect);}
                if(words.contains(MagicWords.love)){ addEffect(Effects.LoveDefenceEffect);}
                if(words.contains(MagicWords.ice)){ addEffect(Effects.IceDefenceEffect);}
                if(words.contains(MagicWords.lie)){ addEffect(Effects.LieDefenceEffect);}
                defenceHero(defence);
            }


            Timer.instance().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    removeDebuffEffects();
                    boolean enemyAttack = true;
                    if(effects.contains(Effects.IceAttackEffect) || effects.contains(Effects.IceDefenceEffect)){

                    }else {
                        if (enemyAttack) {
                            attackHero(5);
                        }

                        if (effects.contains(Effects.FireAttackEffect)) {
                            attackEnemy(3);
                        }
                    }
                    currentEnemy.nextTurn();
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
          //  handsWords.addAll(removedWords);
          //  removedWords.clear();
        }

    }




}
