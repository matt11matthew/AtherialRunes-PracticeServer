package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.utils.RandomUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Matthew E on 9/21/2016.
 */
public enum GearType {

    HELMET(0, "helmet"),
    CHESTPLATE(1, "chestplate"),
    LEGGINGS(2, "leggings"),
    BOOTS(3, "boots"),
    SWORD(4, "sword"),
    AXE(5, "axe");

    private int id;
    private String name;

    GearType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isWeapon() {
        return ((this == AXE) || (this == SWORD));
    }

    public boolean isArmor() {
        return ((this == HELMET) || (this == CHESTPLATE) || (this == LEGGINGS) || (this == BOOTS));
    }

    public static GearType getGearType(ItemStack item) {
        List<GearType> types = Arrays.asList(values());
        for (GearType type : types) {
            if (item.getType().toString().toLowerCase().split("_")[1].equals(type.getName())) {
                return type;
            }
        }
        return null;
    }

    public static GearType getRandomWeapon() {
        int i = RandomUtils.random(1, 2);
        switch (i) {
            case 1:
                return AXE;
            case 2:
                return SWORD;
            default:
                return SWORD;
        }
    }
}
