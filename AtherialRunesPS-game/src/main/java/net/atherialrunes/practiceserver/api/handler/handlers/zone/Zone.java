package net.atherialrunes.practiceserver.api.handler.handlers.zone;

import net.atherialrunes.practiceserver.utils.Utils;

public enum Zone {

    SAFE("&a&l                *** SAFE ZONE (DMG-OFF) ***"),
    WILDERNESS("&e&l           *** WILDERNESS (MOBS-ON, PVP-OFF) ***"),
    CHAOTIC("&c&l                *** CHAOTIC ZONE (PVP-ON) ***");

    String message;

    Zone(String message) {
        this.message = message;
    }

    public String getMessage() {
        return Utils.colorCodes(message);
    }
}