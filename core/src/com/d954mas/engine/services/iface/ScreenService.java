package com.d954mas.engine.services.iface;

import com.badlogic.gdx.Screen;
import com.d954mas.engine.services.Service;

public interface ScreenService extends Service {
    public void showNextScreen(Screen screen);
    public void showNextScreen(Screen screen,boolean clearStack);
    public void showPreviousScreen();
    public Screen getCurrentScreen();
    public Screen getPreviousScreen();
}
