package net.atherialrunes.practiceserver.api.handler.handlers.party.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.party.Party;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPQuit extends AtherialCommand {
    public CommandPQuit(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            if (gp.getParty() != null) {
                Party party = gp.getParty();
                party.removeMember(gp);
                gp.msg("&cYou've left the party!");
                return true;
            }
        }
        return true;
    }
}