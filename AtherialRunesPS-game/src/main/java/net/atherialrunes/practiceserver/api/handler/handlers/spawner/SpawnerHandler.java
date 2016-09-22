package net.atherialrunes.practiceserver.api.handler.handlers.spawner;

import net.atherialrunes.practiceserver.PracticeServer;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class SpawnerHandler extends ListenerHandler {

    public static ConcurrentHashMap<Location, Spawner> spawners = new ConcurrentHashMap<>();

    public static HashMap<GamePlayer, Integer> placeStep = new HashMap<>();

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
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
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

    public void spawnMobTask() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                spawners.values().forEach(spawner -> {
                    int task = 0;
                    if (!spawner.isActive()) {
                        task = AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
                            @Override
                            public void run() {
                                if (spawner.getCurrentCooldown() > 0) {
                                    spawner.setCurrentCooldown((spawner.getCurrentCooldown() - 1));
                                    if (spawner.getCurrentCooldown() == 0) {
                                        spawner.setActive(true);
                                    }
                                }
                            }
                        }, 20L, 20L);
                    }
                    if (spawner.isActive()) {
                        spawner.setCurrentCooldown(spawner.getCooldown());
                        AtherialRunnable.getInstance().cancelTask(task);
                        spawner.setActive(false);
                        spawner.spawn();
                        return;
                    }
                });
            }
        }, 1L, 1L);
    }
}
