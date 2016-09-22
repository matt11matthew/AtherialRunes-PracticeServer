package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import org.bukkit.entity.EntityType;

public enum MobType {

    SKELETON(0, EntityType.SKELETON, "Skeleton"),
    ZOMBIE(1, EntityType.ZOMBIE, "Zombie"),
    NAGA(2, EntityType.ZOMBIE, "Naga"),
    TROLL(3, EntityType.ZOMBIE, "Troll"),
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
