package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.api.player.GamePlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameAPI {

    public static Map<String, GamePlayer> GAMEPLAYERS = new ConcurrentHashMap<>();

    public static GamePlayer getGamePlayer(Player player) {
        return GAMEPLAYERS.get(player.getName());
    }

    public static void handleLogin(Player player) {
        GAMEPLAYERS.put(player.getUniqueId().toString(), new GamePlayer(player));
    }
}
