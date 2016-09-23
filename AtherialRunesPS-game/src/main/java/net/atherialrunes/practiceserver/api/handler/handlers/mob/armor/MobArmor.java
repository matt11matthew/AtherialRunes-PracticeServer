package net.atherialrunes.practiceserver.api.handler.handlers.mob.armor;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import org.bukkit.entity.LivingEntity;

@Data
public class MobArmor {

    private LivingEntity livingEntity;
    private AtherialItem helmet;
    private AtherialItem chestplate;
    private AtherialItem leggings;
    private AtherialItem boots;
    private AtherialItem weapon;

    public MobArmor(LivingEntity livingEntity, AtherialItem helmet, AtherialItem chestplate, AtherialItem leggings, AtherialItem boots, AtherialItem weapon) {
        this.livingEntity = livingEntity;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.weapon = weapon;
    }
}
