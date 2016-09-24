package net.atherialrunes.practiceserver.api.handler.handlers.player;

import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.database.EnumData;
import net.atherialrunes.practiceserver.api.handler.database.EnumOperators;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Matthew E on 9/24/2016 at 1:03 PM.
 */
public enum PlayerToggles {

    DEBUG(0, EnumData.TOGGLE_DEBUG, "toggledebug", "Toggles displaying combat debug messages.", "Debug", "Debug Toggle"),
    PVP(1, EnumData.TOGGLE_PVP, "togglepvp", "Toggles all outgoing PvP damage (anti-neutral).", "Outgoing PvP Damage", "Toggle PvP"),
    CHAOTIC_PREVENTION(2, EnumData.TOGGLE_CHAOTIC_PREVENTION, "togglechaos", "Toggles killing blows on lawful players (anti-chaotic).", "Anti-Chaotic", "Toggle Chaotic"),;
    private int id;
    private EnumData dbField;
    private String commandName;
    private String description;
    private String friendlyName;
    private String name;

    PlayerToggles(int id, EnumData dbField, String commandName, String description, String friendlyName, String name) {
        this.id = id;
        this.dbField = dbField;
        this.commandName = commandName;
        this.description = description;
        this.friendlyName = friendlyName;
        this.name = name;
    }

    public static PlayerToggles getById(int id) {
        for (PlayerToggles playerToggles : values()) {
            if (playerToggles.id == id) {
                return playerToggles;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public static PlayerToggles getByCommandName(String commandName) {
        for (PlayerToggles playerToggles : values()) {
            if (playerToggles.commandName.equalsIgnoreCase(commandName)) {
                return playerToggles;
            }
        }
        return null;
    }

    public EnumData getDbField() {
        return dbField;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public String getDescription() {
        return description;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setToggleState(Player player, boolean state) {
        DatabaseAPI.getInstance().update(player.getUniqueId(), EnumOperators.$SET, dbField, state, false);
        player.sendMessage((state ? ChatColor.GREEN : ChatColor.RED) + friendlyName + " - " + ChatColor.BOLD + (state ? "ENABLED" : "DISABLED"));
    }
}