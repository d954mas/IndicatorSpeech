package com.d954mas.game.indicator2019.speech.services.impl;

import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SpeechServiceDefault implements SpeechService {
    private Set<SpeechListener> speechListeners;

    @Override
    public void init() {
        speechListeners = new LinkedHashSet<>();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void say(String text) {

    }

    @Override
    public void say(String text, NextAction action) {

    }

    @Override
    public void say(List<String> text) {

    }

    @Override
    public void say(List<String> text, NextAction action) {

    }


    @Override
    public void recognitionStop() {

    }

    @Override
    public void recognitionCancel() {

    }

    @Override
    public void recognitionStart() {

    }

    @Override
    public void recognitionToggle() {

    }

    @Override
    public void cancelCurrentTask() {

    }

    @Override
    public void standby() {

    }

    @Override
    public void send(Object request) {

    }

    @Override
    public State getState() {
        return State.LISTENING;
    }

    @Override
    public void addSpeechListener(SpeechListener speechListener) {
        speechListeners.add(speechListener);
    }

    @Override
    public void removeSpeechListener(SpeechListener speechListener) {
        speechListeners.remove(speechListener);
    }

    public void speechEventPart(String string){
        for (SpeechListener listener:speechListeners){
            listener.onPartialResult(string);
        }
    }

    public void speechEventFinal(String string){
        for (SpeechListener listener:speechListeners){
            listener.onResult(string);
        }
    }


}
