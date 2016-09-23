package net.atherialrunes.practiceserver.api.handler.handlers.item;

import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.TierItem;
import net.atherialrunes.practiceserver.api.handler.handlers.item.tier.tiers.*;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
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
            default:
                break;
        }
        return item.build(gearType);
    }
}
