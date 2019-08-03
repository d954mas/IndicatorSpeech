package com.d954mas.game.indicator2019.speech.screnes.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.d954mas.engine.utils.ui.UIUtils;
import com.d954mas.utils.Constants;
import com.generated.ResGame;

public class GameSceneEnemy {
    private Sprite sprite;
    private Animation<TextureRegion> animation;
    private float time;
    public GameSceneEnemy(){
        sprite = new Sprite(ResGame.res.location_atlas.bg1);
        setAnimation(ResGame.res.bat_atlas.sprite1);
        center();
    }

    public void center(){
        float dx = (Constants.GAME_WIDTH-sprite.getWidth())/2;
        float dy = (Constants.GAME_HEIGHT-sprite.getHeight())/2;
        sprite.setPosition(dx,dy);
    }

    public void setAnimation(Animation<TextureRegion> newAnimation){
        animation = newAnimation;
        time = 0;
    }

    public void update(float dt){
        time = time + dt;
        TextureRegion region = animation.getKeyFrame(time,true);
        sprite.setRegion(region);
        sprite.setSize(region.getRegionWidth(),region.getRegionHeight());
        center();
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }

}
