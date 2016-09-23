package net.atherialrunes.practiceserver.api.handler.handlers.zone.commands;

import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.RegionUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matthew E on 9/23/2016.
 */
public class CommandZone extends AtherialCommand {
    public CommandZone(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(Utils.colorCodes(RegionUtils.getZone(player.getLocation()).getMessage()));
            return true;
        }
        return true;
    }
}
