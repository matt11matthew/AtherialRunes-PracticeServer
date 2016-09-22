package net.atherialrunes.practiceserver.api.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    @Getter
    private String name;

    @Getter
    private UUID uniqueId;

    private Player player = null;

    public GamePlayer(Player player) {
        this.player = player;
        this.name = player.getName();
        this.uniqueId = player.getUniqueId();
    }

    public Player getPlayer() {
        if (player == null) {
            player = Bukkit.getPlayerExact(name);
        }
        return player;
    }
}
