package net.atherialrunes.practiceserver.api.handler.handlers.donor.commands;

import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.database.EnumData;
import net.atherialrunes.practiceserver.api.handler.database.EnumOperators;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Matthew E on 9/25/2016 at 12:18 PM.
 */
public class CommandAddSub extends AtherialCommand {
    public CommandAddSub(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 2) {
                Bukkit.getServer().broadcastMessage(Utils.colorCodes("&aThank you " + args[0] + " for buying SUB &3$4.99/per month"));
                DatabaseAPI.getInstance().update(UUID.fromString(args[1]), EnumOperators.$SET, EnumData.RANK, Rank.SUB.toString(), true);
                return true;
            }
        }
        return true;
    }
}
