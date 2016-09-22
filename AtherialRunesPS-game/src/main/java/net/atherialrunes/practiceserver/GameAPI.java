package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.player.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
        getGamePlayer(player).upload();
        GAMEPLAYERS.remove(player.getUniqueId().toString());
    }

    public static GamePlayer getGamePlayer(String name) {
        return getGamePlayer(Bukkit.getPlayerExact(name));
    }
}
