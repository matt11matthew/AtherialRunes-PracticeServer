package net.atherialrunes.practiceserver.api.handler.handlers.vendor;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.itemvendor.ItemVendor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class VendorHandler extends ListenerHandler {

    public static HashMap<String, Vendor> vendors = new HashMap<>();

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    public static void registerVendor(Vendor vendor) {
        vendors.put(vendor.getNPCName(), vendor);
    }

    @EventHandler
    public void onNPCRightClick(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof HumanEntity) {
            Player p = e.getPlayer();
            HumanEntity npc = (HumanEntity) e.getRightClicked();
            if (npc.hasMetadata("NPC")) {
                e.setCancelled(true);
                vendors.values().forEach(vendor -> {
                    if (npc.getName().equals(vendor.getNPCName())) {
                        vendor.onNPCRightClick(GameAPI.getGamePlayer(e.getPlayer()));
                        return;
                    }
                });
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) return;
        if ((e.getCurrentItem().getType() != null) && (e.getCurrentItem().getType() != Material.AIR)) {
            if (e.getClickedInventory().getTitle().equals("Rules")) {
                e.setCancelled(true);
            }
             if (e.getClickedInventory().getTitle().equals("Item Vendor")) {
                 new ItemVendor("Item Vendor", 18).onMenuClick(GameAPI.getGamePlayer(player), e.getSlot(), AtherialItem.fromItemStack(e.getCurrentItem()));
                 e.setCancelled(true);
                 return;
             }
        }
//        vendors.values().forEach(vendor -> {
//            if (e.getClickedInventory().getTitle().equals(vendor.getMenu().getTitle())) {
//                e.setCancelled(true);
//                if ((e.getCurrentItem().getType() != null) && (e.getCurrentItem().getType() != Material.AIR)) {
//                    vendor.onMenuClick(GameAPI.getGamePlayer(player), e.getSlot(), AtherialItem.fromItemStack(e.getCurrentItem()));
//                }
//                return;
//            } else {
//                return;
//            }
//        });
    }
}