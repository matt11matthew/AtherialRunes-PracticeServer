package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.handlers.damage.DamageHandler;
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
import net.atherialrunes.practiceserver.api.handler.handlers.health.HealthHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.staff.StaffHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.VendorHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.foodvendor.FoodVendor;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.itemvendor.ItemVendor;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.misc.Healer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class PracticeServer extends JavaPlugin {

    static PracticeServer instance;

    public static PracticeServer getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        registerHandlers();
        registerCommands();
        registerVendors();
    }

    private void registerHandlers() {
        HandlerManager.registerHandler(new DatabaseAPI());
        HandlerManager.registerHandler(new PlayerHandler());
        HandlerManager.registerHandler(new SpawnerHandler());
        HandlerManager.registerHandler(new HealthHandler());
        HandlerManager.registerHandler(new PartyHandler());
        HandlerManager.registerHandler(new ScoreboardHandler());
        HandlerManager.registerHandler(new DamageHandler());
        HandlerManager.registerHandler(new VendorHandler());
        HandlerManager.registerHandler(new StaffHandler());
        HandlerManager.loadHandlers();
    }

    private void registerVendors() {
        VendorHandler.registerVendor(new ItemVendor("Item Vendor", 9));
        VendorHandler.registerVendor(new Healer("Healer"));
        VendorHandler.registerVendor(new FoodVendor("Food Vendor", 18));
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
