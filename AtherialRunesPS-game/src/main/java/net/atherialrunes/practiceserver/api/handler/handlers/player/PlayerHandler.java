package net.atherialrunes.practiceserver.api.handler.handlers.player;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.GameConstants;
import net.atherialrunes.practiceserver.PracticeServer;
import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.CommandRules;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandToggle;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandToggleChaos;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandToggleDebug;
import net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles.CommandTogglePvP;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class PlayerHandler extends ListenerHandler {


    public static long releaseIn = 0L;

    @Override
    public void onLoad() {
        super.onLoad();
        task();

        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandToggle("toggle", Arrays.asList("toggles")));
        cm.registerCommand(new CommandToggleDebug("debug", Arrays.asList("toggledebug")));
        cm.registerCommand(new CommandTogglePvP("togglepvp", Arrays.asList("toggleplayervsplayer")));
        cm.registerCommand(new CommandToggleChaos("togglechaos", Arrays.asList("togglechaotic", "togglechao")));
        cm.registerCommand(new CommandRules("rules"));
        FileConfiguration c = YamlConfiguration.loadConfiguration(new File(PracticeServer.getInstance().getDataFolder() + "", "time.yml"));
        File file = new File(PracticeServer.getInstance().getDataFolder() + "", "time.yml");
        long time = c.getLong("release");
        if (time == 0L) {
            releaseIn = Utils.convertToMillis(2);
            c.set("release", releaseIn);
            try {
                c.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AtherialRunnable.getInstance().runAsyncRepeatingTask(this::saveTask, (20L * 300L), (20L * 300L));
    }

    public void saveTask() {
        Bukkit.getServer().broadcastMessage(Utils.colorCodes("&bBacking up playerdata..."));
        GameAPI.GAMEPLAYERS.values().forEach(gp -> {
            gp.upload();
        });
    }

    public static long getTimeUntilOut() {
        long sec = TimeUnit.MILLISECONDS.toSeconds(releaseIn);
        if (sec - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) > 0) {
            sec = sec - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
        return sec;
    }

    public static String parseTime() {
        return Utils.parseMilis(releaseIn);
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        DatabaseAPI.getInstance().requestPlayer(e.getUniqueId(), e.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        String MOTD = (ChatColor.WHITE + "" + ChatColor.BOLD + "               Atherial Runes Practice Server Patch 1.0   ");
        String DMOTD = (ChatColor.GRAY + "" + ChatColor.ITALIC + "         Donate at http://atherialrunes.buycraft.net/ for perks! ");
        GameAPI.handleLogin(e.getPlayer());
        GamePlayer gp = GameAPI.getGamePlayer(e.getPlayer());
        gp.msg(MOTD);
        gp.msg(DMOTD);
        if (gp.isNewPlayer()) {
            giveStarterKit(gp);
            gp.fw();
            TextComponent comp = new TextComponent(Utils.colorCodes("&6&l[CLICK]"));
            TextComponent comp1 = new TextComponent(Utils.colorCodes("&aRules: "));
            comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rules"));
            comp1.addExtra(comp);
            gp.msg("&b&l--------------------------------");
            gp.msg("&3Welcome " + gp.getName());
            gp.getPlayer().spigot().sendMessage(comp1);
            gp.msg("&b&l--------------------------------");
            Bukkit.getServer().broadcastMessage(Utils.colorCodes("&aWelcome " + gp.getName() + " &3To Atherial Runes Practice Server"));
            gp.getPlayer().setMaxHealth(200.0D);
            gp.getPlayer().setHealth(200.0D);
            gp.setNewPlayer(false);
        }
        gp.getPlayer().setHealthScale(20);
        gp.getPlayer().setHealthScaled(true);
        gp.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(30000);
        if (gp.getName().equals("matt11matthew")) {
            gp.setRank(Rank.DEVELOPER);
        }
    }

    public void giveSorry(GamePlayer gp) {
       gp.getPlayer().getInventory().addItem(AtherialItem.generate(GearType.SWORD, Tier.T2).setName("&aDowntime Sword").addLore("&7Sorry for downtime").build());
    }

    public void giveStarterKit(GamePlayer gp) {
        int i = RandomUtils.random(1, 2);
        boolean sword = false;
        switch (i) {
            case 1:
                sword = true;
                break;
            case 2:
                sword = false;
                break;
            default:
                break;
        }
        if (sword) {
            AtherialItem wep = new AtherialItem(Material.WOOD_SWORD);
            wep.setName("&fTraining Sword");
            wep.addLore("&cDMG: " + RandomUtils.random(18, 20) + " - " + RandomUtils.random(22, 40));
            gp.getPlayer().getInventory().setItem(0, wep.build());
            return;
        }
        AtherialItem axe = new AtherialItem(Material.WOOD_AXE);
        axe.setName("&fTraining Hatchet");
        axe.addLore("&cDMG: " + RandomUtils.random(18, 20) + " - " + RandomUtils.random(22, 35));
        gp.getPlayer().getInventory().setItem(0, axe.build());
        return;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        GameAPI.handleLogout(e.getPlayer());
    }

    @EventHandler
    public void onServerListPingEvent(ServerListPingEvent e) {
        e.setMaxPlayers(GameConstants.MAX_PLAYERS);
        String motd = Utils.colorCodes(YamlConfiguration.loadConfiguration(new File(PracticeServer.getInstance().getDataFolder() + "", "motd.yml")).getString("motd"));
        motd = motd.replaceAll("%nl%", "\n");
        motd = motd.replaceAll("%release%", parseTime());
        e.setMotd(Utils.colorCodes(motd));
    }

    //Move this wherever quickly put this here :/
    public static String asCentered(String text) {
        StringBuilder builder = new StringBuilder(text);
        char space = ' ';
        int distance = (45 - text.length()) / 2;
        for (int i = 0; i < distance; ++i) {
            builder.insert(0, space);
            builder.append(space);
        }
        return builder.toString();
    }

    public void task() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    GamePlayer gp = GameAPI.getGamePlayer(player);
                    player.setPlayerListName(gp.getAlignment().getTabPrefix() + player.getName());
                });
            }
        }, 5L, 5L);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }
        if (e.getClickedInventory().getTitle().equals("Toggles")) {
            e.setCancelled(true);
            if ((e.getCurrentItem() != null) && (e.getCurrentItem().getType() != Material.AIR) && (e.getCurrentItem().getType() == Material.INK_SACK)) {
                player.performCommand(e.getCurrentItem().getItemMeta().getLore().get(0));
                player.closeInventory();
                player.performCommand("toggles");
                return;
            }
        }
    }
}