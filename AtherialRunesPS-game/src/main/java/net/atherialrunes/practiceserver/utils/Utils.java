package net.atherialrunes.practiceserver.utils;

import org.bukkit.ChatColor;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class Utils {

    public static String colorCodes(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
