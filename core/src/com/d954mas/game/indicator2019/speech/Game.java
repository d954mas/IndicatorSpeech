package com.d954mas.game.indicator2019.speech;


import com.d954mas.engine.EngineApplication;
import com.d954mas.engine.services.Service;
import com.d954mas.engine.services.Services;
import com.d954mas.engine.services.iface.ScreenService;
import com.d954mas.game.indicator2019.speech.screnes.GameScreen;
import com.generated.ResGame;

import java.util.Map;

import javax.annotation.Nullable;


public class Game extends EngineApplication {

    public Game(@Nullable Map<Class<? extends Service>, Service> nativeServices) {
        super(nativeServices);
    }

    @Override
    public void create() {
        super.create();
        ResGame.res.init(manager,true);
        Services.get(ScreenService.class)
                .showNextScreen(new GameScreen());
    }

    @Override
    public void dispose(){
        super.dispose();
        ResGame.res.dispose(manager);
    }
}
