package net.atherialrunes.practiceserver.api.handler.handlers.player;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class PlayerHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
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
}