package com.d954mas.game.indicator2019.speech.model;



import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianAnalyzer;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

public class MagicWord {
    private Set<String> synonims;
    public final String mainWord;

    public MagicWord(@Nonnull List<String> synonims){
        this.synonims = new LinkedHashSet<>();
        for(String word:synonims){
            this.synonims.addAll(World.get().luceneMorph.getNormalForms(word));
        }
        mainWord = synonims.get(0);
    }

    ///word already in normal form
    public boolean match(String word) {
        return synonims.contains(word);
    }

    @Override
    public String toString() {
        return "MagicWord{" +
                "synonims=" + mainWord +
                '}';
    }
}
