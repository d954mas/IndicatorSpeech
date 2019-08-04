package com.d954mas.game.indicator2019.speech.model.effects;

public class Effect {
    private final String tag;
    private final String description;
    public int turns;
    private final int startTurns;
    public Effect(int turns,String tag,String description){
        this.turns = turns;
        this.tag = tag;
        this.description = description;
        startTurns = turns;
    }

    public void reset(){
        turns = startTurns;
    }

    @Override
    public String toString() {
        return tag + " " + turns +"/" + startTurns + "\n" +
                description;
    }
}
