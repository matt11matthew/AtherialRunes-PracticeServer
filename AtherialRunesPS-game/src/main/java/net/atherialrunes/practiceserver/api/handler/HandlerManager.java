package net.atherialrunes.practiceserver.api.handler;

import java.util.concurrent.ConcurrentHashMap;

public class HandlerManager {

    public static ConcurrentHashMap<String, Handler> handlers = new ConcurrentHashMap<>();

    public static void loadHandlers() {
        handlers.values().forEach(Handler::onLoad);
    }

    public static void unloadHandlers() {
        handlers.values().forEach(Handler::onLoad);
    }
}
