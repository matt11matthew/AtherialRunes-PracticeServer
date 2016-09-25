package net.atherialrunes.practiceserver.api.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class HandlerManager {

    public static ConcurrentHashMap<String, Handler> handlers = new ConcurrentHashMap<>();
    public static List<Handler> handlersToLoad = new ArrayList<>();
    public static List<Handler> handlersToUnLoad = new ArrayList<>();

    public static void loadHandlers() {
        handlersToLoad.forEach(handlerToLoad -> {
            System.out.println("[" + handlerToLoad.getClass().getSimpleName() + "] Loading...");
            handlers.put(handlerToLoad.getClass().getSimpleName(), handlerToLoad);
        });
        handlers.values().forEach(handler -> {
            System.out.println("-----------------------------------------");
            System.out.println("[" + handler.getClass().getSimpleName() + "] Has been loaded");
            System.out.println("-----------------------------------------");
            handler.onLoad();
        });
        handlersToLoad.clear();
    }

    public static void unloadHandlers() {
        handlersToUnLoad.forEach(handlerToLUnload -> {
            System.out.println("[" + handlerToLUnload.getClass().getSimpleName() + "] Unloading...");
        });
        handlers.values().forEach(handler -> {
            System.out.println("-----------------------------------------");
            System.out.println("[" + handler.getClass().getSimpleName() + "] has been unloaded");
            System.out.println("-----------------------------------------");
            handler.onUnload();
        });
        handlersToUnLoad.clear();
    }

    public static void registerHandler(Handler handler) {
        handlersToLoad.add(handler);
    }
}
