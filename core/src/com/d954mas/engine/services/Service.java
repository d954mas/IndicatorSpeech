package com.d954mas.engine.services;

public interface Service {

    //init called once,when Services.get(Service.class) called;(lazy init)
    void init();

    void pause();
    void resume();
    void dispose();
    void resize(int width,int height);
    void update(float dt);

}
