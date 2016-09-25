package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import org.bukkit.entity.EntityType;
/**
 * Created by Matthew E on 9/21/2016.
 */
public enum MobType {

    SKELETON(0, EntityType.SKELETON, "Skeleton"),
    ZOMBIE(1, EntityType.ZOMBIE, "Zombie"),
    NAGA(2, EntityType.ZOMBIE, "Naga"),
    PIGMAN(3, EntityType.PIG_ZOMBIE, "PigMan"),
    WITCH(4, EntityType.WITCH, "Witch"),
    ;

    private int id;
    private EntityType entityType;
    private String name;

    MobType(int id, EntityType entityType, String name) {
        this.id = id;
        this.entityType = entityType;
        this.name = name;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
