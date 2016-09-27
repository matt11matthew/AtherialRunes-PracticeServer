package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.GameConstants;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.armor.MobArmor;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.RegionUtils;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.Zone;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Matthew E on 9/24/2016 at 12:15 PM.
 */
public class MobHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        AtherialRunnable.getInstance().runRepeatingTask(this::despawnMobsTask, 5L, 5L);
    }

    @Override
    public void onUnload() {
        Bukkit.getWorld("ARPS").getEntities().forEach(Entity::remove);
    }

    public void despawnMobsTask() {
        MobBuilder.mobArmors.keySet().forEach(mob -> {
            if (mob.isDead()) {
                MobBuilder.mobArmors.remove(mob);
            }
            if (mob.getLocation().getBlock().isLiquid()) {
                MobBuilder.mobArmors.remove(mob);
                mob.remove();
            }
            if (RegionUtils.getZone(mob.getLocation()) == Zone.SAFE) {
                MobBuilder.mobArmors.remove(mob);
                mob.remove();
            }
        });
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            LivingEntity mob = e.getEntity();
            MobArmor mobArmor = MobBuilder.mobArmors.get(mob);
            double dropChance = 0;
            Location location = mob.getLocation();
            switch (mobArmor.getTier()) {
                case T1:
                    dropChance = GameConstants.T1_DROP_RATES;
                    break;
                case T2:
                    dropChance = GameConstants.T2_DROP_RATES;
                    break;
                case T3:
                    dropChance = GameConstants.T3_DROP_RATES;
                    break;
                case T4:
                    dropChance = GameConstants.T4_DROP_RATES;
                    break;
                case T5:
                    dropChance = GameConstants.T5_DROP_RATES;
                    break;
                case T6:
                    dropChance = GameConstants.T6_DROP_RATES;
                    break;
                default:
                    break;
            }
//            if (mobArmor.getKiller().equals("matt11matthew")) {
//                dropChance = 50;
//            }
            if (mobArmor.isElite()) {
                dropChance = (int) (dropChance * GameConstants.ELITE_DROP_MULTIPLIER);
            }
            if (dropChance > 100) {
                dropChance = 100;
            }
            if (RandomUtils.random(1, 20) == 10) {
                int gem_amount = 0;
                switch (mobArmor.getTier()) {
                    case T1:
                        gem_amount = decideValue(GameConstants.T1_GEM_DROP);
                        break;
                    case T2:
                        gem_amount = decideValue(GameConstants.T2_GEM_DROP);
                        break;
                    case T3:
                        gem_amount = decideValue(GameConstants.T3_GEM_DROP);
                        break;
                    case T4:
                        gem_amount = decideValue(GameConstants.T4_GEM_DROP);
                        break;
                    case T5:
                        gem_amount = decideValue(GameConstants.T5_GEM_DROP);
                        break;
                    case T6:
                        gem_amount = decideValue(GameConstants.T6_GEM_DROP);
                        break;
                }
                int stacks = 0;
                if (gem_amount > 64) {
                    stacks += 1;
                    gem_amount -= 64;
                }
                if (stacks > 1) {
                    for (int i = 0; i < stacks; i++) {
                        AtherialItem gem = new AtherialItem(Material.EMERALD);
                        gem.setName("&aGem");
                        gem.setAmount(64);
                        drop(gem.build(), location);
                    }
                    AtherialItem gem = new AtherialItem(Material.EMERALD);
                    gem.setName("&aGem");
                    gem.setAmount(gem_amount);
                    drop(gem.build(), location);
                } else {
                    AtherialItem gem = new AtherialItem(Material.EMERALD);
                    gem.setName("&aGem");
                    gem.setAmount(gem_amount);
                    drop(gem.build(), location);
                }
            }
            int chance = (int) (Math.random() * 100);
            if (chance < dropChance) {
                int type = RandomUtils.random(1, 5);
                switch (type) {
                    case 1:
                        drop(mobArmor.getHelmet().build(), location);
                        break;
                    case 2:
                        drop(mobArmor.getChestplate().build(), location);
                        break;
                    case 3:
                        drop(mobArmor.getLeggings().build(), location);
                        break;
                    case 4:
                        drop(mobArmor.getBoots().build(), location);
                        break;
                    case 5:
                        drop(mobArmor.getWeapon().build(), location);
                        break;
                }
                MobBuilder.mobArmors.remove(mob);
                return;
            }
        }
    }

    public void drop(ItemStack item, Location location) {
        location.getWorld().dropItemNaturally(location, item);
    }

    private int decideValue(String value) {
        return RandomUtils.random(Integer.parseInt(value.split("-")[0]), Integer.parseInt(value.split("-")[1]));
    }
}
