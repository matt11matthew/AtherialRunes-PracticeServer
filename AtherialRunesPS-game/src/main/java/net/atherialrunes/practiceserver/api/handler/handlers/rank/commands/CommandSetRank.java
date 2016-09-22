package net.atherialrunes.practiceserver.api.handler.handlers.rank.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetRank extends AtherialCommand {
    public CommandSetRank(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (!Rank.isGM(sender.getName())) {
                return true;
            }
        }
        if (args.length == 2) {
            String name = args[0];
            String rank = args[1];
            if (!Rank.isRank(rank)) {
                sender.sendMessage(Utils.colorCodes("&c/setrank <NAME> <RANK>"));
                return true;
            }
            if ((Bukkit.getPlayerExact(name) != null) && (Bukkit.getPlayerExact(name).isOnline())) {
                GamePlayer gp = GameAPI.getGamePlayer(Bukkit.getPlayerExact(name));
                gp.setRank(Rank.valueOf(rank.toUpperCase()));
                sender.sendMessage(Utils.colorCodes("&aYou've set " + name + "'s rank to " + rank));
                return true;
            }
        }
        return true;
    }
}
