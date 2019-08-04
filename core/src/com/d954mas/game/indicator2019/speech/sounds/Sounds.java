package com.d954mas.game.indicator2019.speech.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    public Sound effect_attack = Gdx.audio.newSound(Gdx.files.internal("attack.wav"));
    public Sound effect_shield = Gdx.audio.newSound(Gdx.files.internal("shield.wav"));
    public Sound effect_strong = Gdx.audio.newSound(Gdx.files.internal("strong.wav"));
    public Sound effect_fireball = Gdx.audio.newSound(Gdx.files.internal("fireball.wav"));
    public Sound effect_thunder = Gdx.audio.newSound(Gdx.files.internal("thunder.wav"));
    public Sound effect_freeze = Gdx.audio.newSound(Gdx.files.internal("freeze.wav"));
    public Sound sound_effect_cheating = Gdx.audio.newSound(Gdx.files.internal("cheating.wav"));

    public Sound attack_bat = Gdx.audio.newSound(Gdx.files.internal("attack_bat.wav"));
    public Sound attack_lion = Gdx.audio.newSound(Gdx.files.internal("attack_lion.wav"));
    public Sound attack_slime = Gdx.audio.newSound(Gdx.files.internal("attack_slime.wav"));
    public Sound attack_spudi = Gdx.audio.newSound(Gdx.files.internal("attack_spudi.wav"));
    public Sound attack_ogre = Gdx.audio.newSound(Gdx.files.internal("attack_ogre.wav"));

    public Sound spawn_bat = Gdx.audio.newSound(Gdx.files.internal("spawn_bat.wav"));
    public Sound spawn_lion = Gdx.audio.newSound(Gdx.files.internal("spawn_lion.wav"));
    public Sound spawn_slime = Gdx.audio.newSound(Gdx.files.internal("spawn_slime.wav"));
    public Sound spawn_spudi = Gdx.audio.newSound(Gdx.files.internal("spawn_spudi.wav"));
    public Sound spawn_ogre = Gdx.audio.newSound(Gdx.files.internal("spawn_ogre.wav"));

    public Sound death_bat = Gdx.audio.newSound(Gdx.files.internal("death_bat.wav"));
    public Sound death_lion = Gdx.audio.newSound(Gdx.files.internal("death_lion.wav"));
    public Sound death_slime = Gdx.audio.newSound(Gdx.files.internal("death_slime.wav"));
    public Sound death_spudi = Gdx.audio.newSound(Gdx.files.internal("death_spudi.wav"));
    public Sound death_ogre = Gdx.audio.newSound(Gdx.files.internal("death_ogre.wav"));

    public Sound attacked_bat = Gdx.audio.newSound(Gdx.files.internal("attacked_bat.wav"));
    public Sound attacked_lion = Gdx.audio.newSound(Gdx.files.internal("attacked_lion.wav"));
    public Sound attacked_slime = Gdx.audio.newSound(Gdx.files.internal("attacked_slime.wav"));
    public Sound attacked_spudi = Gdx.audio.newSound(Gdx.files.internal("attacked_spudi.wav"));
    public Sound attacked_ogre = Gdx.audio.newSound(Gdx.files.internal("attacked_ogre.wav"));



    public Music ambient_cave =  Gdx.audio.newMusic(Gdx.files.internal("drops.wav"));
}