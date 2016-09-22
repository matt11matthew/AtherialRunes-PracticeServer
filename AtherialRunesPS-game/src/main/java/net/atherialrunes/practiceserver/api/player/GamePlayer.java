package net.atherialrunes.practiceserver.api.player;

import lombok.Getter;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.database.EnumData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {

    @Getter
    private String name;

    @Getter
    private UUID uniqueId;

    @Getter
    private long firstLogin;

    private Player player = null;

    public GamePlayer(Player player) {
        this.player = player;
        this.name = player.getName();
        this.uniqueId = player.getUniqueId();
        this.firstLogin = (long) DatabaseAPI.getInstance().getData(EnumData.FIRST_LOGIN, uniqueId);
    }

    public Player getPlayer() {
        if (player == null) {
            player = Bukkit.getPlayerExact(name);
        }
        return player;
    }
}
