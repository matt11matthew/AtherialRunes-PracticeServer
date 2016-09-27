package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.api.handler.handlers.mob.armor.ArmorDye;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
/**
 * Created by Matthew E on 9/21/2016.
 */
public enum Tier {

    T1(1, "&f", "armor:leather,weapon:wood", ArmorDye.NONE, "Leather", "Wooden"),
    T2(2, "&a", "armor:chainmail,weapon:stone", ArmorDye.NONE, "Chainmail", "Stone"),
    T3(3, "&b", "armor:iron,weapon:iron", ArmorDye.NONE, "Iron", "Iron"),
    T4(4, "&d", "armor:diamond,weapon:diamond", ArmorDye.NONE, "Diamond", "Diamond"),
    T5(5, "&e", "armor:gold,weapon:gold", ArmorDye.NONE, "Golden", "Golden"),
    T6(6, "&3", "armor:iron,weapon:iron", ArmorDye.NONE, "Titanium", "Titanium"),

    ;

    private int tier;
    private String color;
    private String types;
    private ArmorDye armorDye;
    private String prefix1;
    private String prefix2;

    Tier(int tier, String color, String types, ArmorDye armorDye, String prefix1, String prefix2) {
        this.tier = tier;
        this.color = color;
        this.types = types;
        this.armorDye = armorDye;
        this.prefix1 = prefix1;
        this.prefix2 = prefix2;
    }

    public String getTypes() {
        return types;
    }

    public String getColor() {
        return color;
    }

    public int getTier() {
        return tier;
    }

    public Material getType(GearType gearType) {
        String armor = types.split("armor:")[1].split(",")[0];
        String weapon = types.split("weapon:")[1];
        String material = "";
        if (gearType.isArmor()) {
            material = (armor + "_" + gearType.getName()).toUpperCase();
        }
        if (gearType.isWeapon()) {
            material = (weapon + "_" + gearType.getName()).toUpperCase();
        }
        return Material.getMaterial(material);
    }

    public static Tier fromTier(int tierNumber) {
        return Tier.valueOf("T" + tierNumber);
    }

    public ArmorDye getArmorDye() {
        return armorDye;
    }

    public String getPrefix1() {
        return prefix1;
    }

    public String getPrefix2() {
        return prefix2;
    }

    public String getPrefix(String type) {
        switch (type) {
            case "Weapon":
                return getPrefix2().trim();
            case "Armor":
                return getPrefix1().trim();

        }
        return null;
    }

    public static int getTier(ItemStack itemStack) {
        String name = itemStack.getItemMeta().getDisplayName();
        if (name.startsWith(ChatColor.WHITE.toString())) return 1;
        if (name.startsWith(ChatColor.GREEN.toString())) return 2;
        if (name.startsWith(ChatColor.AQUA.toString())) return 3;
        if (name.startsWith(ChatColor.LIGHT_PURPLE.toString())) return 4;
        if (name.startsWith(ChatColor.YELLOW.toString())) return 5;
        if (name.startsWith(ChatColor.DARK_AQUA.toString())) return 6;
        return 0;
    }
}
