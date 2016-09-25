package net.atherialrunes.practiceserver.utils;

import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class Utils {

    public static String colorCodes(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String nmsver;

    public static void load() {
        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
    }

    public static void sendActionBar(Player player, String message) {
        try {
            Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object p = c1.cast(player);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
            Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
            Method m1 = c1.getDeclaredMethod("getHandle");
            Object h = m1.invoke(p);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getTier(ItemStack item) {
        return Tier.getTier(item);
    }

    public static boolean isArmor(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("CHESTPLATE")) || (currentItem.getType().toString().contains("HELMET")) || (currentItem.getType().toString().contains("LEGGINGS")) || (currentItem.getType().toString().contains("BOOTS")));
    }

    public static boolean isWeapon(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("SWORD")) || (currentItem.getType().toString().contains("AXE")) || (currentItem.getType().toString().contains("BOW")));
    }

    public boolean isTool(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("PICKAXE")) || (currentItem.getType().toString().contains("SPADE")) || (currentItem.getType().toString().contains("HOE")));
    }

    public static boolean isPickaxe(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("PICKAXE")));
    }

    public static boolean isShovel(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("SPADE")));
    }

    public static boolean isHoe(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("HOE")));
    }

    public static boolean isAxe(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("AXE")));
    }

    public static boolean isSword(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("SWORD")));
    }

    public static boolean isBow(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("BOW")));
    }

    public static boolean isHelmet(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("HELMET")));
    }

    public static boolean isChestplate(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("CHESTPLATE")));
    }

    public static boolean isLeggings(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("LEGGINGS")));
    }

    public static boolean isBoots(ItemStack currentItem) {
        return ((currentItem.getType().toString().contains("BOOTS")));
    }

    public static int convertStringToInt(String s) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {

        }
        return i;
    }

    public static String parseMilis(long l) {
        long sec = TimeUnit.MILLISECONDS.toSeconds(l);
        if (sec - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) > 0) {
            sec = sec - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
        long hour = 0;
        long min = 0;
        while (sec >= 60) {
            sec -= 60;
            min += 1;
        }
        while (min >= 60) {
            min -= 60;
            hour += 1;
        }
        String returnString = "&a" + hour + "&lh &a" + min + "&lm &a" + sec + "&ls";
        return returnString;
    }

    public static boolean reboot(long l) {
        return (System.currentTimeMillis() >= l);
    }

    public static long convertToMillis(int hours) {
        try {
            return System.currentTimeMillis() + TimeUnit.HOURS.toMillis(hours);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long convertSecondsToMillis(int seconds) {
        try {
            return System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds);
        } catch (Exception e) {
            return 0;
        }
    }
}
