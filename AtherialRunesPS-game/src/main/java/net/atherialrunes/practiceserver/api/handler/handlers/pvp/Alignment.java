package net.atherialrunes.practiceserver.api.handler.handlers.pvp;

import net.atherialrunes.practiceserver.utils.Utils;

/**
 * Created by Matthew E on 9/24/2016 at 11:07 AM.
 */
public enum Alignment {

    LAWFUL(0, "Lawful", "&7", "&f"),
    NEUTRAL(1, "Neutral", "&e", "&e"),
    CHAOTIC(2, "Chaotic", "&c", "&c"),
    ;

    private int id;
    private String name;
    private String chatPrefix;
    private String tabPrefix;

    Alignment(int id, String name, String chatPrefix, String tabPrefix) {
        this.id = id;
        this.name = name;
        this.chatPrefix = chatPrefix;
        this.tabPrefix = tabPrefix;
    }

    public String getTabPrefix() {
        return Utils.colorCodes(tabPrefix);
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
