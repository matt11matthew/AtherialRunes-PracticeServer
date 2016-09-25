package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.armor.MobArmor;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.Spawner;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class MobBuilder {

    public static HashMap<LivingEntity, MobArmor> mobArmors = new HashMap<>();

    public static void spawn(Spawner spawner) {
        spawn(spawner.getLocation(), spawner.getMobTier(), spawner.getMobType(), spawner.isElite());
    }

    public static void spawn(Location location, Tier tier, MobType mobType, boolean isElite) {
        LivingEntity mob = (LivingEntity) location.getWorld().spawnEntity(location, mobType.getEntityType());
        AtherialItem helmet = AtherialItem.generate(GearType.HELMET, tier);
        AtherialItem chestplate = AtherialItem.generate(GearType.CHESTPLATE, tier);
        AtherialItem leggings = AtherialItem.generate(GearType.LEGGINGS, tier);
        AtherialItem boots = AtherialItem.generate(GearType.BOOTS, tier);
        AtherialItem weapon = AtherialItem.generate(GearType.getRandomWeapon(), tier);
        MobArmor mobArmor = new MobArmor(mob, helmet, chestplate, leggings, boots, weapon);
        mobArmor.setTier(tier);
        mobArmor.setElite(isElite);
        String name = getName(tier, mobType, isElite);
        mobArmor.setName(name);
        mob.getEquipment().setHelmet(helmet.build());
        mob.getEquipment().setChestplate(chestplate.build());
        mob.getEquipment().setLeggings(leggings.build());
        mob.getEquipment().setBoots(boots.build());
        mob.getEquipment().setItemInMainHand(weapon.build());
        mob.setCustomName(name);
        mob.setCustomNameVisible(true);
        mob.setMaxHealth(getHealthBasedOnMobArmor(mobArmor));
        mob.setHealth(mob.getMaxHealth());
        mobArmors.put(mob, mobArmor);
        return;
    }

    public static int getHealthBasedOnMobArmor(MobArmor mobArmor) {
        int health = 0;
        health += mobArmor.getHelmet().getHP();
        health += mobArmor.getChestplate().getHP();
        health += mobArmor.getLeggings().getHP();
        health += mobArmor.getBoots().getHP();
        if (mobArmor.isElite()) {
            health *= 2;
        }
        return health;
    }

    public static int getDamageBasedOnMobArmor(MobArmor mobArmor) {
        int damage = 0;
        damage += mobArmor.getWeapon().getDamage();
        if (mobArmor.isElite()) {
            damage *= 2;
        }
        return damage;
    }

    public static String getName(Tier tier, MobType mobType, boolean isElite) {
        String name = mobType.getName();
        switch (mobType) {
            case SKELETON:
                switch (tier) {
                    case T1:
                        int randomName = RandomUtils.random(1, 2);
                        switch (randomName) {
                            case 1:
                                name = "Rotting " + name;
                                break;
                            case 2:
                                name = "Broken " + name;
                                break;
                            default:
                                break;
                        }
                        break;
                    case T2:
                        name = "Cracking " + name;
                        break;
                    case T3:
                        name = "Demonic " + name;
                        break;
                    case T4:
                        name = name + " Guardian";
                        break;
                    case T5:
                        name = "Infernal " + name;
                        break;
                }
                break;
            case ZOMBIE:
                switch (tier) {
                    case T1:
                        name = "Weak " + name;
                        break;
                    case T2:
                        name = "Strong " + name;
                        break;
                    case T3:
                        name = "Magic " + name;
                        break;
                    case T4:
                        name = "Ancient " + name;
                        break;
                    case T5:
                        name = "Infernal " + name;
                        break;
                }
                break;
            case NAGA:
                switch (tier) {
                    case T1:
                        name = "Weak " + name;
                        break;
                    case T2:
                        name = "Strong " + name;
                        break;
                    case T3:
                        name = "Magical " + name;
                        break;
                    case T4:
                        name = "Gigantic " + name;
                        break;
                    case T5:
                        name = "Infernal " + name;
                        break;
                }
                break;
        }
        if (isElite) {
            name = tier.getColor() + "&l" + name;
        } else {
            name = tier.getColor() + name;
        }
        return Utils.colorCodes(name);
    }

    public static String getName(Spawner spawner) {
        MobType mobType = spawner.getMobType();
        String name = mobType.getName();
        switch (mobType) {
            case SKELETON:
                switch (spawner.getMobTier()) {
                    case T1:
                        int randomName = RandomUtils.random(1, 2);
                        switch (randomName) {
                            case 1:
                                name = "Rotting " + name;
                                break;
                            case 2:
                                name = "Broken " + name;
                                break;
                            default:
                                break;
                        }
                        break;
                    case T2:
                        name = "Cracking " + name;
                        break;
                    case T3:
                        name = "Demonic " + name;
                        break;
                    case T4:
                        name = name + " Guardian";
                        break;
                    case T5:
                        name = "Infernal " + name;
                        break;
                }
                break;
            case ZOMBIE:
                switch (spawner.getMobTier()) {
                    case T1:
                        name = "Weak " + name;
                        break;
                    case T2:
                        name = "Strong " + name;
                        break;
                    case T3:
                        name = "Magic " + name;
                        break;
                    case T4:
                        name = "Ancient " + name;
                        break;
                    case T5:
                        name = "Infernal " + name;
                        break;
                }
                break;
            case NAGA:
                switch (spawner.getMobTier()) {
                    case T1:
                        name = "Weak " + name;
                        break;
                    case T2:
                        name = "Strong " + name;
                        break;
                    case T3:
                        name = "Magical " + name;
                        break;
                    case T4:
                        name = "Gigantic " + name;
                        break;
                    case T5:
                        name = "Infernal " + name;
                        break;
                }
                break;
        }
        if (spawner.isElite()) {
            name = spawner.getMobTier().getColor() + "&l" + name;
        } else {
            name = spawner.getMobTier().getColor() + name;
        }
        return Utils.colorCodes(name);
    }
}
