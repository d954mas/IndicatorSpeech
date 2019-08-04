package com.d954mas.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.d954mas.engine.services.Services;
import com.d954mas.game.indicator2019.speech.model.MagicWord;
import com.d954mas.game.indicator2019.speech.model.MagicWords;
import com.d954mas.game.indicator2019.speech.model.World;
import com.d954mas.game.indicator2019.speech.model.effects.Effect;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.utils.Constants;
import com.generated.ResDebug;
import com.generated.ResDebugA;

public class DebugInfoStage implements Disposable {
    private Stage stage;
    private Label lblFrames;
    private Label lblMemoryJava;
    private Label lblMemoryNative;
    private Label lblDrawCalls;
    private Label lblSpeechState;
    private Label lblSpeechResults;
    private Label lblSpeechNormalResults;
    private Label lblSpeechMagicResults;
    private Label lblHp;
    private Label lblEffects;
    private GLProfiler profiler;
    public DebugInfoStage(){
        stage = new Stage(new FitViewport(Constants.GAME_WIDTH,Constants.GAME_HEIGHT));
        lblFrames = new Label("DT:0", ResDebug.res.uiskin_json);
        lblFrames.setPosition(0,1080-60);
        lblMemoryJava= new Label("Mem:0", ResDebug.res.uiskin_json);
        lblMemoryJava.setPosition(120,1080-60);
        lblMemoryNative= new Label("Mem:0", ResDebug.res.uiskin_json);
        lblMemoryNative.setPosition(550,1080-60);
        lblDrawCalls= new Label("DrawCalls:0", ResDebug.res.uiskin_json);
        lblDrawCalls.setPosition(1050,1080-60);

        lblSpeechState= new Label("State:", ResDebug.res.uiskin_json);
        lblSpeechState.setPosition(300,1080-60-60);
        lblSpeechResults= new Label("Result:", ResDebug.res.uiskin_json);
        lblSpeechResults.setPosition(300+420,1080-60-60);

        lblSpeechNormalResults= new Label("Normal:", ResDebug.res.uiskin_json);
        lblSpeechNormalResults.setPosition(300+420,1080-60-60-60);

        lblSpeechMagicResults= new Label("Magic", ResDebug.res.uiskin_json);
        lblSpeechMagicResults.setPosition(300+420,1080-60-60-60-60);

        lblEffects= new Label("Effects", ResDebug.res.uiskin_json);
        lblEffects.setPosition(500,1080-60-60-60-60-60-60);

    ////  stage.addActor(lblFrames);
      //  stage.addActor(lblMemoryJava);
      //  stage.addActor(lblMemoryNative);
      //  stage.addActor(lblDrawCalls);
     //   stage.addActor(lblSpeechState);
  //      stage.addActor(lblSpeechResults);
   //    stage.addActor(lblSpeechNormalResults);
  //      stage.addActor(lblSpeechMagicResults);
   //     stage.addActor(lblEffects);
        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        Services.get(SpeechService.class).addSpeechListener(new SpeechService.SpeechListener() {
            @Override
            public void onStart() { }

            @Override
            public void onEnd() { }

            @Override
            public void onPartialResult(String result) {
                lblSpeechResults.setText("Result(part):" + result);
                lblSpeechNormalResults.setText("Normal(part):" + String.join(" ", MagicWords.recognizeNormal(result)));
                String magicString = "";
                for(MagicWord magicWord:MagicWords.recognize(result)){
                    magicString += "{" +magicWord.mainWord +"}";
                }
                lblSpeechMagicResults.setText("Magic(part):" + magicString);

            }

            @Override
            public void onResult(String result) {
                lblSpeechResults.setText("Result:" + result);
                lblSpeechNormalResults.setText("Normal:" + String.join(" ", MagicWords.recognizeNormal(result)));
                String magicString = "";
                for(MagicWord magicWord:MagicWords.recognize(result)){
                    magicString += "{" +magicWord.mainWord +"}";
                }
                lblSpeechMagicResults.setText("Magic:" + magicString);
            }
        });
    }

    private String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        char pre = "KMGTPE".charAt(exp-1);
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public void update(float dt){
        stage.act(dt);
        lblFrames.setText(String.format("dt:%d", Gdx.graphics.getFramesPerSecond()));
        lblMemoryJava.setText(String.format("mem(java):%s", humanReadableByteCount(Gdx.app.getJavaHeap())));
        lblMemoryNative.setText(String.format("mem(native):%s", humanReadableByteCount(Gdx.app.getNativeHeap())));
        lblDrawCalls.setText(String.format("DrawCalls:%d", profiler.getDrawCalls()));
        lblSpeechState.setText(String.format("Speech:%s", Services.get(SpeechService.class).getState()));
        String stringEffects = "";
        for(Effect effect:World.get().effects){
            stringEffects += effect.toString() + " ";
        }
        lblEffects.setText("EFFECTS:" +stringEffects);
    }

    public void draw() {
        profiler.reset();
        stage.draw();
    }

    public void setInput(InputMultiplexer multiplexer){
        multiplexer.addProcessor(stage);
    }

    public void resize(int w,int h){
        stage.getViewport().update(w,h,true);
    }

    @Override
    public void dispose(){
        stage.dispose();
        profiler.disable();
    }


}
