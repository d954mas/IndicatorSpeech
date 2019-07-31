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
        public AtlasRegion empty;
        public AtlasRegion white;

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
            empty = atlas.findRegion("empty");
            white = atlas.findRegion("white");
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
