package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.GameConstants;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.armor.MobArmor;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import org.bukkit.Location;
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
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            e.setDroppedExp(0);
            e.getDrops().clear();
            LivingEntity mob = e.getEntity();
            MobArmor mobArmor = MobBuilder.mobArmors.get(mob);
            int dropChance = 0;
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
                default:
                    break;
            }
            if (mobArmor.isElite()) {
                dropChance = (int) (dropChance * GameConstants.ELITE_DROP_MULTIPLIER);
            }
            if (dropChance > 100) {
                dropChance = 100;
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
}
