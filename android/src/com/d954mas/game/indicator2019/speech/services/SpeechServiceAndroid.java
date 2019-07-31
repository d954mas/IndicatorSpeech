package com.d954mas.game.indicator2019.speech.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import com.justai.aimybox.Aimybox;
import com.justai.aimybox.api.aimybox.AimyboxDialogApi;
import com.justai.aimybox.core.Config;
import com.justai.aimybox.model.Speech;
import com.justai.aimybox.model.TextSpeech;
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText;
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@SuppressLint("MissingPermission")
public class SpeechServiceAndroid implements com.d954mas.game.indicator2019.speech.services.iface.SpeechService {
    private static final String TAG = "SpeechServiceAndroid";
    private Locale LOCALE =  new Locale("ru","RU");
    private Context context;
    private Aimybox aimybox;
    private final String UNIT_ID = UUID.randomUUID().toString();

    public SpeechServiceAndroid(Context context){
        this.context = context;
    }

    @Override
    public void init() {
        Log.d(TAG,"init speech service");
        Config config = Config.Companion.create(new GooglePlatformSpeechToText(context, LOCALE, true),
                new GooglePlatformTextToSpeech(context, LOCALE, null, 1),
                new AimyboxDialogApi("CXnuBJknIGKjXVaQkQ6MB0d5O7MUC2Hd", UNIT_ID, "https://zb02.just-ai.com/"), builder -> {
                    return Unit.INSTANCE; //kotlin from java ugly
                });
        aimybox = new Aimybox(config);
        aimybox.cancelRecognition();
        say("Разговорный сервис успешно загружен");
        Log.d(TAG,"init speech service completed");
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        aimybox.cancelCurrentTask();
        aimybox.cancelRecognition();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update(float dt) {

    }


    @Override
    public void say(String text) {
        say(text,NextAction.STANDBY);
    }

    @Override
    public void say(String text, NextAction action) {
        aimybox.speak(new TextSpeech(text,LOCALE.getLanguage()), Aimybox.NextAction.valueOf(action.toString()));
    }

    @Override
    public void say(List<String> text) {
        say(text,NextAction.STANDBY);
    }

    @Override
    public void say(List<String> text, NextAction action) {
        List<Speech> speeches = new ArrayList<>();
        for(String speech:text){
            speeches.add(new TextSpeech(speech,LOCALE.getLanguage()));
        }
        aimybox.speak(speeches,  Aimybox.NextAction.valueOf(action.toString()));
    }

    @Override
    public void recognitionStop() {
        aimybox.stopRecognition();
    }

    @Override
    public void recognitionCancel() {
        aimybox.cancelRecognition();
    }

    @Override
    public void recognitionStart() {
        aimybox.startRecognition();
    }

    @Override
    public void recognitionToggle() {
        aimybox.toggleRecognition();
    }

    @Override
    public void cancelCurrentTask() {
        aimybox.cancelCurrentTask();
    }

    @Override
    public void standby() {
        aimybox.standby();
    }

    @Override
    public void send(Object request) {
        throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }
}
