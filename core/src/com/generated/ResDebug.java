package com.generated;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.assets.AssetManager;
import com.d954mas.assetpacker.UnloadableTextureAtlas;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
public class ResDebug {
    public static ResDebug res=new ResDebug();
    private boolean isLoad;
    private boolean isInit;
    public ResAtlas_atlas atlas_atlas=new ResAtlas_atlas();
    public FileHandle uiskin_atlas;
    public FileHandle uiskin_png;
    public Skin uiskin_json;

    public void init(AssetManager manager){
        init(manager,false);
    }
    public void init(AssetManager manager,boolean finishLoading){
        if(isInit)return;
            atlas_atlas.init(manager);
        uiskin_atlas = Gdx.files.internal("debug/uiskin.atlas");
        uiskin_png = Gdx.files.internal("debug/uiskin.png");
        manager.load("debug/uiskin.json", Skin.class);
        if(finishLoading){
            manager.finishLoading();
            onLoadDone(manager);
        }
        isInit=true;
    }
    public void onLoadDone(AssetManager manager){
        if(isLoad)return;
        atlas_atlas.onLoadDone(manager);
        uiskin_json = manager.get("debug/uiskin.json");
        ResDebugA.onLoadDone();
        isLoad=true;
    }
    public void dispose(AssetManager manager){
        if(isInit){
            atlas_atlas.dispose(manager);
            uiskin_atlas = null;
            uiskin_png = null;
        }
        if(isLoad){
        }
        ResDebugA.dispose();
        isInit=false;
        isLoad=false;
    }
    public static class ResAtlas_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public AtlasRegion empty;
        public AtlasRegion white;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("debug/atlas_atlas/atlas.atlas", TextureAtlasData.class);
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
            atlas.load((TextureAtlasData)manager.get("debug/atlas_atlas/atlas.atlas"));
            empty = atlas.findRegion("empty");
            white = atlas.findRegion("white");
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("debug/atlas_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
            }
            isInit=false;
            isLoad=false;
        }
    }
}
