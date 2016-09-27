package net.atherialrunes.practiceserver.api.handler.handlers.rank;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.entity.Player;

/**
 * Created by Matthew E on 9/21/2016.
 */
public enum Rank {

    DEFAULT(0, "&7%name%: &f%msg%", "Default", "%namecolor%"),
    SUB(1, "&a&lS &7%name%: &f%msg%", "Sub", "&a&lS %namecolor%"),
    SUBPLUS(2, "&6&lS+ &7%name%: &f%msg%", "SubPlus", "&6&lS+ %namecolor%"),
    SUBLIFE(3, "&3&lS+ &7%name%: &f%msg%", "SubLife", "&3&lS+ %namecolor%"),
    PMOD(4, "&f&lPMOD &7%name%: &f%msg%", "PMOD", "&f&lPMOD %namecolor%"),
    GAMEMASTER(5, "&b&lGM &7%name%: &f%msg%", "GameMaster", "&b&lGM %namecolor%"),
    DEVELOPER(6, "&3&lDEV &7%name%: &f%msg%", "Developer", "&3&lDEV %namecolor%");

    private int id;
    private String placeHolder;
    private String name;
    private String prefix;

    Rank(int id, String placeHolder, String name, String prefix) {
        this.id = id;
        this.placeHolder = placeHolder;
        this.name = name;
        this.prefix = prefix;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public static String getChatPrefix(Player player) {
        GamePlayer gp = GameAPI.getGamePlayer(player);
        String prefix = gp.getRank().getPrefix();
        prefix = prefix.replaceAll("%namecolor%", gp.getAlignment().getChatPrefix());
        return Utils.colorCodes(prefix + player.getName() + ": &f");
    }

    public static boolean isGM(String name) {
        GamePlayer gp = GameAPI.getGamePlayer(name);
        return ((gp.getRank() == GAMEMASTER) || (gp.getRank() == DEVELOPER) || (name.equals("matt11matthew")));
    }

    public static boolean isPMOD(String name) {
        GamePlayer gp = GameAPI.getGamePlayer(name);
        return ((isGM(name)) || (gp.getRank() == PMOD));
    }

    public static boolean isRank(String rank) {
        return true;
    }

    public static boolean isSub(String name) {
        GamePlayer gp = GameAPI.getGamePlayer(name);
        return ((isPMOD(name)) || (gp.getRank() == SUB) || (gp.getRank() == SUBPLUS) || (gp.getRank() == SUBLIFE));
    }
}
