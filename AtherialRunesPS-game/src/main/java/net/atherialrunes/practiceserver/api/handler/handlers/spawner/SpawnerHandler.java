package net.atherialrunes.practiceserver.api.handler.handlers.spawner;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.GameConstants;
import net.atherialrunes.practiceserver.PracticeServer;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobBuilder;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.armor.MobArmor;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class SpawnerHandler extends ListenerHandler {

    public static ConcurrentHashMap<Location, Spawner> spawners = new ConcurrentHashMap<>();

    public static HashMap<GamePlayer, Location> placing = new HashMap<>();

    @Override
    public void onLoad() {
        super.onLoad();
        loadAllSpawners();
        spawnMobTask();
    }

    @Override
    public void onUnload() {
        saveAllSpawners();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (Rank.isGM(player.getName())) {
            if (e.getBlock().getType() == Material.MOB_SPAWNER) {
                if (spawners.containsKey(e.getBlock().getLocation())) {
                    spawners.remove(e.getBlock().getLocation());
                    gp.msg("&cYou've removed a spawner!");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (Rank.isGM(player.getName())) {
            if (e.getBlock().getType() == Material.MOB_SPAWNER) {
                gp.msg("&aPlease type the spawner info");
                gp.msg("&fMobType:skeleton,Tier:1,Elite:false,Range:5,Cooldown:60,Amount:1,");
                e.setCancelled(true);
                placing.put(gp, e.getBlock().getLocation());
            }
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player player = e.getPlayer();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (Rank.isGM(player.getName())) {
            if (placing.containsKey(gp)) {
                e.setCancelled(true);
                try {
                    spawners.put(placing.get(gp), Spawner.getFromSpawnData(placing.get(gp), e.getMessage().trim()));
                    gp.msg("&aYou've placed at spawner!");
                    spawners.get(placing.get(gp)).spawn();
                    placing.remove(gp);
                    return;
                } catch (Exception ee) {
                    gp.msg("&cError");
                    placing.remove(gp);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (Rank.isGM(player.getName())) {
            if (action == Action.RIGHT_CLICK_BLOCK) {
                if ((e.getClickedBlock() != null) && (e.getClickedBlock().getType() == Material.MOB_SPAWNER)) {
                    if (spawners.containsKey(e.getClickedBlock().getLocation())) {
                        e.setCancelled(true);
                        Spawner spawner = spawners.get(e.getClickedBlock().getLocation());
                        gp.msg("&a--------------------------------");
                        gp.msg("&cMob: " + spawner.getMobType().getName());
                        gp.msg("&cTier: " + spawner.getMobTier().toString());
                        gp.msg("&cCooldown: " + spawner.getCooldown());
                        gp.msg("&cRange: " + spawner.getRange());
                        gp.msg("&cAmount: " + spawner.getAmount());
                        gp.msg("&a--------------------------------");
                        return;
                    }
                }
            }
        }
    }

    public void saveAllSpawners() {
        String all_dat = "";
        for (Map.Entry<Location, Spawner> entry : spawners.entrySet()) {
            Location location = entry.getKey();
            if (location.getWorld().getName().equalsIgnoreCase(((World) Bukkit.getWorlds().get(0)).getName())) {
                Spawner spawn_data = (Spawner) entry.getValue();
                all_dat = all_dat + location.getX() + "," + location.getY() + "," + location.getZ() + "=" + spawn_data.toString() + "\r\n";
            }
        }
        if (all_dat.length() > 1) {
            try {

                DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(PracticeServer.getInstance().getDataFolder() + "/spawners/", "spawners.dat"), false));
                dos.writeBytes(all_dat + "\n");
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadAllSpawners() {
        try {
            File file = new File(PracticeServer.getInstance().getDataFolder() + "/spawners/", "spawners.dat");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] cords = line.split("=")[0].split(",");
                    Location location = new Location(Bukkit.getWorlds().get(0), Double.parseDouble(cords[0]), Double.parseDouble(cords[1]), Double.parseDouble(cords[2]));
                    String spawn_data = line.split("=")[1];
                    spawners.put(location, Spawner.getFromSpawnData(location, spawn_data));
                }
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private Set<Entity> getEntitiesInChunk(Location l, int chunkRadius) {
        Block b = l.getBlock();
        Set<Entity> entities = new HashSet<>();
        for (int x = -16 * chunkRadius; x <= 16 * chunkRadius; x += 16) {
            for (int z = -16 * chunkRadius; z <= 16 * chunkRadius; z += 16) {
                for (Entity e : b.getRelative(x, 0, z).getChunk().getEntities()) {
                    if (!(e instanceof Player)) {
                        if (e.hasMetadata("NPC")) continue;
                        entities.add(e);
                    }
                }
            }
        }
        return entities;
    }

    private Set<LivingEntity> getEntitiesInChunks(Location l, int chunkRadius) {
        Block b = l.getBlock();
        Set<Entity> oldEntities = getEntitiesInChunk(l, chunkRadius);
        Set<LivingEntity> entities = new HashSet<>();
        for (Entity entity : oldEntities) {
            for (MobType mobType : MobType.values()) {
                if (entity.getType().equals(mobType.getEntityType())) {
                    for (MobArmor mobArmor : MobBuilder.mobArmors.values()) {
                        LivingEntity mob = (LivingEntity) entity;
                        if (mobArmor.getSpawner().equals(MobBuilder.mobArmors.get(mob))) {
                            entities.add(mob);
                        }
                    }
                }
            }
        }
        return entities;
    }

    public void spawnMobTask() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                spawners.values().forEach(spawner -> {
                    if (!spawner.isActive()) {
                        if (spawner.getCurrentCooldown() > 0) {
                            spawner.setCurrentCooldown((spawner.getCurrentCooldown() - 1));
                            if (spawner.getCurrentCooldown() == 0) {
                                spawner.setActive(true);
                            }
                        }
                    } else if (spawner.isActive()) {
                        if (canSpawn(spawner)) {
                            spawner.setCurrentCooldown(spawner.getCooldown());
                            spawner.setActive(false);
                            spawner.spawn();
                            return;
                        }
                    }
                });
            }
        }, 20L, 20L);
    }

    private boolean canSpawn(Spawner spawner) {
        Location location = spawner.getLocation();
        if (getEntitiesInChunks(location, 1).size() < GameConstants.MAX_ENTITIES_IN_CHUNK) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getWorld().getName().equals(spawner.getLocation().getWorld().getName())) continue;
                if (location.distance(player.getLocation()) <= 16) {
                    return true;
                }
            }
        }
        return false;
    }
}