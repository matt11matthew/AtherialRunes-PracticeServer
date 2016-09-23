package net.atherialrunes.practiceserver.utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class Utils {

    public static String colorCodes(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean isAxe(ItemStack wep) {
        return ((wep.getType() != null) && (wep.getType().toString().contains("AXE")));
    }
}
