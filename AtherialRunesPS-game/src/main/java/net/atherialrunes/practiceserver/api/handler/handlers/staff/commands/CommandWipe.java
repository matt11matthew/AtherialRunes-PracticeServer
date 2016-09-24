package net.atherialrunes.practiceserver.api.handler.handlers.staff.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matthew E on 9/24/2016 at 1:52 PM.
 */
public class CommandWipe extends AtherialCommand {
    public CommandWipe(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!Rank.isGM(player.getName())) {
                return true;
            }
        }
        if (args.length == 1) {
            String name = args[0];
            if ((Bukkit.getPlayerExact(name) != null) && (Bukkit.getPlayerExact(name).isOnline())) {
                Player player = Bukkit.getPlayerExact(name);
                GamePlayer gp = GameAPI.getGamePlayer(player);
                gp.wipe();
                sender.sendMessage(Utils.colorCodes("&cYou've wiped " + name));
                return true;
            }
        }
        return true;
    }
}
