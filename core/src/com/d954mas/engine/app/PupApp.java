package com.d954mas.engine.app;

import com.badlogic.gdx.ApplicationListener;
import com.d954mas.engine.services.Service;
import com.d954mas.engine.services.Services;

import java.util.Map;

public abstract class PupApp implements ApplicationListener {

    public PupApp(Map<Class<? extends Service>,? extends Service> nativeServices){
        Services.addOrReplaceServices(nativeServices);
    }

    @Override
    public void resize(int width, int height) {
        Services.resize(width,height);
    }

    @Override
    public void render() {
    }

    @Override
    public void pause() {
        Services.pause();
    }

    @Override
    public void resume() {
        Services.resume();
    }

    @Override
    public void dispose() {
        Services.dispose();
    }
}
