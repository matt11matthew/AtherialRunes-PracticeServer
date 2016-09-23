package net.atherialrunes.practiceserver.api.handler.handlers.zone;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
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
                if (!canEnterZone(player, to_zone)) {
                    e.setCancelled(true);
                    player.teleport(from);
                }
            }
        }
    }

    public boolean canEnterZone(Player p, Zone to_zone) {
        return true;
    }
}
