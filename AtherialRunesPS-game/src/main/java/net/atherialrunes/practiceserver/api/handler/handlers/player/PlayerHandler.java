package net.atherialrunes.practiceserver.api.handler.handlers.player;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandToggle;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandToggleChaos;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandToggleDebug;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandTogglePvP;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class PlayerHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        task();

        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandToggle("toggle", Arrays.asList("toggles")));
        cm.registerCommand(new CommandToggleDebug("debug", Arrays.asList("toggledebug")));
        cm.registerCommand(new CommandTogglePvP("togglepvp", Arrays.asList("toggleplayervsplayer")));
        cm.registerCommand(new CommandToggleChaos("togglechaos", Arrays.asList("togglechaotic", "togglechao")));
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        DatabaseAPI.getInstance().requestPlayer(e.getUniqueId(), e.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        String MOTD = asCentered(ChatColor.WHITE + "" + ChatColor.BOLD + "Atherial Runes Practice Server Patch 1.0");
        String DMOTD = asCentered(ChatColor.GRAY + "" + ChatColor.ITALIC + "Donate at http://atherialrunes.buycraft.net/ for perks!");
        GameAPI.getGamePlayer(e.getPlayer()).msg(MOTD);
        GameAPI.getGamePlayer(e.getPlayer()).msg(DMOTD);
        GameAPI.handleLogin(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        GameAPI.handleLogout(e.getPlayer());
    }

    //Move this wherever quickly put this here :/
    public static String asCentered(String text) {
        StringBuilder builder = new StringBuilder(text);
        char space = ' ';
        int distance = (45 - text.length()) / 2;
        for (int i = 0; i < distance; ++i) {
            builder.insert(0, space);
            builder.append(space);
        }
        return builder.toString();
    }

    public void task() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    GamePlayer gp = GameAPI.getGamePlayer(player);
                    player.setPlayerListName(gp.getAlignment().getTabPrefix() + player.getName());
                });
            }
        }, 5L, 5L);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }
        if (e.getClickedInventory().getTitle().equals("Toggles")) {
            e.setCancelled(true);
            if ((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR) && (e.getCurrentItem().getType() == Material.INK_SACK)) {
                player.performCommand(e.getCurrentItem().getItemMeta().getLore().get(0));
                player.closeInventory();
                player.performCommand("toggles");
                return;
            }
        }
    }
}