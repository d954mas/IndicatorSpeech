package com.d954mas.game.indicator2019.speech.screnes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.d954mas.engine.services.Services;
import com.d954mas.engine.services.iface.ScreenService;
import com.d954mas.game.indicator2019.speech.Game;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.utils.Constants;
import com.generated.ResDebug;

public class GameScreen implements Screen {
    private Stage stage;
    private Button buttonStartRecognotion;

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT));
        buttonStartRecognotion = new  Button(ResDebug.res.uiskin_json);
        buttonStartRecognotion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("GameSceen","start recognition");
                Services.get(SpeechService.class).recognitionStart();
            }
        });
        buttonStartRecognotion.setPosition(Constants.GAME_WIDTH/2,Constants.GAME_HEIGHT/2);
        buttonStartRecognotion.setSize(400,400);
        stage.addActor(buttonStartRecognotion);
        ((Game)Gdx.app.getApplicationListener()).getMainInput().addProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        ((Game)Gdx.app.getApplicationListener()).getMainInput().removeProcessor(stage);
    }
}
