package com.d954mas.game.indicator2019.speech.model.effects;

public class Effect {
    private final String tag;
    public int turns;
    private final int startTurns;
    public Effect(int turns,String tag){
        this.turns = turns;
        this.tag = tag;
        startTurns = turns;
    }

    public void reset(){
        turns = startTurns;
    }

    @Override
    public String toString() {
        return tag + "{" + turns +"/" + startTurns + "}";
    }
}
