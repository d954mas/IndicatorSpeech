package com.d954mas.engine.services.impl;

import com.badlogic.gdx.Screen;
import com.d954mas.engine.services.iface.ScreenService;


import java.util.Stack;

public class ScreenServiceDefault implements ScreenService {
    private Stack<Screen> stack;
    @Override
    public void init() {
        stack = new Stack<Screen>();
    }

    @Override
    public void pause() {
        Screen screen=getCurrentScreen();
        if(screen!=null)screen.pause();
    }

    @Override
    public void resume() {
        Screen screen=getCurrentScreen();
        if(screen!=null)screen.resume();
    }

    @Override
    public void dispose() {
        for(Screen screen:stack){
            screen.pause();
            screen.dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        Screen screen=getCurrentScreen();
        if(screen!=null)screen.resize(width,height);
    }

    @Override
    public void update(float dt) {
        Screen screen=getCurrentScreen();
        if(screen!=null)screen.render(dt);
    }


    @Override
    public void showNextScreen(Screen nextScreen) {
        showNextScreen(nextScreen,false);
    }

    @Override
    public void showNextScreen(Screen nextScreen, boolean clearStack) {
        Screen currentScreen = getCurrentScreen();
        if (currentScreen != null) {
            currentScreen.hide();
        }
        if(clearStack){
            while (!stack.empty()){
                Screen screen=stack.pop();
                screen.dispose();
            }
        }

        stack.push(nextScreen);
        nextScreen.show();
        // nextScreen.resize(Display.getWidth(), Display.getHeight());
    }

    @Override
    public void showPreviousScreen() {
        Screen currentScreen = getCurrentScreen();
        Screen prevScreen = getPreviousScreen();

        if (currentScreen != null) {
            currentScreen.pause();
            currentScreen.dispose();
        }

        if (stack.size() > 0) {
            stack.pop();
        }

        if (prevScreen != null) {
            prevScreen.show();
           // prevScreen.resize(Display.getWidth(), Display.getHeight());
        }
    }

    @Override
    public Screen getCurrentScreen() {
        if (stack.size() > 0) {
            return stack.lastElement();
        }
        return null;
    }

    @Override
    public Screen getPreviousScreen() {
        if (stack.size() > 1) {
            return (stack.get(stack.size() - 2));
        }
        return null;
    }

}
