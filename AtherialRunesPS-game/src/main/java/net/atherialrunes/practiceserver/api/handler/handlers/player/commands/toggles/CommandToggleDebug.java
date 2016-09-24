package net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.player.PlayerToggles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Matthew E on 9/24/2016 at 1:07 PM.
 */
public class CommandToggleDebug extends AtherialCommand {

    public CommandToggleDebug(String command, List<String> aliases) {
        super(command, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            PlayerToggles toggle = PlayerToggles.DEBUG;
            toggle.setToggleState(player, !(boolean) DatabaseAPI.getInstance().getData(toggle.getDbField(), player.getUniqueId()));
            return true;
        }
        return true;
    }

}
