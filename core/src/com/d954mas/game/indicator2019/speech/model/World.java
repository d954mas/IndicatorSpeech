package com.d954mas.game.indicator2019.speech.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
import com.d954mas.utils.Constants;
import com.generated.ResGame;
import com.generated.ResUi;

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
    public Stage stage;

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
                MagicWords.ice,
                MagicWords.lie,
                MagicWords.love,
                MagicWords.storm));
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
        effects.clear();
       // Sounds.spawns[currentEnemyIdx].play();
        //TODO: timer 2s
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
       // Sounds.attackeds[currentEnemyIdx].play();
        //TODO: timer 0.5s
        currentEnemy.hp = Math.max(currentEnemy.hp - damage,0);
        if(effects.contains(Effects.LoveAttackEffect)){
            //add hp
        }
        if(currentEnemy.hp<=0){
            //Sounds.deaths[currentEnemyIdx].play();
            //TODO: timer 2s
            nextEnemyDebug();
        }
    }

    private void attackHero(int damage){
        if(effects.contains(Effects.LieDefenceEffect)){
            //reduce attack
            damage = Math.max(0,damage-4);
        }
       // Sounds.attacks[currentEnemyIdx].play();
        //TODO: timer 1s
        heroHp = Math.max(0,heroHp-damage);
        Actor actor = new Image(ResGame.res.effect_atlas.vinetka_player_uron);
        actor.getColor().a = 1;
        actor.addAction(Actions.sequence(Actions.alpha(1),Actions.delay(0.5f),Actions.alpha(0),
                Actions.removeActor()));
        stage.addActor(actor);
    }

    private void defenceHero(int defence){
        heroDefence = heroDefence + defence;
        Actor actor = new Image(ResGame.res.effect_atlas.vinetka_player_def);
        actor.getColor().a = 1;
        actor.addAction(Actions.sequence(Actions.alpha(1),Actions.delay(0.5f),Actions.alpha(0),
                Actions.removeActor()));
        stage.addActor(actor);
    }

    private void defenceEnemy(int defence){

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

            float dt = 0;
            for (MagicWord word : words) {
                dt = dt + 0.3f;
                TextureRegion region = ResUi.res.atlas_atlas.empty;
                if(word==MagicWords.attack){
                    region = ResGame.res.effect_atlas.attack_effect;
                }else if(word == MagicWords.fire){
                    region = ResGame.res.effect_atlas.fire_effect;
                }else if(word == MagicWords.storm){
                    region = ResGame.res.effect_atlas.light_effect;
                }else if(word == MagicWords.ice){
                    region = ResGame.res.effect_atlas.ice_effect;
                }else if(word == MagicWords.lie){
                    region = ResGame.res.effect_atlas.obman_effect;
                }else if(word == MagicWords.love){
                    region = ResGame.res.effect_atlas.love_effect;
                }
                Actor actor = new Image(region);
                actor.setPosition(Constants.GAME_WIDTH/2-actor.getWidth()/2,Constants.GAME_HEIGHT/2-actor.getHeight()/2);
                actor.getColor().a = 0;
                actor.addAction(Actions.sequence(Actions.delay(dt),Actions.alpha(1,0.15f),Actions.delay(0.2f),Actions.alpha(0.1f),
                        Actions.removeActor()));
                stage.addActor(actor);
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
                    boolean enemyAttack = World.get().currentEnemy.attack;
                    if(effects.contains(Effects.IceAttackEffect) || effects.contains(Effects.IceDefenceEffect)){

                    }else {
                        if (enemyAttack) {
                            attackHero(World.get().currentEnemy.attackValue);
                        }else{
                            defenceEnemy(World.get().currentEnemy.defenceValue);
                        }

                        if (effects.contains(Effects.FireAttackEffect)) {
                            attackEnemy(3);
                        }
                    }
                    currentEnemy.nextTurn();
                    state = States.PLAYER_TURN;
                }
            }, 1f);
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
