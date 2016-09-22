package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.handlers.party.PartyHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.party.ScoreboardHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.CommandRoll;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.CommandSync;
import net.atherialrunes.practiceserver.api.handler.HandlerManager;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.player.PlayerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.commands.CommandSetRank;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.SpawnerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.commands.CommandHideMs;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.commands.CommandShowMs;
import net.atherialrunes.practiceserver.api.handler.health.HealthHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class PracticeServer extends JavaPlugin {

    static PracticeServer instance;

    public static PracticeServer getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        registerHandlers();
        registerCommands();
    }

    private void registerHandlers() {
        HandlerManager.registerHandler(new DatabaseAPI());
        HandlerManager.registerHandler(new PlayerHandler());
        HandlerManager.registerHandler(new SpawnerHandler());
        HandlerManager.registerHandler(new HealthHandler());
        HandlerManager.registerHandler(new PartyHandler());
        HandlerManager.registerHandler(new ScoreboardHandler());
        HandlerManager.loadHandlers();
    }

    private void registerCommands() {
        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandRoll("roll", "/roll", "Roll command."));
        cm.registerCommand(new CommandSync("sync"));
        cm.registerCommand(new CommandSetRank("setrank"));
        cm.registerCommand(new CommandShowMs("showms"));
        cm.registerCommand(new CommandHideMs("hidems"));
    }

    public void onDisable() {
        HandlerManager.unloadHandlers();
    }
}
