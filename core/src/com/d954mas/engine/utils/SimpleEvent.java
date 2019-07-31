package com.d954mas.engine.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SimpleEvent {
    Map<String, Runnable> listeners = new HashMap<>();
    boolean removeOnException = false;

    public SimpleEvent() {}

    public SimpleEvent(boolean removeOnException) {
        this.removeOnException = removeOnException;
    }

    public void listen(String id, Runnable r) {
        listeners.put(id, r);
    }

    public void fire() {
        for (Iterator<Runnable> iterator = listeners.values().iterator(); iterator.hasNext(); ) {
            Runnable listener = iterator.next();
            try {
                listener.run();
            } catch (Throwable e) {
                if (removeOnException) {
                    iterator.remove();
                }
            }
        }
    }

    public void removeListener(String id) {
        listeners.remove(id);
    }
}
