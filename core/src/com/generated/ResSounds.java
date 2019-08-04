package com.generated;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
public class ResSounds {
    public static ResSounds res=new ResSounds();
    private boolean isLoad;
    private boolean isInit;
    public FileHandle attack_bat_wav;
    public FileHandle attack_lion_wav;
    public FileHandle attack_ogre_wav;
    public FileHandle attack_slime_wav;
    public FileHandle attack_spudi_wav;
    public FileHandle attacked_bat_wav;
    public FileHandle attacked_lion_wav;
    public FileHandle attacked_ogre_wav;
    public FileHandle attacked_slime_wav;
    public FileHandle attacked_spudi_wav;
    public FileHandle cheating_wav;
    public FileHandle death_bat_wav;
    public FileHandle death_lion_wav;
    public FileHandle death_ogre_wav;
    public FileHandle death_slime_wav;
    public FileHandle death_spudi_wav;
    public FileHandle dungeon_ambiance_wav;
    public FileHandle fireball_wav;
    public FileHandle freeze_wav;
    public FileHandle rain_mp3;
    public FileHandle shield_wav;
    public FileHandle spawn_bat_wav;
    public FileHandle spawn_lion_wav;
    public FileHandle spawn_ogre_mp3;
    public FileHandle spawn_spudi_wav;
    public FileHandle spown_slime_wav;
    public FileHandle strong_wav;
    public FileHandle thunder_wav;
  //  public FileHandle water-dripping-in-cave_wav;

    public void init(AssetManager manager){
        init(manager,false);
    }
    public void init(AssetManager manager,boolean finishLoading){
        if(isInit)return;
        attack_bat_wav = Gdx.files.internal("sounds/attack_bat.wav");
        attack_lion_wav = Gdx.files.internal("sounds/attack_lion.wav");
        attack_ogre_wav = Gdx.files.internal("sounds/attack_ogre.wav");
        attack_slime_wav = Gdx.files.internal("sounds/attack_slime.wav");
        attack_spudi_wav = Gdx.files.internal("sounds/attack_spudi.wav");
        attacked_bat_wav = Gdx.files.internal("sounds/attacked_bat.wav");
        attacked_lion_wav = Gdx.files.internal("sounds/attacked_lion.wav");
        attacked_ogre_wav = Gdx.files.internal("sounds/attacked_ogre.wav");
        attacked_slime_wav = Gdx.files.internal("sounds/attacked_slime.wav");
        attacked_spudi_wav = Gdx.files.internal("sounds/attacked_spudi.wav");
        cheating_wav = Gdx.files.internal("sounds/cheating.wav");
        death_bat_wav = Gdx.files.internal("sounds/death_bat.wav");
        death_lion_wav = Gdx.files.internal("sounds/death_lion.wav");
        death_ogre_wav = Gdx.files.internal("sounds/death_ogre.wav");
        death_slime_wav = Gdx.files.internal("sounds/death_slime.wav");
        death_spudi_wav = Gdx.files.internal("sounds/death_spudi.wav");
        dungeon_ambiance_wav = Gdx.files.internal("sounds/dungeon_ambiance.wav");
        fireball_wav = Gdx.files.internal("sounds/fireball.wav");
        freeze_wav = Gdx.files.internal("sounds/freeze.wav");
        rain_mp3 = Gdx.files.internal("sounds/rain.mp3");
        shield_wav = Gdx.files.internal("sounds/shield.wav");
        spawn_bat_wav = Gdx.files.internal("sounds/spawn_bat.wav");
        spawn_lion_wav = Gdx.files.internal("sounds/spawn_lion.wav");
        spawn_ogre_mp3 = Gdx.files.internal("sounds/spawn_ogre.mp3");
        spawn_spudi_wav = Gdx.files.internal("sounds/spawn_spudi.wav");
        spown_slime_wav = Gdx.files.internal("sounds/spown_slime.wav");
        strong_wav = Gdx.files.internal("sounds/strong.wav");
        thunder_wav = Gdx.files.internal("sounds/thunder.wav");
     //   water-dripping-in-cave_wav = Gdx.files.internal("sounds/water-dripping-in-cave.wav");
        if(finishLoading){
            manager.finishLoading();
            onLoadDone(manager);
        }
        isInit=true;
    }
    public void onLoadDone(AssetManager manager){
        if(isLoad)return;
        ResSoundsA.onLoadDone();
        isLoad=true;
    }
    public void dispose(AssetManager manager){
        if(isInit){
            attack_bat_wav = null;
            attack_lion_wav = null;
            attack_ogre_wav = null;
            attack_slime_wav = null;
            attack_spudi_wav = null;
            attacked_bat_wav = null;
            attacked_lion_wav = null;
            attacked_ogre_wav = null;
            attacked_slime_wav = null;
            attacked_spudi_wav = null;
            cheating_wav = null;
            death_bat_wav = null;
            death_lion_wav = null;
            death_ogre_wav = null;
            death_slime_wav = null;
            death_spudi_wav = null;
            dungeon_ambiance_wav = null;
            fireball_wav = null;
            freeze_wav = null;
            rain_mp3 = null;
            shield_wav = null;
            spawn_bat_wav = null;
            spawn_lion_wav = null;
            spawn_ogre_mp3 = null;
            spawn_spudi_wav = null;
            spown_slime_wav = null;
            strong_wav = null;
            thunder_wav = null;
        //    water-dripping-in-cave_wav = null;
        }
        if(isLoad){
        }
        ResSoundsA.dispose();
        isInit=false;
        isLoad=false;
    }
}
