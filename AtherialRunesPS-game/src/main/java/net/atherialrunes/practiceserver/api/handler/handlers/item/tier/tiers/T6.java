package net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class T6 extends TierItem {

    public T6() {
        setAxeDurability((short) 0);
        setAxeMinDamageRange("3200-4200");
        setAxeMaxDamageRange("3850-8200");
        setAxeType(Material.IRON_AXE);

        setCriticalHitRange("14-19");
        setElementDamageRange("72-220");
        setReflectRange("6-11");
        setBlockRange("9-14");
        setDodgeRange("9-14");
        setVitStatRange("220-350");
        setStrStatRange("220-350");
        setEnergyRange("4-7");
        setHpsRange("2250-4590");

        setSwordDurability((short) 0);
        setSwordMaxDamageRange("3625-8009");
        setSwordMinDamageRange("3000-4059");
        setSwordType(Material.IRON_SWORD);

        setBootDurability((short) 0);
        setBootsHpRange("17200-51000");
        setBootType(Material.IRON_BOOTS);

        setHelmetDurability((short) 0);
        setHelmetHpRange("16000-48250");
        setHelmetType(Material.IRON_HELMET);

        setChestplateDurability((short) 0);
        setChestplateHpRange("42220-129500");
        setChestplateType(Material.IRON_CHESTPLATE);

        setLeggingDurability((short) 0);
        setLeggingsHpRange("39900-125500");
        setLeggingType(Material.IRON_LEGGINGS);

        setArmorPenStatRange("9-30");
        setDpsMinStatRange("10-18");
        setDpsMaxStatRange("18-32");

        setArmorMinStatRange("10-18");
        setArmorMaxStatRange("18-32");
        setIntStatRange("220-350");
        setVsStatRange("9-26");
        setAccuracyStatRange("18-38");

        setSwordName("Mythical Titanium Sword");
        setAxeName("Mythical Titanium Axe");
        setBootName("Mythical Titanium Platemail Boots");
        setChestplateName("Mythical Titanium Platemail");
        setLeggingName("Mythical Titanium Platemail Leggings");
        setHelmetName("Mythical Titanium Platemail Helmet");

        setTier(Tier.T6);
        setTierNumber(6);
    }
}
