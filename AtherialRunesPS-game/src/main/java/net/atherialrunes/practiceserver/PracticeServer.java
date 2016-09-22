package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.handler.HandlerManager;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.player.PlayerHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PracticeServer extends JavaPlugin {

    static PracticeServer instance;

    public static PracticeServer getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        registerHandlers();
    }

    private void registerHandlers() {
        HandlerManager.registerHandler(new DatabaseAPI());
        HandlerManager.registerHandler(new PlayerHandler());
        HandlerManager.loadHandlers();
    }

    public void onDisable() {
        HandlerManager.unloadHandlers();
    }
}
