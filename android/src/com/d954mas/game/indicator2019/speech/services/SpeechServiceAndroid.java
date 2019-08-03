package com.d954mas.game.indicator2019.speech.services;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.justai.aimybox.Aimybox;
import com.justai.aimybox.api.DialogApi;
import com.justai.aimybox.api.aimybox.AimyboxDialogApi;
import com.justai.aimybox.core.AimyboxException;
import com.justai.aimybox.core.Config;
import com.justai.aimybox.model.Request;
import com.justai.aimybox.model.Response;
import com.justai.aimybox.model.Speech;
import com.justai.aimybox.model.TextSpeech;
import com.justai.aimybox.speechkit.google.fixed.GooglePlatformSpeechToText;
import com.justai.aimybox.speechkit.google.platform.GooglePlatformTextToSpeech;
import com.justai.aimybox.speechkit.yandex.cloud.YandexSpeechToText;
import com.justai.aimybox.speechtotext.SpeechToText;
import com.justai.aimybox.texttospeech.TextToSpeech;
import com.justai.aimybox.voicetrigger.VoiceTrigger;
import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CancellationException;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.ActorScope;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.selects.SelectClause1;

@SuppressLint("MissingPermission")
public class SpeechServiceAndroid implements com.d954mas.game.indicator2019.speech.services.iface.SpeechService, OnDSListener  {
    private static final String TAG = "SpeechServiceAndroid";
    private static final String TAG_EVENT = "Speech[EVENT]";
    private final String UNIT_ID = UUID.randomUUID().toString();
    private Locale LOCALE =  new Locale("ru","RU");
    private Activity context;
    private Aimybox aimybox;

    //region subscriptions
    private  ReceiveChannel<AimyboxException> subscriptionExceptions;
    private  ReceiveChannel<SpeechToText.Event> subscriptionSTTEvents;
    private  ReceiveChannel<TextToSpeech.Event> subscriptionTTSEvents;
    private  ReceiveChannel<VoiceTrigger.Event> subscriptionVoiceTriggerEvents;
    private  ReceiveChannel<DialogApi.Event> subscriptionDialogsEvents;
    //endregion
    private Set<SpeechListener> speechListeners;

    //region init
    public SpeechServiceAndroid(Activity context){ this.context = context; }

    @Override
    public void init() {
        Log.d(TAG,"init speech service");
        speechListeners = new LinkedHashSet<>();

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DroidSpeech droidSpeech =  new DroidSpeech(context, null);
                droidSpeech.setPreferredLanguage(LOCALE.getLanguage());
                droidSpeech.setOnDroidSpeechListener(SpeechServiceAndroid.this);
                GooglePlatformSpeechToText speechToText =  new GooglePlatformSpeechToText(context, LOCALE, false){
                    @NotNull
                    @Override
                    public ReceiveChannel<Result> startRecognition() {
                        context.runOnUiThread(() -> droidSpeech.startDroidSpeechRecognition());

                        return super.startRecognition();
                    }

                    @Override
                    public void stopRecognition() {
                        super.stopRecognition();
                       // droidSpeech.closeDroidSpeechOperations();
                    }

                    @Override
                    public void cancelRecognition() {
                        super.cancelRecognition();
                     //   droidSpeech.closeDroidSpeechOperations();
                    }

                    @Override
                    public void destroy() {
                        super.destroy();
                       // droidSpeech.closeDroidSpeechOperations();
                    }
                };
                Config config = Config.Companion.create(
                        speechToText,
                        new GooglePlatformTextToSpeech(context, LOCALE, null, 1),
                        new DialogApi() {
                            @Override
                            public long getRequestTimeoutMs() {
                                return 0;
                            }

                            @Nullable
                            @Override
                            public Object send(@NotNull Request request, @NotNull Continuation<? super Response> continuation) {
                                return null;
                            }

                            @Override
                            public void destroy() {
                            }
                        }, builder -> Unit.INSTANCE);
                aimybox = new Aimybox(config);
                registerListeners();
                aimybox.stopRecognition();
                //say("Разговорный сервис успешно загружен");
                Log.d(TAG,"init speech service completed");
            }
        });

    }

    private void registerListeners(){
        subscriptionExceptions = aimybox.getExceptions().openSubscription();
        subscriptionSTTEvents = aimybox.getSpeechToTextEvents().openSubscription();
        subscriptionTTSEvents = aimybox.getTextToSpeechEvents().openSubscription();
        subscriptionVoiceTriggerEvents = aimybox.getVoiceTriggerEvents().openSubscription();
        subscriptionDialogsEvents = aimybox.getDialogApiEvents().openSubscription();
    }
    //endregion

    @Override
    public void dispose() {
        aimybox.cancelCurrentTask();
        aimybox.cancelRecognition();
        aimybox.getExceptions().cancel(null);
        aimybox.getSpeechToTextEvents().cancel(null);
        aimybox.getDialogApiEvents().cancel(null);
        aimybox.getTextToSpeechEvents().cancel(null);
        aimybox.getVoiceTriggerEvents().cancel(null);
    }

    @Override
    public void update(float dt) {
        iterateEvents();
    }

    //region events
    private void iterateExceptions(){
        AimyboxException event;
        while ((event = subscriptionExceptions.poll())!=null){
            Log.e(TAG_EVENT,"AimyboxException");
            event.printStackTrace();
        }
    }
    private void iterateSTTEvents(){
        SpeechToText.Event event;
        while ((event = subscriptionSTTEvents.poll())!=null){
            if (event instanceof SpeechToText.Event.RecognitionStarted){
                SpeechToText.Event.RecognitionStarted eventCast = (SpeechToText.Event.RecognitionStarted) event;
                Log.d(TAG_EVENT,"SpeechToText.Event.RecognitionStarted");
            }
            else if (event instanceof SpeechToText.Event.RecognitionPartialResult){
                SpeechToText.Event.RecognitionPartialResult eventCast = (SpeechToText.Event.RecognitionPartialResult) event;
                Log.d(TAG_EVENT,"SpeechToText.Event.RecognitionPartialResult");
                for (SpeechListener listener:speechListeners){
                    listener.onPartialResult(eventCast.getText());
                }
            }
            else if (event instanceof SpeechToText.Event.RecognitionResult){
                SpeechToText.Event.RecognitionResult eventCast = (SpeechToText.Event.RecognitionResult) event;
                Log.d(TAG_EVENT,"SpeechToText.Event.RecognitionResult");
                for (SpeechListener listener:speechListeners){
                    listener.onResult(eventCast.getText());
                }
            }
            else if (event instanceof SpeechToText.Event.EmptyRecognitionResult){
                SpeechToText.Event.EmptyRecognitionResult eventCast = (SpeechToText.Event.EmptyRecognitionResult) event;
                Log.d(TAG_EVENT,"SpeechToText.Event.EmptyRecognitionResult");
                for (SpeechListener listener:speechListeners){
                    listener.onResult("");
                }
            }
            else if (event instanceof SpeechToText.Event.RecognitionCancelled){
                SpeechToText.Event.RecognitionCancelled eventCast = (SpeechToText.Event.RecognitionCancelled) event;
                Log.d(TAG_EVENT,"SpeechToText.Event.RecognitionCancelled");
            }
            else if (event instanceof SpeechToText.Event.SpeechStartDetected){
                SpeechToText.Event.SpeechStartDetected eventCast = (SpeechToText.Event.SpeechStartDetected) event;
                Log.d(TAG_EVENT,"SpeechToText.Event.SpeechStartDetected");
                for (SpeechListener listener:speechListeners){
                    listener.onStart();
                }
            }
            else if (event instanceof SpeechToText.Event.SpeechEndDetected){
                SpeechToText.Event.SpeechEndDetected eventCast = (SpeechToText.Event.SpeechEndDetected) event;
                Log.d(TAG_EVENT,"SpeechToText.Event.SpeechEndDetected");
                for (SpeechListener listener:speechListeners){
                    listener.onEnd();
                }
            }
            else if (event instanceof SpeechToText.Event.SoundVolumeRmsChanged){
                SpeechToText.Event.SoundVolumeRmsChanged eventCast = (SpeechToText.Event.SoundVolumeRmsChanged) event;
             //   Log.d(TAG_EVENT,"SpeechToText.Event.SoundVolumeRmsChanged");
            }
        };
    }
    private void iterateTTSEvents(){
        TextToSpeech.Event event;
        while ((event = subscriptionTTSEvents.poll())!=null){
            if (event instanceof TextToSpeech.Event.SpeechSequenceStarted){
                TextToSpeech.Event.SpeechSequenceStarted eventCast = (TextToSpeech.Event.SpeechSequenceStarted) event;
                Log.d(TAG_EVENT,"TextToSpeech.Event.SpeechSequenceStarted");
            }
            else if (event instanceof TextToSpeech.Event.SpeechStarted){
                TextToSpeech.Event.SpeechStarted eventCast = (TextToSpeech.Event.SpeechStarted) event;
                Log.d(TAG_EVENT,"TextToSpeech.Event.SpeechStarted");
            }
            else if (event instanceof TextToSpeech.Event.SpeechEnded){
                TextToSpeech.Event.SpeechEnded eventCast = (TextToSpeech.Event.SpeechEnded) event;
                Log.d(TAG_EVENT,"TextToSpeech.Event.SpeechEnded");
            }
            else if (event instanceof TextToSpeech.Event.SpeechSequenceCompleted){
                TextToSpeech.Event.SpeechSequenceCompleted eventCast = (TextToSpeech.Event.SpeechSequenceCompleted) event;
                Log.d(TAG_EVENT,"TextToSpeech.Event.SpeechSequenceCompleted");
            }
            else if (event instanceof TextToSpeech.Event.SpeechSkipped){
                TextToSpeech.Event.SpeechSkipped eventCast = (TextToSpeech.Event.SpeechSkipped) event;
                Log.d(TAG_EVENT,"TextToSpeech.Event.SpeechSkipped");
            }
        };
    }
    private void iterateVoiceTriggerEvents(){
        VoiceTrigger.Event event;
        while ((event = subscriptionVoiceTriggerEvents.poll())!=null){
            if (event instanceof VoiceTrigger.Event.Started){
                VoiceTrigger.Event.Started eventCast = (VoiceTrigger.Event.Started) event;
                Log.d(TAG_EVENT,"VoiceTrigger.Event.Started");
            }
            else if (event instanceof VoiceTrigger.Event.Stopped){
                VoiceTrigger.Event.Stopped eventCast = (VoiceTrigger.Event.Stopped) event;
                Log.d(TAG_EVENT,"VoiceTrigger.Event.Stopped");
            }else if (event instanceof VoiceTrigger.Event.Triggered){
                VoiceTrigger.Event.Triggered eventCast = (VoiceTrigger.Event.Triggered) event;
                Log.d(TAG_EVENT,"VoiceTrigger.Event.Triggered");
            }

        };
    }
    private void iterateDialogsEvents(){
        DialogApi.Event event;
        while ((event = subscriptionDialogsEvents.poll())!=null){
            if (event instanceof DialogApi.Event.Receive){
                DialogApi.Event.Receive receiveEvent = (DialogApi.Event.Receive) event;
                Log.d(TAG_EVENT,"DialogApi.Event.Receive");
            }
            else if (event instanceof DialogApi.Event.Send){
                DialogApi.Event.Send sendEvent = (DialogApi.Event.Send) event;
                Log.d(TAG_EVENT,"DialogApi.Event.Send");
            }
        };
    }

    private void iterateEvents(){
        iterateExceptions();
        iterateSTTEvents();
        iterateTTSEvents();
        iterateVoiceTriggerEvents();
        iterateDialogsEvents();
    }
    //endregion

    //region say
    @Override
    public void say(String text) { say(text,NextAction.STANDBY); }

    @Override
    public void say(String text, NextAction action) {
        aimybox.speak(new TextSpeech(text,LOCALE.getLanguage()), Aimybox.NextAction.valueOf(action.toString()));
    }

    @Override
    public void say(List<String> text) { say(text,NextAction.STANDBY); }

    @Override
    public void say(List<String> text, NextAction action) {
        List<Speech> speeches = new ArrayList<>();
        for(String speech:text){
            speeches.add(new TextSpeech(speech,LOCALE.getLanguage()));
        }
        aimybox.speak(speeches,  Aimybox.NextAction.valueOf(action.toString()));
    }
    //endregion

    //region recognition
    @Override
    public void recognitionStop() { aimybox.stopRecognition(); }

    @Override
    public void recognitionCancel() { aimybox.cancelRecognition(); }

    @Override
    public void recognitionStart() { aimybox.startRecognition(); }

    @Override
    public void recognitionToggle() { aimybox.toggleRecognition(); }

    //endregion

    @Override
    public void cancelCurrentTask() { aimybox.cancelCurrentTask(); }

    @Override
    public void standby() { aimybox.standby(); }

    @Override
    public void send(Object request) { throw new java.lang.UnsupportedOperationException("Not implemented yet."); }

    @Override
    public State getState() {
       return State.valueOf(aimybox.getState().getValue().toString());
    }

    @Override
    public void addSpeechListener(SpeechListener speechListener) {
        speechListeners.add(speechListener);
    }

    @Override
    public void removeSpeechListener(SpeechListener speechListener) {
        speechListeners.remove(speechListener);
    }

    //region life cycle
    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
    //endregion

    //region continuous recognition
    @Override
    public void onDroidSpeechSupportedLanguages(String currentSpeechLanguage, List<String> supportedSpeechLanguages) {

    }

    @Override
    public void onDroidSpeechRmsChanged(float rmsChangedValue) {

    }

    @Override
    public void onDroidSpeechLiveResult(String liveSpeechResult) {
        Log.d(TAG,"Live result" + liveSpeechResult);
        for (SpeechListener listener:speechListeners){
            listener.onPartialResult(liveSpeechResult);
        }
    }

    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult) {
        Log.d(TAG,"Final result" + finalSpeechResult);
        for (SpeechListener listener:speechListeners){
            listener.onResult(finalSpeechResult);
        }
    }

    @Override
    public void onDroidSpeechClosedByUser() {

    }

    @Override
    public void onDroidSpeechError(String errorMsg) {

    }

}
