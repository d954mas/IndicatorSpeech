package com.generated;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.d954mas.assetpacker.UnloadableTextureAtlas;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
public class ResUi {
    public static ResUi res=new ResUi();
    private boolean isLoad;
    private boolean isInit;
    public ResAtlas_atlas atlas_atlas=new ResAtlas_atlas();
    public FileHandle roboto_Regular_ttf;

    public void init(AssetManager manager){
        init(manager,false);
    }
    public void init(AssetManager manager,boolean finishLoading){
        if(isInit)return;
            atlas_atlas.init(manager);
        roboto_Regular_ttf = Gdx.files.internal("ui/Roboto_Regular.ttf");
        if(finishLoading){
            manager.finishLoading();
            onLoadDone(manager);
        }
        isInit=true;
    }
    public void onLoadDone(AssetManager manager){
        if(isLoad)return;
        atlas_atlas.onLoadDone(manager);
        ResUiA.onLoadDone();
        isLoad=true;
    }
    public void dispose(AssetManager manager){
        if(isInit){
            atlas_atlas.dispose(manager);
            roboto_Regular_ttf = null;
        }
        if(isLoad){
        }
        ResUiA.dispose();
        isInit=false;
        isLoad=false;
    }
    public static class ResAtlas_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public AtlasRegion activnoe_slovo;
        public AtlasRegion empty;
        public AtlasRegion monster_attack_icon;
        public AtlasRegion monster_def_icon;
        public AtlasRegion mosnster_def;
        public AtlasRegion mosnster_hp_def;
        public AtlasRegion plashka_hp_def_player;
        public AtlasRegion plashka_mp;
        public AtlasRegion ui;
        public AtlasRegion white;
        public AtlasRegion word_null;
        public AtlasRegion plashka_spisok;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("ui/atlas_atlas/atlas.atlas", TextureAtlasData.class);
            if(finishLoading){
                manager.finishLoading();
                onLoadDone(manager);
            }
            isInit=true;
        }
        protected void onLoadDone(AssetManager manager){
            if(isLoad)return;
            if(atlas==null){
                atlas=new UnloadableTextureAtlas();
            }
            atlas.load((TextureAtlasData)manager.get("ui/atlas_atlas/atlas.atlas"));
            activnoe_slovo = atlas.findRegion("activnoe_slovo");
            empty = atlas.findRegion("empty");
            monster_attack_icon = atlas.findRegion("monster_attack_icon");
            monster_def_icon = atlas.findRegion("monster_def_icon");
            mosnster_def = atlas.findRegion("mosnster_def");
            mosnster_hp_def = atlas.findRegion("mosnster_hp_def");
            plashka_hp_def_player = atlas.findRegion("plashka_hp_def_player");
            plashka_mp = atlas.findRegion("plashka_mp");
            ui = atlas.findRegion("ui");
            white = atlas.findRegion("white");
            word_null = atlas.findRegion("word_null");
            plashka_spisok = atlas.findRegion("plashka_spisok");
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("ui/atlas_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
            }
            isInit=false;
            isLoad=false;
        }
    }
}
