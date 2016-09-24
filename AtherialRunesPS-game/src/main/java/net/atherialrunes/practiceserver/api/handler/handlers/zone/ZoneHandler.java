package net.atherialrunes.practiceserver.api.handler.handlers.zone;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.pvp.Alignment;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.commands.CommandZone;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Matthew E on 9/23/2016.
 */
public class ZoneHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();

        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandZone("zone"));
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        Zone from_zone = RegionUtils.getZone(from);
        Zone to_zone = RegionUtils.getZone(to);
        if ((from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ())) {
            if (from_zone != to_zone) {
                player.sendMessage(to_zone.getMessage());
                player.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.25F, 0.3F);
                if (!canEnterZone(GameAPI.getGamePlayer(player), to_zone)) {
                    e.setCancelled(true);
                    player.teleport(from);
                }
            }
        }
    }

    public boolean canEnterZone(GamePlayer gp, Zone to_zone) {
        Alignment alignment = gp.getAlignment();
        switch (to_zone) {
            case CHAOTIC:
                return true;
            case WILDERNESS:
                if (alignment == Alignment.CHAOTIC) {
                    gp.msg("&cYou &ncannot&c enter &lNON-PVP&c zones with a chaotic alignment.");
                    return false;
                } else if ((alignment == Alignment.NEUTRAL) && (gp.isInCombat())) {
                    gp.msg("&cYou &ncannot&c leave a chaotic zone while in combat.");
                    gp.msg("&7Out of combat in: &l" + gp.getCombatTime() + "s");
                    return false;
                } else {
                    return true;
                }
            case SAFE:
                if (alignment == Alignment.CHAOTIC) {
                    gp.msg("&cYou &ncannot&c enter &lNON-PVP&c zones with a chaotic alignment.");
                    return false;
                } else if ((alignment == Alignment.NEUTRAL) && (gp.isInCombat())) {
                    gp.msg("&cYou &ncannot&c leave a chaotic zone while in combat.");
                    gp.msg("&7Out of combat in: &l" + gp.getCombatTime() + "s");
                    return false;
                } else {
                    return true;
                }
        }
        return true;
    }
}
