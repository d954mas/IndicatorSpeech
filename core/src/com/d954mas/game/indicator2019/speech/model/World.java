package com.d954mas.game.indicator2019.speech.model;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;

public class World {
    private static World instance;
    private World() {
        try {
            luceneMorph = new RussianLuceneMorphology();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LuceneMorphology luceneMorph;

    public static void init() {
        instance = new World();
    }

    public static World get(){
        return instance;
    }

    public static void dispose(){
        instance = null;
    }
}
