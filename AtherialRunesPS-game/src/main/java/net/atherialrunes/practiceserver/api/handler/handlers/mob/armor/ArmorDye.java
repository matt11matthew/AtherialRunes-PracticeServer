package net.atherialrunes.practiceserver.api.handler.handlers.mob.armor;

import org.bukkit.DyeColor;

public enum ArmorDye {

    NONE(null);

    private DyeColor dyeColor;

    ArmorDye(DyeColor dyeColor) {
        this.dyeColor = dyeColor;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }
}
