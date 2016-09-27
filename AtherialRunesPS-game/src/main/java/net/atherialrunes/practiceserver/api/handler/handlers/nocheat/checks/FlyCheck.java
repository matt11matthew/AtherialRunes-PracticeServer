package net.atherialrunes.practiceserver.api.handler.handlers.nocheat.checks;

import net.atherialrunes.practiceserver.api.handler.handlers.nocheat.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Matthew E on 9/26/2016 at 5:12 PM.
 */
public class FlyCheck implements Check {

    @EventHandler
    public void onFlyHack(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!player.getAllowFlight()) {
            if ((e.getTo().getY() - e.getFrom().getY()) >= 1.5) {
                player.teleport(e.getFrom());
            }
        }
    }
}
