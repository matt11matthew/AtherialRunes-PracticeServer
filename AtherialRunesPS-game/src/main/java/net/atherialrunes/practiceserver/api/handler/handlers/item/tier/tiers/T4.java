package net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class T4 extends TierItem {

    public T4() {
        setAxeDurability((short) 0);
        setAxeMinDamageRange("321-606");
        setAxeMaxDamageRange("489-920");
        setAxeType(Material.DIAMOND_AXE);

        setCriticalHitRange("9-17");
        setElementDamageRange("35-85");
        setReflectRange("5-9");
        setBlockRange("9-16");
        setDodgeRange("9-16");
        setVitStatRange("105-200");
        setStrStatRange("105-200");
        setEnergyRange("3-5");
        setHpsRange("425-1200");


        setSwordDurability((short) 0);
        setSwordMaxDamageRange("465-860");
        setSwordMinDamageRange("320-601");
        setSwordType(Material.DIAMOND_SWORD);

        setBootDurability((short) 0);
        setBootsHpRange("1400-4460");
        setBootType(Material.DIAMOND_BOOTS);

        setHelmetDurability((short) 0);
        setHelmetHpRange("1525-4875");
        setHelmetType(Material.DIAMOND_HELMET);

        setChestplateDurability((short) 0);
        setChestplateHpRange("4000-13250");
        setChestplateType(Material.DIAMOND_CHESTPLATE);

        setLeggingDurability((short) 0);
        setLeggingsHpRange("3450-11800");
        setLeggingType(Material.DIAMOND_LEGGINGS);

        setSwordName("Ancient Sword");
        setAxeName("Ancient Axe");
        setBootName("Ancient Platemail Boots");
        setChestplateName("Ancient Platemail");
        setLeggingName("Ancient Platemail Leggings");
        setHelmetName("Ancient Platemail Helmet");

        setTier(Tier.T4);
        setTierNumber(4);

        setArmorPenStatRange("1-10");
        setDpsMaxStatRange("7-10");
        setDpsMaxStatRange("11-12");
        setArmorMinStatRange("7-10");
        setArmorMaxStatRange("11-12");
        setIntStatRange("105-200");
        setVsStatRange("5-20");
        setAccuracyStatRange("15-30");
    }
}
