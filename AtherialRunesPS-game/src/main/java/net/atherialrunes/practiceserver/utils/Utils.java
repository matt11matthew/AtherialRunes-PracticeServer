package net.atherialrunes.practiceserver.utils;

import org.bukkit.ChatColor;

public class Utils {

    public static String colorCodes(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
