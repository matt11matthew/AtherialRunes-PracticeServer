package net.atherialrunes.practiceserver.api.handler.handlers.spawner.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.SpawnerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.IntegerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShowMs extends AtherialCommand {
    public CommandShowMs(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            if (Rank.isGM(player.getName())) {
                if (args.length == 1) {
                    if (IntegerUtils.isInt(args[0])) {
                        int amount = 0;
                        for (Location location : SpawnerHandler.spawners.keySet()) {
                            if (location.distance(player.getLocation()) <= Integer.parseInt(args[0])) {
                                location.getBlock().setType(Material.MOB_SPAWNER);
                                amount++;
                            }
                        }
                        gp.msg("&aShowing " + amount + " spawners");
                        gp.msg("&7Radius: " + args[0].trim());
                        return true;
                    } else {
                        gp.msg("&c/showms <radius>");
                        return true;
                    }
                } else {
                    gp.msg("&c/showms <radius>");
                    return true;
                }
            }
        }
        return true;
    }
}