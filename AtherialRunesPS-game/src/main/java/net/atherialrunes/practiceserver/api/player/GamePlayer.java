package net.atherialrunes.practiceserver.api.player;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.database.EnumData;
import net.atherialrunes.practiceserver.api.handler.database.EnumOperators;
import net.atherialrunes.practiceserver.api.handler.handlers.party.Party;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class GamePlayer {

    private String name;
    private UUID uniqueId;
    private long firstLogin;
    private Rank rank;
    private Player player = null;
    private double gems;
    private boolean hasParty;
    private Party party;

    public GamePlayer(Player player) {
        this.player = player;
        new GamePlayer(player.getName(), player.getUniqueId());
    }

    public GamePlayer(String name, UUID uuid) {
        this.name = player.getName();
        this.uniqueId = player.getUniqueId();
        this.firstLogin = (long) DatabaseAPI.getInstance().getData(EnumData.FIRST_LOGIN, uniqueId);
        this.rank = Rank.valueOf(DatabaseAPI.getInstance().getData(EnumData.RANK, uniqueId) + "");
        this.gems = (double) DatabaseAPI.getInstance().getData(EnumData.GEMS, uniqueId);
        this.hasParty = false;
        setParty(null);
    }

    public Player getPlayer() {
        if (player == null) {
            player = Bukkit.getPlayerExact(name);
        }
        return player;
    }

    public void upload() {
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.RANK, getRank(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.GEMS, getGems(), true);
    }

    public void msg(String msg) {
        getPlayer().sendMessage(Utils.colorCodes(msg));
    }

    public int getHP() {
        return (int) getPlayer().getHealth();
    }
}
