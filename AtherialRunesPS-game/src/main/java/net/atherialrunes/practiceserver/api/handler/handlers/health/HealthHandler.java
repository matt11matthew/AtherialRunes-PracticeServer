package net.atherialrunes.practiceserver.api.handler.handlers.health;

import net.atherialrunes.practiceserver.PracticeServer;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.damage.DamageHandler;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import net.atherialrunes.practiceserver.utils.StatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class HealthHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        levelBarTask();
        hpTask();
    }

    @Override
    public void onUnload() {

    }


    public void levelBarTask() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.setLevel((int) player.getHealth());
                });
            }
        }, 5L, 5L);
    }

    public void hpTask() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    hpRegen(player);

                });
            }
        }, 5L, 5L);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        new BukkitRunnable() {

            @Override
            public void run() {
                hpCheck(p);
            }

        }.runTaskLater(PracticeServer.getInstance(), 1L);
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    public static void hpRegen(Player player) {
        if (DamageHandler.tagged.contains(player)) {
            return;
        }
        double hps = 5.0D;
        double add_hps = getHPS(player);
        if (add_hps > 0.0D) {
            hps += add_hps;
        }
        if ((player.getHealth() + hps) > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        } else {
            double new_hp = (player.getHealth() + hps);
            player.setHealth(new_hp);
        }
    }

    public void hpCheck(Player p) {
        double vit = getVIT(p);
        double a = 200.0D;
        double hp = getHP(p);
        a += hp;
        if (vit > 0.0D) {
            double divide = vit / 2000.0D;
            double pre = a * divide;
            int cleaned = (int)(a + pre);
            if (p.getHealth() > cleaned) {
                p.setHealth(cleaned);
            }
            p.setMaxHealth(cleaned);
        } else {
            p.setMaxHealth(a);
        }
        p.setHealthScale(20.0D);
        p.setHealthScaled(true);
    }


    public static double getVIT(Player p) {
        double vit = 0.0D;
        if ((p.getEquipment().getHelmet() != null) && (p.getEquipment().getHelmet().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getHelmet(), "VIT")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getHelmet(), "VIT: +", ""));
            }
        }
        if ((p.getEquipment().getChestplate() != null) && (p.getEquipment().getChestplate().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getChestplate(), "VIT")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getChestplate(), "VIT: +", ""));
            }
        }
        if ((p.getEquipment().getLeggings() != null) && (p.getEquipment().getLeggings().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getLeggings(), "VIT")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getLeggings(), "VIT: +", ""));
            }
        }
        if ((p.getEquipment().getBoots() != null) && (p.getEquipment().getBoots().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getBoots(), "VIT")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getBoots(), "VIT: +", ""));
            }
        }
        return vit;
    }

    public static double getHPS(Player p) {
        double vit = 0.0D;
        if ((p.getEquipment().getHelmet() != null) && (p.getEquipment().getHelmet().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getHelmet(), "HP REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getHelmet(), "HP REGEN: ", "HP/s"));
            }
        }
        if ((p.getEquipment().getChestplate() != null) && (p.getEquipment().getChestplate().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getChestplate(), "HP REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getChestplate(), "HP REGEN: ", "HP/s"));
            }
        }
        if ((p.getEquipment().getLeggings() != null) && (p.getEquipment().getLeggings().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getLeggings(), "HP REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getLeggings(), "HP REGEN: ", "HP/s"));
            }
        }
        if ((p.getEquipment().getBoots() != null) && (p.getEquipment().getBoots().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getBoots(), "HP REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getBoots(), "HP REGEN: ", "HP/s"));
            }
        }
        return vit;
    }

    public static double getHP(Player p) {
        double vit = 0.0D;
        if ((p.getEquipment().getHelmet() != null) && (p.getEquipment().getHelmet().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getHelmet(), "HP")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getHelmet(), "HP: +", ""));
            }
        }
        if ((p.getEquipment().getChestplate() != null) && (p.getEquipment().getChestplate().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getChestplate(), "HP")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getChestplate(), "HP: +", ""));
            }
        }
        if ((p.getEquipment().getLeggings() != null) && (p.getEquipment().getLeggings().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getLeggings(), "HP")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getLeggings(), "HP: +", ""));
            }
        }
        if ((p.getEquipment().getBoots() != null) && (p.getEquipment().getBoots().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getBoots(), "HP")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getBoots(), "HP: +", ""));
            }
        }
        return vit;
    }

}
