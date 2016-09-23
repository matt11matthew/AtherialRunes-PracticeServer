package net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;

public class T5 extends TierItem {

    public T5() {
        setAxeDurability((short) 0);
        setAxeMinDamageRange("21-61");
        setAxeMaxDamageRange("39-86");
        setAxeType(Material.WOOD_AXE);

        setCriticalHitRange("1-5");
        setElementDamageRange("6-16");

        setSwordDurability((short) 0);
        setSwordMaxDamageRange("18-55");
        setSwordMinDamageRange("38-69");
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

        setTier(Tier.T1);
        setTierNumber(1);
    }
}
