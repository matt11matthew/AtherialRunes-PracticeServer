package net.atherialrunes.practiceserver.api.handler.handlers.spawner;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobBuilder;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import org.bukkit.Location;

/**
 * Created by Matthew E on 9/21/2016.
 */

@Data
public class Spawner {

    private Location location;
    private String mobData;
    private int cooldown;
    private int range;
    private int amount;
    private MobType mobType;
    private boolean elite;
    private int tier;
    private boolean active;
    private int currentCooldown;
    private Tier mobTier;

    public static Spawner getFromSpawnData(Location location, String spawn_data) {
        Spawner spawner = new Spawner();
        spawner.setAmount(Integer.parseInt(spawn_data.split("Amount:")[1].split(",")[0]));
        spawner.setRange(Integer.parseInt(spawn_data.split("Range:")[1].split(",")[0]));
        spawner.setCooldown(Integer.parseInt(spawn_data.split("Cooldown:")[1].split(",")[0]));
        spawner.setMobType(MobType.valueOf(spawn_data.split("MobType:")[1].split(",")[0]));
        spawner.setTier(Integer.parseInt(spawn_data.split("Tier:")[1].split(",")[0]));
        spawner.setElite(Boolean.parseBoolean(spawn_data.split("Elite:")[1].split(",")[0]));
        spawner.setLocation(location);
        spawner.setCurrentCooldown(spawner.getCooldown());
        spawner.setMobTier(Tier.fromTier(spawner.getTier()));
        return spawner;
    }

    @Override
    public String toString() {
        return "MobType:" + getMobType().toString() + ",Amount:" + getCooldown() + ",Range:" + getRange() + ",Tier:" + getTier() + ",Elite:" + isElite()  + ",Cooldown:" + getCooldown() + ",";
    }

    public void spawn() {
        MobBuilder.spawn(this);
        return;
    }
}

