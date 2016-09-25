package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.HandlerManager;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.bank.BankHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.damage.DamageHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.enchant.EnchantHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.enchant.OrbHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.health.HealthHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.item.ItemHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.party.ScoreboardHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.PlayerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.CommandRoll;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.CommandSync;
import net.atherialrunes.practiceserver.api.handler.handlers.pvp.PvPHandlers;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.RankHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.commands.CommandSetRank;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.SpawnerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.commands.CommandHideMs;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.commands.CommandShowMs;
import net.atherialrunes.practiceserver.api.handler.handlers.staff.StaffHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.VendorHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.foodvendor.FoodVendor;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.itemvendor.ItemVendor;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.misc.Healer;
import net.atherialrunes.practiceserver.api.handler.handlers.world.WorldHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.ZoneHandler;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class PracticeServer extends JavaPlugin {

    public static PracticeServer instance;

    public static PracticeServer getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        registerHandlers();
        registerCommands();
        registerVendors();
        Utils.load();
        Bukkit.getServer().setWhitelist(true);
    }

    private void registerHandlers() {
        HandlerManager.registerHandler(new PlayerHandler());
        HandlerManager.registerHandler(new SpawnerHandler());
        HandlerManager.registerHandler(new HealthHandler());
      //  HandlerManager.registerHandler(new PartyHandler());
        HandlerManager.registerHandler(new ScoreboardHandler());
        HandlerManager.registerHandler(new DamageHandler());
       HandlerManager.registerHandler(new VendorHandler());
        HandlerManager.registerHandler(new StaffHandler());
        HandlerManager.registerHandler(new ItemHandler());
        HandlerManager.registerHandler(new ZoneHandler());
        HandlerManager.registerHandler(new BankHandler());
        HandlerManager.registerHandler(new PvPHandlers());
        HandlerManager.registerHandler(new MobHandler());
        HandlerManager.registerHandler(new OrbHandler());
        HandlerManager.registerHandler(new EnchantHandler());
        HandlerManager.registerHandler(new DatabaseAPI());
        HandlerManager.registerHandler(new RankHandler());
        HandlerManager.registerHandler(new WorldHandler());
        HandlerManager.loadHandlers();
    }

    private void registerVendors() {
        VendorHandler.registerVendor(new ItemVendor("Item Vendor", 18));
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
