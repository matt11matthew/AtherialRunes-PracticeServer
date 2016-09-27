package net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class T1 extends TierItem {

    public T1() {
        setAxeDurability((short) 0);
        setAxeMinDamageRange("21-61");
        setAxeMaxDamageRange("39-86");
        setAxeType(Material.WOOD_AXE);

        setCriticalHitRange("1-5");
        setElementDamageRange("6-16");
        setReflectRange("1-1");
        setBlockRange("1-3");
        setDodgeRange("1-3");
        setVitStatRange("1-50");
        setStrStatRange("1-50");
        setEnergyRange("1-2");
        setHpsRange("5-50");

        setSwordDurability((short) 0);
        setSwordMaxDamageRange("38-69");
        setSwordMinDamageRange("18-55");
        setSwordType(Material.WOOD_SWORD);

        setBootDurability((short) 0);
        setBootsHpRange("50-200");
        setBootType(Material.LEATHER_BOOTS);

        setHelmetDurability((short) 0);
        setHelmetHpRange("65-210");
        setHelmetType(Material.LEATHER_HELMET);

        setChestplateDurability((short) 0);
        setChestplateHpRange("150-400");
        setChestplateType(Material.LEATHER_CHESTPLATE);

        setLeggingDurability((short) 0);
        setLeggingsHpRange("125-375");
        setLeggingType(Material.LEATHER_LEGGINGS);

        setSwordName("Shortsword");
        setAxeName("Wooden Hatchet");
        setBootName("Leather Sandals");
        setChestplateName("Leather Vest");
        setLeggingName("Leather Pants");
        setHelmetName("Leather Cap");

        setArmorPenStatRange("1-10");
        setDpsMaxStatRange("1-3");
        setDpsMaxStatRange("4-5");
        setArmorMinStatRange("1-4");
        setArmorMaxStatRange("7-17");
        setIntStatRange("1-50");
        setVsStatRange("1-10");
        setAccuracyStatRange("1-30");
        setTier(Tier.T1);
        setTierNumber(1);
    }
}