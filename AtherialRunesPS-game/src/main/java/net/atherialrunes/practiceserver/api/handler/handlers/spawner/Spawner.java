package net.atherialrunes.practiceserver.api.handler.handlers.spawner;

import lombok.Data;
import lombok.Getter;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobType;
import org.bukkit.Location;

@Data
public class Spawner {

    @Getter
    private Location location;

    @Getter
    private String mobData;

    private int cooldown;
    private int range;
    private int amount;
    private MobType mobType;
    private boolean elite;
    private int tier;

    public static Spawner getFromSpawnData(Location location, String spawn_data) {
        Spawner spawner = new Spawner();
        spawner.setAmount(Integer.parseInt(spawn_data.split("Amount:")[1].split(",")[0]));
        spawner.setRange(Integer.parseInt(spawn_data.split("Range:")[1].split(",")[0]));
        spawner.setCooldown(Integer.parseInt(spawn_data.split("Cooldown:")[1].split(",")[0]));
        spawner.setMobType(MobType.valueOf(spawn_data.split("MobType:")[1].split(",")[0]));
        spawner.setTier(Integer.parseInt(spawn_data.split("Tier:")[1].split(",")[0]));
        spawner.setElite(Boolean.parseBoolean(spawn_data.split("Elite:")[1].split(",")[0]));
        spawner.setLocation(location);
        return spawner;
    }

    @Override
    public String toString() {
        return "MobType:" + getMobType().getName() + ",Amount:" + getCooldown() + ",Range:" + getRange() + ",Tier:" + getTier() + ",Elite:" + isElite()  + ",Cooldown:" + getCooldown() + ",";
    }
}

