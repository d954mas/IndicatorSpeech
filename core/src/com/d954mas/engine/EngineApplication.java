package com.d954mas.engine;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.d954mas.assetpacker.UnloadableTextureAtlasLoader;
import com.d954mas.debug.DebugInfoStage;
import com.d954mas.engine.services.Service;
import com.d954mas.engine.services.Services;
import com.d954mas.engine.utils.GeneratedFontSkinLoader;
import com.d954mas.engine.utils.PerformanceLogger;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.generated.ResDebug;
import com.generated.ResUi;
import com.github.czyzby.kiwi.log.LoggerService;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

//Skins https://github.com/czyzby/gdx-skins

public abstract class EngineApplication extends ApplicationAdapter {
	private static String FONT_CHARACTERS = FreeTypeFontGenerator.DEFAULT_CHARS + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

	private AssetManager manager;
	private PerformanceLogger performanceLogger;
	private DebugInfoStage debugInfoStage;
	private InputMultiplexer mainInput;
	private Map<String,BitmapFont> fontsByName;


	public EngineApplication(@Nullable Map<Class<? extends Service>, Service> nativeServices) {
		super();
		if(nativeServices != null) Services.addOrReplaceServices(nativeServices);
	}

	@Override
	public void create () {
		LoggerService.simpleClassNames(true);
		LoggerService.logTime(true);
		super.create();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug("Game","create");
		Services.get(SpeechService.class);
		performanceLogger = new PerformanceLogger();
		mainInput = new InputMultiplexer();
		Gdx.input.setInputProcessor(mainInput);

		fontsByName = initFonts();


		manager=new AssetManager();
		manager.setErrorListener((asset, throwable) -> Gdx.app.error("Assets", "Could not load asset " + asset.fileName + " ", throwable));
		FreeTypeFontGeneratorLoader generatorLoader = new FreeTypeFontGeneratorLoader(new InternalFileHandleResolver());

		manager.setLoader(FreeTypeFontGenerator.class, generatorLoader);
		manager.setLoader(TextureAtlas.TextureAtlasData.class, new UnloadableTextureAtlasLoader(new InternalFileHandleResolver()));
		SkinLoader ldr = new GeneratedFontSkinLoader( new InternalFileHandleResolver(), fontsByName);
		manager.setLoader(Skin.class, ldr);
		Texture.setAssetManager(manager);

		//preload debug and ui for loading scene
		ResDebug.res.init(manager,true);
		ResUi.res.init(manager,true);
		createDebug();

		Services.get(SpeechService.class).cancelCurrentTask();
	}


	private  FreeTypeFontGenerator.FreeTypeFontParameter fontParameter(float size){
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.size = (int)Math.ceil(size);
		param.minFilter = Texture.TextureFilter.Nearest;
		param.magFilter = Texture.TextureFilter.MipMapLinearNearest;
		param.characters = FONT_CHARACTERS;
		return  param;
	}
	//Грузим все нужные шрифт.
	private Map<String, BitmapFont> initFonts() {
		Gdx.app.debug("GeneratedFontSkinLoader", "Loading fonts...");
		FileHandle fontFile = Gdx.files.internal("ui/Roboto_Regular.ttf");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);

		Map<String,BitmapFont> fontsByName = new HashMap<>();

		fontsByName.put("huge-font", generator.generateFont(fontParameter(128)));
		fontsByName.put("big-font", generator.generateFont(fontParameter(64)));
		fontsByName.put("default-font", generator.generateFont(fontParameter(40)));
		fontsByName.put("debug-font", generator.generateFont(fontParameter(40)));
		generator.dispose();
		return fontsByName;
	}

	private void createDebug(){
		debugInfoStage = new DebugInfoStage();
		debugInfoStage.setInput(mainInput);
	}

	@Override
	public void pause() {
		super.pause();
		Gdx.app.debug("Game","pause");
		Services.pause();
	}

	private boolean needStartRecognotion = true;
	private void update(float dt){
		Services.update(dt);
		performanceLogger.update(dt);
		debugInfoStage.update(dt);
		if (needStartRecognotion){
			needStartRecognotion = false;
			Services.get(SpeechService.class).recognitionStart();
		}
	}

	@Override
	public void render(){
		super.render();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(Gdx.graphics.getDeltaTime());
		debugInfoStage.draw();
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.debug("Game","dispose");
		Services.dispose();
		ResDebug.res.dispose(manager);
		ResUi.res.dispose(manager);
		manager.dispose();
	}

	@Override
	public void resume() {
		super.resume();
		Services.resume();
		Gdx.app.debug("Game", "resume");
		while (!manager.update()){ Gdx.app.log("Game","reload Assets"); }

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Services.resize(width,height);
		Gdx.app.debug("Game","resize("+width+" "+height+")");
		debugInfoStage.resize(width,height);
	}

	public InputMultiplexer getMainInput(){
		return  mainInput;
	}
}
