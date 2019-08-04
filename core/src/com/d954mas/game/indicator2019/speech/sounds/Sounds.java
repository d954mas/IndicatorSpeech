package com.d954mas.game.indicator2019.speech.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    public static Sound[] deaths = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("death_bat.wav")),
            Gdx.audio.newSound(Gdx.files.internal("death_lion.wav")),
            Gdx.audio.newSound(Gdx.files.internal("death_slime.wav")),
            Gdx.audio.newSound(Gdx.files.internal("death_spudi.wav")),
            Gdx.audio.newSound(Gdx.files.internal("death_ogre.wav"))};

    public static Sound[] attacks = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("attack_bat.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attack_lion.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attack_slime.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attack_spudi.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attack_ogre.wav"))};

    public static Sound[] spawns = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("spawn_bat.wav")),
            Gdx.audio.newSound(Gdx.files.internal("spawn_lion.wav")),
            Gdx.audio.newSound(Gdx.files.internal("spawn_slime.wav")),
            Gdx.audio.newSound(Gdx.files.internal("spawn_spudi.wav")),
            Gdx.audio.newSound(Gdx.files.internal("spawn_ogre.wav"))};

    public static Sound[] attackeds = new Sound[]{
            Gdx.audio.newSound(Gdx.files.internal("attacked_bat.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attacked_lion.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attacked_slime.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attacked_spudi.wav")),
            Gdx.audio.newSound(Gdx.files.internal("attacked_ogre.wav"))};



    public static Music ambient_cave =  Gdx.audio.newMusic(Gdx.files.internal("drops.wav"));
}