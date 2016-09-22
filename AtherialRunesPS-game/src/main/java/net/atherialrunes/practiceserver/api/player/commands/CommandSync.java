package net.atherialrunes.practiceserver.api.player.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSync extends AtherialCommand {

    public CommandSync(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            try {
                gp.upload();
            } finally {
                gp.msg("&aSynced player data to &nHIVE&a server.");
            }
            return true;
        }
        return true;
    }
}
