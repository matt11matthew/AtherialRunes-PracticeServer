package net.atherialrunes.practiceserver.api.handler.handlers.enchant;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.item.ItemGenerator;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Matthew E on 9/24/2016 at 12:31 PM.
 */
public class OrbHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return; 
        }
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if ((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR)) {
            if (isOrb(e.getCursor())) {
                if (isGear(e.getCurrentItem())) {
                    e.setCancelled(true);
                    if (e.getCursor().getAmount() > 1) {
                        e.getCursor().setAmount((e.getCursor().getAmount() - 1));
                    } else {
                        e.setCursor(new ItemStack(Material.AIR));
                    }
                    ItemStack oldItem = e.getCurrentItem();
                    ItemStack newItem = ItemGenerator.rerollStats(e.getCurrentItem());
                    if (!newItem.equals(oldItem)) {
                        gp.fw();
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1.0F, 1.0F);
                    }
                    e.setCurrentItem(newItem);
                    return;
                }
            }
        }
    }

    public boolean isGear(ItemStack item) {
        GearType type = GearType.getGearType(item);
        return (type != null) ? true : false;
    }

    public boolean isOrb(ItemStack item) {
        return (item.getType() == Material.MAGMA_CREAM);
    }
}
