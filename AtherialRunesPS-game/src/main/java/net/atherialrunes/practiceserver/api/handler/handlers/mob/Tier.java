package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.api.handler.handlers.mob.armor.ArmorDye;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
/**
 * Created by Matthew E on 9/21/2016.
 */
public enum Tier {

    T1(1, "&f", "armor:leather,weapon:wood", ArmorDye.NONE),
    T2(2, "&a", "armor:chainmail,weapon:stone", ArmorDye.NONE),
    T3(3, "&b", "armor:iron,weapon:iron", ArmorDye.NONE),
    T4(4, "&d", "armor:diamond,weapon:diamond", ArmorDye.NONE),
    T5(5, "&e", "armor:gold,weapon:gold", ArmorDye.NONE);

    private int tier;
    private String color;
    private String types;
    private ArmorDye armorDye;

    Tier(int tier, String color, String types, ArmorDye armorDye) {
        this.tier = tier;
        this.color = color;
        this.types = types;
        this.armorDye = armorDye;
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
        List<Tier> tiers = Arrays.asList(values());
        for (Tier tier : tiers) {
            return (tier.getTier() == tierNumber) ? tier : null;
        }
        return null;
    }

    public ArmorDye getArmorDye() {
        return armorDye;
    }
}
