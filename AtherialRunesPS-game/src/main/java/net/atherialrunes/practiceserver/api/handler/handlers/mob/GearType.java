package net.atherialrunes.practiceserver.api.handler.handlers.mob;

public enum GearType {

    HELMET(0, "helmet"),
    CHESTPLATE(1, "chestplate"),
    LEGGINGS(2, "leggings"),
    BOOTS(3, "boots"),
    SWORD(4, "sword"),
    AXE(5, "axe");

    private int id;
    private String name;

    GearType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isWeapon() {
        return ((this == AXE) || (this == SWORD));
    }

    public boolean isArmor() {
        return ((this == HELMET) || (this == CHESTPLATE) || (this == LEGGINGS) || (this == BOOTS));
    }
}
