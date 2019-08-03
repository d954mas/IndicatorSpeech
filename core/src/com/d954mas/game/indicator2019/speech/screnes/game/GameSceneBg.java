package com.d954mas.game.indicator2019.speech.screnes.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.generated.ResGame;

public class GameSceneBg {
    private Sprite bg;
    public GameSceneBg(){
        bg = new Sprite(ResGame.res.location_atlas.bg1);
    }

    public void update(float dt){

    }

    public void draw(SpriteBatch batch){
       bg.draw(batch);
    }

}
