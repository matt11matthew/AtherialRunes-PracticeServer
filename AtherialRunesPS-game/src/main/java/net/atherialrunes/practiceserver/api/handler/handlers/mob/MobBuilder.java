package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import net.atherialrunes.practiceserver.api.handler.handlers.spawner.Spawner;
import org.bukkit.entity.LivingEntity;

public class MobBuilder {

    public static void spawn(Spawner spawner) {
        LivingEntity mob = null;
        switch (spawner.getMobType()) {
            case SKELETON:
                mob = (LivingEntity) spawner.getLocation().getWorld().spawnEntity(spawner.getLocation(), spawner.getMobType().getEntityType());
                break;
            default:
                break;
        }
        switch (spawner.getMobTier()) {
            case T1:
                break;
            case T2:
                break;
            case T3:
                break;
            case T4:
                break;
            case T5:
                break;
        }
    }
}
