package net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class T2 extends TierItem {

    public T2() {
        setAxeDurability((short) 0);
        setAxeMinDamageRange("67-118");
        setAxeMaxDamageRange("96-192");
        setAxeType(Material.STONE_AXE);

        setCriticalHitRange("4-9");
        setElementDamageRange("14-28");
        setReflectRange("2-4");
        setBlockRange("4-8");
        setDodgeRange("4-8");
        setVitStatRange("25-90");
        setStrStatRange("25-90");
        setEnergyRange("2-3");
        setHpsRange("45-150");


        setSwordDurability((short) 0);
        setSwordMaxDamageRange("58-110");
        setSwordMinDamageRange("82-176");
        setSwordType(Material.STONE_SWORD);

        setBootDurability((short) 0);
        setBootsHpRange("145-515");
        setBootType(Material.CHAINMAIL_BOOTS);

        setHelmetDurability((short) 0);
        setHelmetHpRange("160-525");
        setHelmetType(Material.CHAINMAIL_HELMET);

        setChestplateDurability((short) 0);
        setChestplateHpRange("335-1275");
        setChestplateType(Material.CHAINMAIL_CHESTPLATE);

        setLeggingDurability((short) 0);
        setLeggingsHpRange("315-1150");
        setLeggingType(Material.CHAINMAIL_LEGGINGS);

        setSwordName("Broadsword");
        setAxeName("Reinforced Hatchet");
        setBootName("Laced Sandals");
        setChestplateName("Platemail");
        setLeggingName("Platemail Leggings");
        setHelmetName("Platemail Helmet");

        setTier(Tier.T2);
        setTierNumber(2);

        setArmorPenStatRange("1-10");
        setDpsMaxStatRange("2-3");
        setDpsMaxStatRange("4-6");
        setArmorMinStatRange("2-4");
        setArmorMaxStatRange("6-9");
        setIntStatRange("25-95");
        setVsStatRange("1-10");
        setAccuracyStatRange("1-30");
    }
}
