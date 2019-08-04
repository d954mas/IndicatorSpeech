package com.d954mas.game.indicator2019.speech.screnes.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.d954mas.engine.services.Services;
import com.d954mas.game.indicator2019.speech.model.MagicWord;
import com.d954mas.game.indicator2019.speech.model.World;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.game.indicator2019.speech.services.impl.SpeechServiceDefault;
import com.generated.ResDebug;

import java.util.ArrayList;
import java.util.List;

public class GameUI {
    private Stage stage;
    private Group root;
    private Table wordsTable;
    private List<MagicWord> debugWords;
    public GameUI(Stage stage){
        debugWords = new ArrayList<>();
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

    public void updateWords(){
        wordsTable.reset();
        wordsTable.remove();
        wordsTable = new Table();
        root.addActor(wordsTable);
        for(MagicWord word:World.get().handsWords){
            Label lbl = new Label(word.mainWord,ResDebug.res.uiskin_json);
            lbl.setTouchable(Touchable.enabled);
            lbl.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    playWordDebug(word);
                }
            });
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
