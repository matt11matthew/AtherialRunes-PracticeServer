package net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.itemvendor;

import net.atherialrunes.practiceserver.api.handler.handlers.item.ItemGenerator;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import net.atherialrunes.practiceserver.api.menu.Menu;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class ItemVendorMenu extends Menu {

    public ItemVendorMenu(String npcName, int slots) {
        super(npcName, slots);

        setItem(0, ItemGenerator.generateOrbsOfAlteration(1).setPrice(1600).build());

        setItem(1, ItemGenerator.generateEnchant(1, Tier.T6, "Armor").setPrice(10000).build());
        setItem(2, ItemGenerator.generateEnchant(1, Tier.T5, "Armor").setPrice(3000).build());
        setItem(3, ItemGenerator.generateEnchant(1, Tier.T4, "Armor").setPrice(1600).build());
        setItem(4, ItemGenerator.generateEnchant(1, Tier.T3, "Armor").setPrice(500).build());
        setItem(5, ItemGenerator.generateEnchant(1, Tier.T2, "Armor").setPrice(250).build());
        setItem(6, ItemGenerator.generateEnchant(1, Tier.T1, "Armor").setPrice(100).build());

        setItem(7, ItemGenerator.generateEnchant(1, Tier.T6, "Weapon").setPrice(10000).build());
        setItem(8, ItemGenerator.generateEnchant(1, Tier.T5, "Weapon").setPrice(3000).build());
        setItem(9, ItemGenerator.generateEnchant(1, Tier.T4, "Weapon").setPrice(1600).build());
        setItem(10, ItemGenerator.generateEnchant(1, Tier.T3, "Weapon").setPrice(500).build());
        setItem(11, ItemGenerator.generateEnchant(1, Tier.T2, "Weapon").setPrice(250).build());
        setItem(12, ItemGenerator.generateEnchant(1, Tier.T1, "Weapon").setPrice(100).build());
    }
}
