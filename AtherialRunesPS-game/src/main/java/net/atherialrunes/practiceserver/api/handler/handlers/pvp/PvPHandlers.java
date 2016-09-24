package net.atherialrunes.practiceserver.api.handler.handlers.pvp;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.StatUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew E on 9/24/2016 at 11:06 AM.
 */
public class PvPHandlers extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        e.setDeathMessage(null);
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        e.getDrops().clear();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        try {
            if (getDrops(gp).isEmpty()) {
                player.spigot().respawn();
                return;
            }
            for (ItemStack drop : getDrops(gp)) {
                player.getInventory().removeItem(drop);
                player.getWorld().dropItemNaturally(player.getLocation(), drop);
            }
            player.spigot().respawn();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public List<ItemStack> getDrops(GamePlayer gp) {
        List<ItemStack> drops = new ArrayList<>();
        Alignment alignment = gp.getAlignment();
        Player p = gp.getPlayer();
        switch (alignment) {
            case CHAOTIC:
                for (int i = 0; i < p.getInventory().getContents().length; i++) {
                    ItemStack item = p.getInventory().getItem(i);
                    if (item == null) continue;
                    if (p.getEquipment().getHelmet() != null) {
                        drops.add(p.getEquipment().getHelmet());
                        p.getEquipment().setHelmet(new ItemStack(Material.AIR));
                    }
                    if (p.getEquipment().getChestplate() != null) {
                        drops.add(p.getEquipment().getChestplate());
                        p.getEquipment().setChestplate(new ItemStack(Material.AIR));
                    }
                    if (p.getEquipment().getLeggings() != null) {
                        drops.add(p.getEquipment().getLeggings());
                        p.getEquipment().setLeggings(new ItemStack(Material.AIR));
                    }
                    if (p.getEquipment().getBoots() != null) {
                        drops.add(p.getEquipment().getBoots());
                        p.getEquipment().setBoots(new ItemStack(Material.AIR));
                    }
                    drops.add(item);
                }
                break;
            case LAWFUL:
                for (int i = 0; i < p.getInventory().getContents().length; i++) {
                    ItemStack item = p.getInventory().getItem(i);
                    if (item == null) continue;
                    drops.add(item);
                    if (p.getEquipment().getHelmet() != null) {
                        drops.remove(p.getEquipment().getHelmet());
                    }
                    if (p.getEquipment().getChestplate() != null) {
                        drops.remove(p.getEquipment().getChestplate());
                    }
                    if (p.getEquipment().getLeggings() != null) {
                        drops.remove(p.getEquipment().getLeggings());
                    }
                    if (StatUtils.hasStat(p.getInventory().getItem(i), "Untradable")) {
                        for (int ii = 1; ii < p.getInventory().getSize(); ii++) {
                            if (p.getInventory().getItem(ii) != null) {
                                if (StatUtils.hasStat(p.getInventory().getItem(ii), "Untradable")) {
                                    drops.remove(p.getInventory().getItem(ii));
                                }
                            }
                        }
                    }
                    if (StatUtils.hasStat(p.getInventory().getItem(i), "Soulbound")) {
                        for (int ii = 1; ii < p.getInventory().getSize(); ii++) {
                            if (p.getInventory().getItem(ii) != null) {
                                if (StatUtils.hasStat(p.getInventory().getItem(ii), "Soulbound")) {
                                    drops.remove(p.getInventory().getItem(ii));
                                }
                            }
                        }
                    }
                    if (p.getEquipment().getBoots() != null) {
                        drops.remove(p.getEquipment().getBoots());
                    }
                    if (p.getInventory().getItem(0) != null) {
                        drops.remove(p.getInventory().getItem(0));
                    }
                    if (p.getEquipment().getItemInOffHand() != null) {
                        drops.remove(p.getEquipment().getItemInOffHand());
                    }
                }
                break;
            case NEUTRAL:
                for (int i = 0; i < p.getInventory().getContents().length; i++) {
                    ItemStack item = p.getInventory().getItem(i);
                    if (item == null) continue;
                    drops.add(item);
                    if (p.getEquipment().getHelmet() != null) {
                        drops.remove(p.getEquipment().getHelmet());
                    }
                    if (p.getEquipment().getChestplate() != null) {
                        drops.remove(p.getEquipment().getChestplate());
                    }
                    if (StatUtils.hasStat(p.getInventory().getItem(i), "Untradable")) {
                        for (int ii = 1; ii < p.getInventory().getSize(); ii++) {
                            if (p.getInventory().getItem(ii) != null) {
                                if (StatUtils.hasStat(p.getInventory().getItem(ii), "Untradable")) {
                                    drops.remove(p.getInventory().getItem(ii));
                                }
                            }
                        }
                    }
                    if (StatUtils.hasStat(p.getInventory().getItem(i), "Soulbound")) {
                        for (int ii = 1; ii < p.getInventory().getSize(); ii++) {
                            if (p.getInventory().getItem(ii) != null) {
                                if (StatUtils.hasStat(p.getInventory().getItem(ii), "Soulbound")) {
                                    drops.remove(p.getInventory().getItem(ii));
                                }
                            }
                        }
                    }
                    if (p.getEquipment().getLeggings() != null) {
                        drops.remove(p.getEquipment().getLeggings());
                    }
                    if (p.getEquipment().getBoots() != null) {
                        drops.remove(p.getEquipment().getBoots());
                    }
                    if (p.getInventory().getItem(0) != null) {
                        drops.remove(p.getInventory().getItem(0));
                    }
                    if (p.getEquipment().getItemInOffHand() != null) {
                        drops.remove(p.getEquipment().getItemInOffHand());
                    }
                }
                break;
        }
        return drops;
    }

}
