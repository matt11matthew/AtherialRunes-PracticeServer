package net.atherialrunes.practiceserver.api.handler.handlers.mob;

import org.bukkit.entity.EntityType;

public enum MobType {

    SKELETON(0, EntityType.SKELETON, "skeleton");

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
