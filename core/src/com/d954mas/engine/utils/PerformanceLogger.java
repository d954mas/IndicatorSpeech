package com.d954mas.engine.utils;

import com.badlogic.gdx.Gdx;

public class PerformanceLogger {
    public  float logTime = 60f;
    private float time;

    public void update(float delta) {
        time += delta;
        if (time > logTime) {
            time -= logTime;
            Gdx.app.debug("PerformanceLogger", "fps:" + Gdx.graphics.getFramesPerSecond());
            float javaHeap = Gdx.app.getJavaHeap() / 1048576f;
            float nativeHeap = Gdx.app.getNativeHeap() / 1048576f;
            Gdx.app.debug("PerformanceLogger", "javaHeap:" + javaHeap);
            Gdx.app.debug("PerformanceLogger", "nativeHeap:" + nativeHeap);
        }
    }

    public void setLogTime(float logTime){
        this.logTime=logTime;
        time=0;
    }
}
