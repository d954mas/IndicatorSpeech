package com.d954mas.game.indicator2019.speech.services.iface;

import com.d954mas.engine.services.Service;

import java.util.List;

public interface SpeechService extends Service {
    //region enums
    enum  NextAction {
        // Go to standby state.
        STANDBY,
        //Start speech recognition.
        RECOGNITION,
        /**
         * Do nothing after synthesis.
         * <p>
         * **Caution: this constant is intended primarily for usage in a [CustomSkill].
         * It will not start the voice trigger after the synthesis, so your app may enter to a non-interactive state.**
         */
        NOTHING;
    }

    enum State {
        // Aimybox is waiting for interaction. If voice trigger is defined, it is active in this state.
        STANDBY,
        //Aimybox is recognizing speech.
        LISTENING,
        //Aimybox is waiting for a dialog API to process the request.
        PROCESSING,
        //Aimybox is synthesizing speech.
        SPEAKING
    }

    //endregion

    //region Components
        /*Library use separated components for different logic
        private val speechToText = SpeechToTextComponent(config.speechToText, speechToTextEvents, exceptions)
        private val textToSpeech = TextToSpeechComponent(config.textToSpeech, textToSpeechEvents, exceptions)
        --Aimybox logic
        private val dialogApi = DialogApiComponent(config.dialogApi, dialogApiEvents, exceptions)
        private val responseHandler = AimyboxResponseHandler(this, config.skills)
        private val voiceTrigger = VoiceTriggerComponent(voiceTriggerEvents, exceptions, onTriggered = ::toggleRecognition)

        private val components = listOf(speechToText, textToSpeech, dialogApi, responseHandler)

    */
    //endregion

    //region Event channels

    //endregion



    //region speak
    public void say(String text);
    public void say(String text,NextAction action);
    public void say(List<String> text);
    public void say(List<String> text,NextAction action);
    //endregion

    //region Recognition
    /**
     * Stops the current recognition, but not cancels it completely. If something was recognized,
     * then the request to a dialog API will be executed asynchronously after calling this method.
     * */
    public void recognitionStop();

    /**
     * Cancels the current recognition and discard partial recognition results.
     * */
    public void recognitionCancel();

    /**
     * Start speech recognition.
     *
     * Once some speech has been recognized, then a dialog API request is launched.
     * in case nothing is recognized, [SpeechToText.Event.EmptyRecognitionResult] will be sent
     * to [speechToTextEvents], and Aimybox will go to [State.STANDBY] state.
     *
     * @return [Job] which completes when recognition is finished
     *
     * */
    public void recognitionStart();

    /**
     * Toggle speech recognition.
     * This method is designed to use with software or hardware recognition button,
     * it plays earcon sound when recognition starts.
     *
     * @see [Config.Builder.setEarconRes]
     * */
    public void recognitionToggle();

    //endregion


    //Cancels any active component.
    public void cancelCurrentTask();

    //Stop recognition, synthesis, API call and launch voice trigger if present.
    public void standby();



    //Do not need it ?
    /**
     * Send the [request] to a dialog API.
     *
     * @return [Job] which completes when the response is received.
     * Request model, which is used across the library.
     * You can extend it by adding some fields to [data] JSON in [CustomSkill] or custom [DialogApi].
        data class Request(
            //User input, recognized by STT or manually entered.
            val query: String,
            val data: JsonObject = JsonObject()
        )
     * */
    public void send(Object request);



}
