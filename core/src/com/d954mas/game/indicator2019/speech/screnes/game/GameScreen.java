package com.d954mas.game.indicator2019.speech.screnes.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.d954mas.engine.services.Services;
import com.d954mas.game.indicator2019.speech.Game;
import com.d954mas.game.indicator2019.speech.model.MagicWord;
import com.d954mas.game.indicator2019.speech.model.MagicWords;
import com.d954mas.game.indicator2019.speech.model.World;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.utils.Constants;
import com.generated.ResDebug;

import java.util.List;

public class GameScreen implements Screen {
    private Stage stage;
    private Button buttonStartRecognotion;
    private GameSceneBg gameSceneBg;
    private GameUI gameUI;
    private GameSceneEnemy gameSceneEnemy;
    private SpriteBatch batch;
    private Camera camera;

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT));
        buttonStartRecognotion = new Button(ResDebug.res.uiskin_json);
        buttonStartRecognotion.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("GameScreen","start recognition");
                Services.get(SpeechService.class).recognitionStart();
            }
        });
        buttonStartRecognotion.setPosition(Constants.GAME_WIDTH-80,Constants.GAME_HEIGHT-100);
        buttonStartRecognotion.setSize(50,50);
        stage.addActor(buttonStartRecognotion);
        ((Game)Gdx.app.getApplicationListener()).getMainInput().addProcessor(stage);
        gameSceneBg = new GameSceneBg();
        gameSceneEnemy = new GameSceneEnemy();
        gameUI = new GameUI(stage);
        batch = new SpriteBatch();
        camera = new OrthographicCamera(1920,1080);
        camera.position.x = Constants.GAME_WIDTH/2;
        camera.position.y = Constants.GAME_HEIGHT/2;

        Services.get(SpeechService.class).addSpeechListener(new SpeechService.SpeechListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onEnd() {
            }

            @Override
            public void onPartialResult(String result) {
            }

            @Override
            public void onResult(String result) {
                Gdx.app.postRunnable(() -> {
                    List<MagicWord> words = MagicWords.recognize(result);
                    if (World.get().playWords(words)) {
                        gameUI.updateWords();
                    }
                });
            }
        });
    }

    @Override
    public void render(float delta) {
        gameSceneBg.update(delta);
        stage.act(delta);
        gameSceneEnemy.update(delta);
        gameSceneEnemy.update(delta);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameSceneBg.draw(batch);
        gameSceneEnemy.draw(batch);

        batch.end();
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
