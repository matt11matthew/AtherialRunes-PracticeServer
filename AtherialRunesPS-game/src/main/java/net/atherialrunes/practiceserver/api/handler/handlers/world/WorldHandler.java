package net.atherialrunes.practiceserver.api.handler.handlers.world;

import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobBuilder;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by Matthew E on 9/25/2016 at 10:49 AM.
 */
public class WorldHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!Rank.isGM(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (!Rank.isGM(e.getPlayer().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    //@EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (!MobBuilder.mobArmors.containsKey(e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCrop(BlockFadeEvent e) {
        e.setCancelled(true);
    }
}
