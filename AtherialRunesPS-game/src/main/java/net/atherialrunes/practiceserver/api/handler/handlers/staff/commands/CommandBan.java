package net.atherialrunes.practiceserver.api.handler.handlers.staff.commands;

import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Jax on 9/22/2016.
 */
public class CommandBan extends AtherialCommand {

    public CommandBan(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (Rank.isGM(p.getName())) {
                try {
                   
                }


            }
        }

    }
}
