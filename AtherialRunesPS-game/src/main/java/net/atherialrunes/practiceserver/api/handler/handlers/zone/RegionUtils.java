package net.atherialrunes.practiceserver.api.handler.handlers.zone;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/**
 * Created by Matthew E on 9/23/2016.
 */
public class RegionUtils {

    public static WorldGuardPlugin getWg() {
        return (WorldGuardPlugin) org.bukkit.Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    }

    public static boolean isWorldGuardEnabled() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        return (p.isEnabled()) ? true : false;
    }

    public static ApplicableRegionSet getRegionSet(Location loc) {
        return getWg().getRegionManager(loc.getWorld()).getApplicableRegions(loc);
    }

    public static boolean allowsPvP(Location loc) {
        return getRegionSet(loc).allows(DefaultFlag.PVP);
    }

    public static boolean allowsMobDamage(Location loc) {
        return getRegionSet(loc).allows(DefaultFlag.MOB_DAMAGE);
    }

    public static boolean isSafeZone(Location loc) {
        return ((!allowsMobDamage(loc)) && (!allowsPvP(loc))) ? true : false;
    }

    public static boolean isChaoticZone(Location loc) {
        return ((allowsMobDamage(loc)) && (allowsPvP(loc))) ? true : false;
    }

    public static boolean isWildernessZone(Location loc) {
        return ((allowsMobDamage(loc)) && (!allowsPvP(loc))) ? true : false;
    }

    public static Zone getZone(Location loc) {
        if (isSafeZone(loc)) {
            return Zone.SAFE;
        } else if (isChaoticZone(loc)) {
            return Zone.CHAOTIC;
        } else if (isWildernessZone(loc)) {
            return Zone.WILDERNESS;
        } else {
            return Zone.WILDERNESS;
        }
    }
}
