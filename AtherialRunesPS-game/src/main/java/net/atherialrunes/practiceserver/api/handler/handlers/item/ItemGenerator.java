package net.atherialrunes.practiceserver.api.handler.handlers.item;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers.*;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class ItemGenerator {

    public static ItemStack generateGear(int tier, GearType gearType) {
        TierItem item = null;
        switch (tier) {
            case 1:
                item = new T1();
                break;
            case 2:
                item = new T2();
                break;
            case 3:
                item = new T3();
                break;
            case 4:
                item = new T4();
                break;
            case 5:
                item = new T5();
                break;
            case 6:
                item = new T6();
                break;
            default:
                break;
        }
        return item.build(gearType);
    }

    public static ItemStack rerollStats(ItemStack itemStack) {
        TierItem item = null;
        int tier = Tier.getTier(itemStack);
        switch (tier) {
            case 0:
                return itemStack;
            case 1:
                item = new T1();
                break;
            case 2:
                item = new T2();
                break;
            case 3:
                item = new T3();
                break;
            case 4:
                item = new T4();
                break;
            case 5:
                item = new T5();
                break;
            case 6:
                item = new T6();
                break;
            default:
                break;
        }
        return item.rerollStats(itemStack);
    }

    public static AtherialItem generateOrbsOfAlteration(int amount) {
        AtherialItem orb = new AtherialItem(Material.MAGMA_CREAM);
        orb.setName("&dOrb of Alteration");
        orb.addLore("&7Randomizes stats of selected equipment.");
        orb.setAmount(amount);
        return orb;
    }

    public static AtherialItem generateEnchant(int amount, Tier tier, String type) {
        String color = tier.getColor();
        String name = "&f&lScroll:" + color + " Enchant " + tier.getPrefix(type) + " " + type;
        AtherialItem enchant = new AtherialItem(Material.EMPTY_MAP);
        enchant.setName(name);
        enchant.setAmount(amount);
        switch (type) {
            case "Armor":
                enchant.addLore("&c+5% HP");
                enchant.addLore("&c+5% HP REGEN");
                enchant.addLore("&7   - OR -");
                enchant.addLore("&c+1% ENERGY REGEN");
                enchant.addLore("&7&oArmor will VANISH if enchant above +3 FAILS.");
                break;
            case "Weapon":
                enchant.addLore("&c+5% DMG");
                enchant.addLore("&7&oWeapon will VANISH if enchant above +3 FAILS.");
                break;
            default:
                break;
        }
        return enchant;
    }
}
