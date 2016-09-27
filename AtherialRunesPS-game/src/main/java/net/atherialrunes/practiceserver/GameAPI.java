package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class GameAPI {

    public static Map<String, GamePlayer> GAMEPLAYERS = new ConcurrentHashMap<>();

    public static GamePlayer getGamePlayer(Player player) {
        return GAMEPLAYERS.get(player.getUniqueId().toString());
    }

    public static void handleLogin(Player player) {
        handleLogin(player.getUniqueId(), player.getName());
    }

    public static void handleLogin(UUID uuid, String name) {
        GAMEPLAYERS.put(uuid.toString(), new GamePlayer(name, uuid));
    }

    public static void handleLogout(Player player) {
        GamePlayer gp = getGamePlayer(player);
        if (gp.isInCombat()) {
            gp.kill();
            Bukkit.getServer().broadcastMessage(Utils.colorCodes("&7" + player.getName()) + " has logged out while in combat!");
        }
        if (gp.isSave()) {
            gp.upload();
        }
        GAMEPLAYERS.remove(player.getUniqueId().toString());
    }

    public static GamePlayer getGamePlayer(String name) {
        return getGamePlayer(Bukkit.getPlayerExact(name));
    }
}
