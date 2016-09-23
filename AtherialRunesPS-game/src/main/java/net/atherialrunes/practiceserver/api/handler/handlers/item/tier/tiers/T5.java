package net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Material;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class T5 extends TierItem {

    public T5() {
        setAxeDurability((short) 0);
        setAxeMinDamageRange("698-802");
        setAxeMaxDamageRange("1024-2975");
        setAxeType(Material.GOLD_AXE);

        setCriticalHitRange("12-19");
        setElementDamageRange("56-125");
        setReflectRange("5-11");
        setBlockRange("9-18");
        setDodgeRange("9-18");
        setVitStatRange("180-300");
        setStrStatRange("180-300");
        setEnergyRange("3-6");
        setHpsRange("1125-3250");

        setSwordDurability((short) 0);
        setSwordMaxDamageRange("988-2750");
        setSwordMinDamageRange("624-756");
        setSwordType(Material.GOLD_SWORD);

        setBootDurability((short) 0);
        setBootsHpRange("3990-11255");
        setBootType(Material.GOLD_BOOTS);

        setHelmetDurability((short) 0);
        setHelmetHpRange("4200-13650");
        setHelmetType(Material.GOLD_HELMET);

        setChestplateDurability((short) 0);
        setChestplateHpRange("11850-32750");
        setChestplateType(Material.GOLD_CHESTPLATE);

        setLeggingDurability((short) 0);
        setLeggingsHpRange("10250-31100");
        setLeggingType(Material.GOLD_LEGGINGS);

        setSwordName("Legendary Sword");
        setAxeName("Legendary Axe");
        setBootName("Legendary Platemail Boots");
        setChestplateName("Legendary Platemail");
        setLeggingName("Legendary Platemail Leggings");
        setHelmetName("Legendary Platemail Helmet");

        setTier(Tier.T5);
        setTierNumber(5);
    }
}
