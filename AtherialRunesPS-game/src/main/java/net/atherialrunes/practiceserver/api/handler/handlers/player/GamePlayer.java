package net.atherialrunes.practiceserver.api.handler.handlers.player;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.database.EnumData;
import net.atherialrunes.practiceserver.api.handler.database.EnumOperators;
import net.atherialrunes.practiceserver.api.handler.handlers.party.Party;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Matthew E on 9/21/2016.
 */
@Data
public class GamePlayer {

    private String name;
    private UUID uniqueId;
    private long firstLogin;
    private Rank rank;
    private Player player = null;
    private double gems;
    private Party party = null;
    private String chatChannel = "local";
    private int hp;
    private int foodLevel;
    private float exp;
    private int level;
    private Location location;

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
        this.hp = (int) DatabaseAPI.getInstance().getData(EnumData.HEALTH, uniqueId);
        this.foodLevel = (int) DatabaseAPI.getInstance().getData(EnumData.FOOD_LEVEL, uniqueId);
        this.exp = (float) DatabaseAPI.getInstance().getData(EnumData.EXP, uniqueId);
        this.level = (int) DatabaseAPI.getInstance().getData(EnumData.LEVEL, uniqueId);
        this.location = getLocationNotParsed();
        getPlayer().teleport(getLocation());
        setParty(null);
    }

    private Location getLocationNotParsed() {
        String location = getLocation().toString();
        return new Location(Bukkit.getWorld(location.split("world=")[1].split(",")[0]), Double.parseDouble(location.split("x=")[1].split(",")[0]), Double.parseDouble(location.split("y=")[1].split(",")[0]),Double.parseDouble(location.split("z=")[1].split(",")[0]));
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
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.HEALTH, getHp(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.FOOD_LEVEL, getFoodLevel(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.EXP, getExp(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.LEVEL, getLevel(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.LOCATION, getLocationParsed(), true);
    }

    private String getLocationParsed() {
        return getLocation().toString();
    }

    public void msg(String msg) {
        getPlayer().sendMessage(Utils.colorCodes(msg));
    }

    public ItemStack getWeapon() {
        return getPlayer().getEquipment().getItemInMainHand();
    }
}
