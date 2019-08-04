package com.d954mas.game.indicator2019.speech.screnes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Timer;
import com.d954mas.engine.services.Services;
import com.d954mas.game.indicator2019.speech.model.MagicWord;
import com.d954mas.game.indicator2019.speech.model.World;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.game.indicator2019.speech.services.impl.SpeechServiceDefault;
import com.generated.ResDebug;

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
    public GameUI(Stage stage){
        debugWords = new ArrayList<>();
        labels = new ArrayList<>();
        root = new Group();
        wordsTable = new Table();
        root.addActor(wordsTable);
        stage.addActor(root);
        updateWords();
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
                    lbl.addAction(Actions.delay(delay,Actions.alpha(0,0.4f)));
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
                       lbl.getColor().a = 0;
                       lbl.addAction(Actions.sequence(Actions.delay(delay),Actions.alpha(1,0.3f)));
                       delay = delay + 0.1f;
                   }
                }
            }, delay);
    }


    public void updateWords(){
        wordsTable.reset();
        wordsTable.remove();
        wordsTable = new Table();
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
            wordsTable.add(lbl).space(40).row();
        }
        wordsTable.setPosition(150,1000-wordsTable.getPrefHeight());
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
