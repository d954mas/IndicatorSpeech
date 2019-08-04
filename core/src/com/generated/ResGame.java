package com.generated;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.d954mas.assetpacker.UnloadableTextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
public class ResGame {
    public static ResGame res=new ResGame();
    private boolean isLoad;
    private boolean isInit;
    public ResBat_atlas bat_atlas=new ResBat_atlas();
    public ResLocation_atlas location_atlas=new ResLocation_atlas();
    public ResReg_ghost_atlas reg_ghost_atlas=new ResReg_ghost_atlas();
    public ResSlug_atlas slug_atlas=new ResSlug_atlas();
    public ResSpudi_atlas spudi_atlas=new ResSpudi_atlas();
    public ResTroll_atlas troll_atlas=new ResTroll_atlas();

    public void init(AssetManager manager){
        init(manager,false);
    }
    public void init(AssetManager manager,boolean finishLoading){
        if(isInit)return;
            bat_atlas.init(manager);
            location_atlas.init(manager);
            reg_ghost_atlas.init(manager);
            slug_atlas.init(manager);
            spudi_atlas.init(manager);
            troll_atlas.init(manager);
        if(finishLoading){
            manager.finishLoading();
            onLoadDone(manager);
        }
        isInit=true;
    }
    public void onLoadDone(AssetManager manager){
        if(isLoad)return;
        bat_atlas.onLoadDone(manager);
        location_atlas.onLoadDone(manager);
        reg_ghost_atlas.onLoadDone(manager);
        slug_atlas.onLoadDone(manager);
        spudi_atlas.onLoadDone(manager);
        troll_atlas.onLoadDone(manager);
        ResGameA.onLoadDone();
        isLoad=true;
    }
    public void dispose(AssetManager manager){
        if(isInit){
            bat_atlas.dispose(manager);
            location_atlas.dispose(manager);
            reg_ghost_atlas.dispose(manager);
            slug_atlas.dispose(manager);
            spudi_atlas.dispose(manager);
            troll_atlas.dispose(manager);
        }
        if(isLoad){
        }
        ResGameA.dispose();
        isInit=false;
        isLoad=false;
    }
    public static class ResBat_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public Animation sprite1;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("game/bat_atlas/atlas.atlas", TextureAtlasData.class);
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
            atlas.load((TextureAtlasData)manager.get("game/bat_atlas/atlas.atlas"));
            //region animation sprite1
            Array<AtlasRegion> sprite1Regions = new Array<>();
            sprite1Regions.add(atlas.findRegion("Sprite1"));
            sprite1Regions.add(atlas.findRegion("Sprite2"));
            sprite1Regions.add(atlas.findRegion("Sprite3"));
            sprite1Regions.add(atlas.findRegion("Sprite4"));
            sprite1 = new Animation(1f,sprite1Regions);
            //endregion
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("game/bat_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
                sprite1 = null;
            }
            isInit=false;
            isLoad=false;
        }
    }
    public static class ResLocation_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public AtlasRegion bg1;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("game/location_atlas/atlas.atlas", TextureAtlasData.class);
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
            atlas.load((TextureAtlasData)manager.get("game/location_atlas/atlas.atlas"));
            bg1 = atlas.findRegion("bg1");
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("game/location_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
            }
            isInit=false;
            isLoad=false;
        }
    }
    public static class ResReg_ghost_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public Animation sprite1;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("game/reg_ghost_atlas/atlas.atlas", TextureAtlasData.class);
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
            atlas.load((TextureAtlasData)manager.get("game/reg_ghost_atlas/atlas.atlas"));
            //region animation sprite1
            Array<AtlasRegion> sprite1Regions = new Array<>();
            sprite1Regions.add(atlas.findRegion("Sprite1"));
            sprite1Regions.add(atlas.findRegion("Sprite2"));
            sprite1Regions.add(atlas.findRegion("Sprite3"));
            sprite1Regions.add(atlas.findRegion("Sprite4"));
            sprite1Regions.add(atlas.findRegion("Sprite5"));
            sprite1Regions.add(atlas.findRegion("Sprite6"));
            sprite1Regions.add(atlas.findRegion("Sprite7"));
            sprite1 = new Animation(1f,sprite1Regions);
            //endregion
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("game/reg_ghost_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
                sprite1 = null;
            }
            isInit=false;
            isLoad=false;
        }
    }
    public static class ResSlug_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public Animation sprite1;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("game/slug_atlas/atlas.atlas", TextureAtlasData.class);
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
            atlas.load((TextureAtlasData)manager.get("game/slug_atlas/atlas.atlas"));
            //region animation sprite1
            Array<AtlasRegion> sprite1Regions = new Array<>();
            sprite1Regions.add(atlas.findRegion("Sprite1"));
            sprite1Regions.add(atlas.findRegion("Sprite2"));
            sprite1Regions.add(atlas.findRegion("Sprite3"));
            sprite1Regions.add(atlas.findRegion("Sprite4"));
            sprite1Regions.add(atlas.findRegion("Sprite5"));
            sprite1Regions.add(atlas.findRegion("Sprite6"));
            sprite1Regions.add(atlas.findRegion("Sprite7"));
            sprite1 = new Animation(1f,sprite1Regions);
            //endregion
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("game/slug_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
                sprite1 = null;
            }
            isInit=false;
            isLoad=false;
        }
    }
    public static class ResSpudi_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public Animation sprite1;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("game/spudi_atlas/atlas.atlas", TextureAtlasData.class);
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
            atlas.load((TextureAtlasData)manager.get("game/spudi_atlas/atlas.atlas"));
            //region animation sprite1
            Array<AtlasRegion> sprite1Regions = new Array<>();
            sprite1Regions.add(atlas.findRegion("Sprite1"));
            sprite1Regions.add(atlas.findRegion("Sprite2"));
            sprite1Regions.add(atlas.findRegion("Sprite3"));
            sprite1Regions.add(atlas.findRegion("Sprite4"));
            sprite1Regions.add(atlas.findRegion("Sprite5"));
            sprite1Regions.add(atlas.findRegion("Sprite6"));
            sprite1 = new Animation(1f,sprite1Regions);
            //endregion
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("game/spudi_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
                sprite1 = null;
            }
            isInit=false;
            isLoad=false;
        }
    }
    public static class ResTroll_atlas {
        private boolean isLoad;
        private boolean isInit;
        public UnloadableTextureAtlas atlas;
        public Animation sprite1;

        protected void init(AssetManager manager){
            init(manager,false);
        }
        protected void init(AssetManager manager,boolean finishLoading){
            if(isInit)return;
            manager.load("game/troll_atlas/atlas.atlas", TextureAtlasData.class);
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
            atlas.load((TextureAtlasData)manager.get("game/troll_atlas/atlas.atlas"));
            //region animation sprite1
            Array<AtlasRegion> sprite1Regions = new Array<>();
            sprite1Regions.add(atlas.findRegion("Sprite1"));
            sprite1Regions.add(atlas.findRegion("Sprite2"));
            sprite1Regions.add(atlas.findRegion("Sprite3"));
            sprite1 = new Animation(1f,sprite1Regions);
            //endregion
            isLoad=true;
        }
        protected void dispose(AssetManager manager){
            if(isInit){
                manager.unload("game/troll_atlas/atlas.atlas");
            }
            if(isLoad){
                atlas.dispose();
                sprite1 = null;
            }
            isInit=false;
            isLoad=false;
        }
    }
}
