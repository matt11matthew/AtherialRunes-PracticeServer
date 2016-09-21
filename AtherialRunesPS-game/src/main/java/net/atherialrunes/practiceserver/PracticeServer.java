package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.handler.HandlerManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PracticeServer extends JavaPlugin {

    static PracticeServer instance;

    public static PracticeServer getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        HandlerManager.loadHandlers();
    }

    public void onDisable() {
        HandlerManager.unloadHandlers();
    }
}
