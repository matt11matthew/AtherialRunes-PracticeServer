package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.api.handler.handlers.spawner.Spawner;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
/**
 * Created by Matthew E on 9/21/2016.
 */
public class MobBuilder {

    public static void spawn(Spawner spawner) {
        LivingEntity mob = (LivingEntity) spawner.getLocation().getWorld().spawnEntity(spawner.getLocation(), spawner.getMobType().getEntityType());
        mob.getEquipment().setHelmet(new ItemStack(spawner.getMobTier().getType(GearType.HELMET)));
        mob.getEquipment().setChestplate(new ItemStack(spawner.getMobTier().getType(GearType.CHESTPLATE)));
        mob.getEquipment().setLeggings(new ItemStack(spawner.getMobTier().getType(GearType.LEGGINGS)));
        mob.getEquipment().setBoots(new ItemStack(spawner.getMobTier().getType(GearType.BOOTS)));
        mob.setCustomName(Utils.colorCodes(spawner.getMobTier().toString() + " " + spawner.getMobTier().getColor() + spawner.getMobType().getName()));
    }
}
