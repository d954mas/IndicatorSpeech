package com.d954mas.game.indicator2019.speech.screnes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.d954mas.engine.services.Services;
import com.d954mas.game.indicator2019.speech.model.MagicWord;
import com.d954mas.game.indicator2019.speech.model.World;
import com.d954mas.game.indicator2019.speech.model.effects.Effect;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.game.indicator2019.speech.services.impl.SpeechServiceDefault;
import com.d954mas.utils.Constants;
import com.generated.ResDebug;
import com.generated.ResUi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GameUI {
    private Stage stage;
    private Group root;
    private Table wordsTable;
    private List<MagicWord> debugWords;
    private List<Label> labels;

    private Label enemyHp;
    private Label enemyDefence;
    private Label heroHp;
    private Label heroDefence;
    private Actor enemyIntentDefence;
    private Actor enemyIntentAttack;
    private Label enemyIntentText;
    private Label effectsLbl;

    public GameUI(Stage stage){
        debugWords = new ArrayList<>();
        labels = new ArrayList<>();
        root = new Group();
        wordsTable = new Table();
        root.addActor(wordsTable);
        stage.addActor(root);
        stage.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER){
                    playWordsDebug();
                    return true;
                }
                return false;
            }
        });



        Actor listWordBg = new Image(ResUi.res.atlas_atlas.plashka_spisok);
        ResUi.res.atlas_atlas.plashka_spisok.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);


        Group bgEnemyGroup = new Group();
        Actor bgEnemy = new Image(ResUi.res.atlas_atlas.mosnster_hp_def);
        bgEnemyGroup.setPosition(Constants.GAME_WIDTH/2-bgEnemy.getWidth()/2,40);
        ResUi.res.atlas_atlas.mosnster_hp_def.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        enemyHp = new Label("HP:", ResDebug.res.uiskin_json,"big");
        enemyDefence = new Label("Def:", ResDebug.res.uiskin_json,"big");
        Table enemyData = new Table();
        enemyData.setSize(bgEnemy.getWidth(),bgEnemy.getHeight());
        enemyData.add(enemyHp).expandX();
        enemyData.add(enemyDefence).expandX();
        enemyData.setPosition(0,80);

        //bgEnemyGroup.addActor(bgEnemy);
        bgEnemyGroup.addActor(enemyData);


        Group bgHeroHpGroup = new Group();
        Actor bgHeroHp = new Image(ResUi.res.atlas_atlas.plashka_hp_def_player);
        bgHeroHpGroup.setPosition(Constants.GAME_WIDTH-bgHeroHp.getWidth()-40,100);
        ResUi.res.atlas_atlas.plashka_hp_def_player.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        heroHp = new Label("HP:", ResDebug.res.uiskin_json,"big");
        heroDefence = new Label("Def:", ResDebug.res.uiskin_json,"big");
        Label lbl = new Label("Hero:", ResDebug.res.uiskin_json,"big");


        Table heroData = new Table();
        heroData.setSize(bgHeroHp.getWidth(),bgHeroHp.getHeight());
        heroData.add(lbl).colspan(2).row();
        heroData.add(heroHp).expandX();
        heroData.add(heroDefence).expandX();
        heroData.setPosition(0,1080-300);
       // bgHeroHpGroup.addActor(bgHeroHp);
        bgHeroHpGroup.addActor(heroData);

        Group bgHeroManaGroup = new Group();
        Actor bgHeroMana = new Image(ResUi.res.atlas_atlas.plashka_mp);
        bgHeroManaGroup.setPosition(Constants.GAME_WIDTH-bgHeroMana.getWidth()-40,260);
        ResUi.res.atlas_atlas.plashka_mp.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Label mpText = new Label("MP", ResDebug.res.uiskin_json);
        mpText.setPosition(bgHeroMana.getWidth()/2-mpText.getWidth()/2,80);
        //bgHeroManaGroup.addActor(bgHeroMana);
       // bgHeroManaGroup.addActor(mpText);

        root.addActor(bgEnemyGroup);
        root.addActor(bgHeroHpGroup);
        root.addActor(bgHeroManaGroup);
        root.addActor(listWordBg);

        Group group = new Group();
        enemyIntentAttack = new Image(ResUi.res.atlas_atlas.monster_attack_icon);
        enemyIntentAttack.setScale(2);
        enemyIntentDefence = new Image(ResUi.res.atlas_atlas.monster_def_icon);
        enemyIntentDefence.setScale(2);
        enemyIntentText = new Label("12",ResDebug.res.uiskin_json,"big");
        enemyIntentText.setPosition(-30,55);
        group.setPosition(Constants.GAME_WIDTH/2-enemyIntentAttack.getWidth()/2,800);
        group.addActor(enemyIntentAttack);
        group.addActor(enemyIntentDefence);
        group.addActor(enemyIntentText);

        root.addActor(group);

        effectsLbl = new Label("Effects:",ResDebug.res.uiskin_json);
        root.addActor(effectsLbl);



        Actor debugScene = new Image(ResUi.res.atlas_atlas.ui);
        debugScene.getColor().a = 0.44f;
       // root.addActor(debugScene);

        updateWords();

        World.get().stage = stage;
    }

    private void playWordsDebug(){
        if (Services.get(SpeechService.class) instanceof SpeechServiceDefault){
            SpeechServiceDefault speechServiceDefault = (SpeechServiceDefault) Services.get(SpeechService.class);
            if(debugWords.size()>1){
                String magicString = "";
                for(MagicWord magicWord:debugWords){
                    magicString += magicWord.mainWord +" ";
                }
                magicString = magicString.trim();
                speechServiceDefault.speechEventFinal(magicString);
                debugWords.clear();
            }
        }
    }

    public void changeWordsVisual(List<MagicWord> speechWords){
        List<Label> labelsCopy = new ArrayList<>(labels);
        for(MagicWord magicWord:speechWords){
            Iterator<Label> i = labelsCopy.iterator();
            while (i.hasNext()) {
                Label lbl = i.next(); // must be called before you can call i.remove()
                MagicWord word = (MagicWord) lbl.getUserObject();
                if(word == magicWord){
                    //небыло анимации
                    if(lbl.getColor().equals(Color.WHITE)){
                        lbl.addAction(Actions.color(new Color(1,0,0,1),0.5f));
                    }
                    i.remove();
                    break;
                }
            }
        }
    }

    public void removeTableRow(int row,Table table) {
        Gdx.app.log("GameUI","remove row:" + row);
        Array<Cell> cells = table.getCells();
        //Remove contents of first row
        cells.get(row).clearActor();
        //Copy all cells up one row
        for (int i = row; i < cells.size - 1; i++)
            cells.set(i, cells.get(i + 1));

        cells.removeIndex(cells.size - 1);
    }

    public void animateUseAndNewWordsAppear(List<MagicWord> speechWords){
        List<Label> labelsCopy = new ArrayList<>(labels);
        float delay = 0f;
        for(MagicWord magicWord:speechWords){
            ListIterator<Label> i = labelsCopy.listIterator();
            while (i.hasNext()) {
                Label lbl = i.next(); // must be called before you can call i.remove()
                MagicWord word = (MagicWord) lbl.getUserObject();
                lbl.clearActions();
                if(word == magicWord){
                    lbl.addAction(Actions.delay(delay,Actions.color(new Color(1,1,1,1),0.4f)));
                    delay = delay + 0.15f;
                    i.remove();
                    break;
                }
            }
        }
        delay = delay + 0.1f;
        Timer.instance().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    int currentSize = labelsCopy.size();
                    updateWords();
                   int newSize = labels.size();
                   float delay = 0;
                   for(int i = currentSize;i<newSize;i++){
                       Label lbl = labels.get(i);
                       lbl.addAction(Actions.sequence(Actions.delay(delay),Actions.color(new Color(1,1,1,1))));
                       delay = delay + 0.1f;
                   }
                }
            }, delay);
    }

    public void update(float dt){
        enemyDefence.setText("Def:" + World.get().currentEnemy.currentDefence);
        enemyHp.setText("Hp:" + World.get().currentEnemy.hp);
        heroHp.setText("Hp:" + World.get().heroHp);
        heroDefence.setText("Def:" + World.get().heroDefence);
        if(World.get().currentEnemy.attack){
            enemyIntentText.setText(World.get().currentEnemy.attackValue);
            enemyIntentDefence.setVisible(false);
            enemyIntentAttack.setVisible(true);
        }else{
            enemyIntentDefence.setVisible(true);
            enemyIntentAttack.setVisible(false);
            enemyIntentText.setText(World.get().currentEnemy.defenceValue);
        }


        String stringEffectsAttack = "";
        String stringEffectsDefence = "";
        for(Effect effect:World.get().effects){
            stringEffectsAttack += effect.toString() + "\n";
        }
        effectsLbl.setPosition(1400,800);
        effectsLbl.setAlignment(Align.top);
        effectsLbl.setText("Эффекты:\n" +stringEffectsAttack);

        root.addActor(effectsLbl);
    }

    public void updateWords(){
        wordsTable.reset();
        wordsTable.remove();
        wordsTable = new Table();
        wordsTable.setPosition(40,0);
        root.addActor(wordsTable);
        labels.clear();
        for(MagicWord word:World.get().handsWords){
            Label lbl = new Label(word.mainWord,ResDebug.res.uiskin_json);
            lbl.setTouchable(Touchable.enabled);
            lbl.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playWordDebug(word);
                }
            });
            lbl.setUserObject(word);
            labels.add(lbl);
            wordsTable.add(lbl).space(43).row();
        }
        wordsTable.setPosition(260,1080-wordsTable.getPrefHeight()+145);
    }

    private void playWordDebug(MagicWord word){
        if (Services.get(SpeechService.class) instanceof SpeechServiceDefault){
            SpeechServiceDefault speechServiceDefault = (SpeechServiceDefault) Services.get(SpeechService.class);
            List<MagicWord> availableWords = new ArrayList<>(World.get().handsWords);
            for(MagicWord magicWord:debugWords){
                availableWords.remove(magicWord);
            }
            if(debugWords.size()<3 && availableWords.contains(word)){
                debugWords.add(word);
                String magicString = "";
                for(MagicWord magicWord:debugWords){
                    magicString += magicWord.mainWord +" ";
                }
                magicString = magicString.trim();
                speechServiceDefault.speechEventPart(magicString);
            }
        }
    }

    private void dispose(){
        root.remove();
        debugWords.clear();
    }
}
