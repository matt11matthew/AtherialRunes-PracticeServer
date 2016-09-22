package net.atherialrunes.practiceserver.api.handler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class HandlerManager {

    public static ConcurrentHashMap<String, Handler> handlers = new ConcurrentHashMap<>();

    public static void loadHandlers() {
        handlers.values().forEach(handler -> {
            System.out.println("-----------------------------------------");
            System.out.println("[" + handler.getClass().getSimpleName() + "] Loading...");
            System.out.println("-----------------------------------------");
            handler.onLoad();
        });
    }

    public static void unloadHandlers() {
        handlers.values().forEach(handler -> {
            System.out.println("-----------------------------------------");
            System.out.println("[" + handler.getClass().getSimpleName() + "] Unloading...");
            System.out.println("-----------------------------------------");
            handler.onUnload();
        });
    }

    public static void registerHandler(Handler handler) {
        handlers.put(handler.getClass().getSimpleName(), handler);
    }
}
