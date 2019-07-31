package com.d954mas.game.indicator2019.speech;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.d954mas.engine.utils.Cs;
import com.d954mas.game.indicator2019.speech.Game;
import com.d954mas.game.indicator2019.speech.services.SpeechServiceAndroid;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        initialize(new Game(Cs.ofM(SpeechService.class,new SpeechServiceAndroid(this))), config);
    }
}
