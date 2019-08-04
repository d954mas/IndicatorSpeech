package com.d954mas.game.indicator2019.speech.model;

import com.d954mas.engine.utils.Cs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

public class MagicWords {
    public static MagicWord attack;
    public static MagicWord defence;
    public static MagicWord strength;
    public static MagicWord fire;
    public static MagicWord storm;
    public static MagicWord love;
    public static MagicWord ice;
    public static MagicWord random;
    public static MagicWord lie;

    public static List<MagicWord> words;


    public static void init(){
        attack = new MagicWord(Cs.of("атака","удар","ударил","атаковать","атакован"));
        defence = new MagicWord(Cs.of("защита"));
        strength = new MagicWord(Cs.of("сильный","сильно"));
        fire = new MagicWord(Cs.of("огненный"));
        storm = new MagicWord(Cs.of("грозовой"));
        love = new MagicWord(Cs.of("любовный"));
        ice = new MagicWord(Cs.of("ледяной"));
        random = new MagicWord(Cs.of("случайно"));
        lie = new MagicWord(Cs.of("обманный"));
        words = Cs.of(attack,defence,strength,
                fire,storm,love,ice,random,lie);
        World.get().setStartWords();
    }

    public static void dispose(){

    }

    @Nullable
    private static MagicWord recognizeSingle(String string){
        for(MagicWord magicWord:World.get().handsWords){
            if (magicWord.match(string)){
                return magicWord;
            }
        }
        return null;
    }

    public static List<String> recognizeNormal(String string){
        //remove all not cyrilic from string
        string = string.toLowerCase();
        StringBuilder onlyCyrilic = new StringBuilder();
        for(int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if(Character.UnicodeBlock.of(character).equals(Character.UnicodeBlock.CYRILLIC)) {
                onlyCyrilic.append(character);
            }else if(character == ' '){
                onlyCyrilic.append(character);
            }
        }
        string = onlyCyrilic.toString();
      List<String> result = new ArrayList<>();
      String[] words = string.split(" ");
      for(String word:words){
          if(!word.isEmpty()) {
              List<String> normalForms = World.get().luceneMorph.getNormalForms(word);
              result.add(normalForms.get(0));
          }
      }
      return result;
    };

    public static List<MagicWord> recognize(String string){
        List<String> normals = recognizeNormal(string);
        List<MagicWord> magicWords = new ArrayList<>();
        for(String word:normals){
            MagicWord magicWord = recognizeSingle(word);
            if(magicWord!=null){
                magicWords.add(magicWord);
            }
        }

        List<MagicWord> availableWords = new ArrayList<>(World.get().handsWords);

        Iterator<MagicWord> iter = magicWords.iterator();
        while (iter.hasNext()){
            MagicWord magicWord = iter.next();
            if(availableWords.contains(magicWord)){
                availableWords.remove(magicWord);
            }else{
                iter.remove();
            }
        }


        return magicWords;
    }
}
