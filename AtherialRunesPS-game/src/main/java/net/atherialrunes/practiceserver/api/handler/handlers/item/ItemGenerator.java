package net.atherialrunes.practiceserver.api.handler.handlers.item;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import org.bukkit.inventory.ItemStack;

public class ItemGenerator {

    public static ItemStack generateGear(TierItem tierItem, GearType gearType) {
        return tierItem.build(gearType);
    }
}
