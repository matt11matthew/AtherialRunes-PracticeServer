package net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class T3 extends TierItem {

    public T3() {
        setAxeDurability((short) 0);
        setAxeMinDamageRange("108-201");
        setAxeMaxDamageRange("181-482");
        setAxeType(Material.IRON_AXE);

        setCriticalHitRange("6-12");
        setElementDamageRange("26-56");
        setReflectRange("3-6");
        setBlockRange("7-12");
        setDodgeRange("7-12");
        setVitStatRange("60-165");
        setStrStatRange("60-165");
        setEnergyRange("2-4");
        setHpsRange("135-440");


        setSwordDurability((short) 0);
        setSwordMaxDamageRange("181-482");
        setSwordMinDamageRange("99-189");
        setSwordType(Material.IRON_SWORD);

        setBootDurability((short) 0);
        setBootsHpRange("440-1825");
        setBootType(Material.IRON_BOOTS);

        setHelmetDurability((short) 0);
        setHelmetHpRange("490-1950");
        setHelmetType(Material.IRON_HELMET);

        setChestplateDurability((short) 0);
        setChestplateHpRange("1080-4350");
        setChestplateType(Material.IRON_CHESTPLATE);

        setLeggingDurability((short) 0);
        setLeggingsHpRange("990-4075");
        setLeggingType(Material.IRON_LEGGINGS);

        setSwordName("Magic Sword");
        setAxeName("War Axe");
        setBootName("Iron Platemail Boots");
        setChestplateName("Iron Platemail Chestplate");
        setLeggingName("Iron Platemail Leggings");
        setHelmetName("Iron Platemail Helmet");

        setTier(Tier.T3);
        setTierNumber(3);

        setArmorPenStatRange("1-10");
        setDpsMaxStatRange("3-6");
        setDpsMaxStatRange("7-8");
        setArmorMinStatRange("3-6");
        setArmorMaxStatRange("7-8");
        setIntStatRange("60-165");
        setVsStatRange("2-12");
        setAccuracyStatRange("15-30");
    }
}
