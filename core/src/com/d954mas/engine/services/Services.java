package com.d954mas.engine.services;

import com.badlogic.gdx.Gdx;
import com.d954mas.engine.services.iface.ScreenService;
import com.d954mas.engine.services.impl.ScreenServiceDefault;
import com.d954mas.engine.utils.Cs;
import com.d954mas.game.indicator2019.speech.services.iface.SpeechService;
import com.d954mas.game.indicator2019.speech.services.impl.SpeechServiceDefault;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



public class Services {
    private static Services services;
    private final Map<Class<? extends Service>, Service> defaultServiceMap=generateDefaultServiceMap();

    private Set<Class<? extends Service>> initedServices;
    private Map<Class<? extends Service>, Service> allServices;
    private Services() {
        allServices = new HashMap<>();
        initedServices=new HashSet<>();
        allServices.putAll(defaultServiceMap);
    }

    private static Services get(){
        if(services==null)services=new Services();
        return services;
    }



    public static <T extends Service> T get(Class<T> iface) {
        //search among real services
        T t = (T) get().allServices.get(iface);
        if (t == null) {
            throw new RuntimeException("Service with class:'" + iface.getSimpleName() + "' can't be found");
        }
        initIfNescessary(iface, t);
        return t;
    }

    private static <T extends Service> void initIfNescessary(Class<T> iface, Service t) {
        if (!get().initedServices.contains(iface)) {
            Gdx.app.log("Services","Init service: " + iface.getSimpleName());
            t.init();
            get().initedServices.add(iface);
        }
    }

    public static void addOrReplaceService(Class<? extends Service> iface, Service service) {
        if(get().initedServices.contains(iface)){
            Service oldService=get().allServices.get(iface);
           // oldService.pause(); todo think about it
            oldService.dispose();
            get().initedServices.remove(iface);
        }
        get().allServices.put(iface,service);
    }

    public static void addOrReplaceServices(Map<Class<? extends Service>,? extends Service> services){
        for(Map.Entry<Class<? extends Service>,? extends Service> service:services.entrySet()){
            addOrReplaceService(service.getKey(), service.getValue());
        }

    }

    public static void pause(){
        for(Class<? extends Service> iface:get().initedServices){
            get().allServices.get(iface).pause();
        }
    }
    public static void resume(){
        for(Class<? extends Service> iface:get().initedServices){
            get().allServices.get(iface).pause();
        }
    }
    public static void resize(int width,int height){
        for(Class<? extends Service> iface:get().initedServices){
            get().allServices.get(iface).resize(width,height);
        }
    }

    public static void update(float dt){
        for(Class<? extends Service> iface:get().initedServices){
            get().allServices.get(iface).update(dt);
        }
    }


    //NEED reset because in android static can live after app close.
    public static void dispose(){
        for(Class<? extends Service> iface:get().initedServices){
            get().allServices.get(iface).dispose();
        }
        get().allServices.clear();
        get().initedServices.clear();
        services=null;
    }


    protected static Map<Class<? extends Service>, Service> generateDefaultServiceMap(){
        return Cs.ofM(ScreenService.class,new ScreenServiceDefault(),
                SpeechService.class,new SpeechServiceDefault());
    }




}
