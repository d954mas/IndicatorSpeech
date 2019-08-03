package com.d954mas.game.indicator2019.speech.screnes.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.generated.ResDebug;

import java.util.List;

public class GameUI {
    private Stage stage;
    private Group root;
    private Table wordsTable;
    private GameUI(Stage stage){
        root = new Group();
        root.addActor(wordsTable);
        stage.addActor(root);
    }

    private void setWords(List<String> words){
        wordsTable.clearChildren();
        for(String word:words){
            Label lbl = new Label(word,ResDebug.res.uiskin_json);
            wordsTable.add(lbl);
            wordsTable.row();
        }
    }

    private void dispose(){
        root.remove();
    }
}
