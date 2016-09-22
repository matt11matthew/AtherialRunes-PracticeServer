package net.atherialrunes.practiceserver.api.handler.handlers.party.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.party.PartyHandler;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandParty extends AtherialCommand {
    public CommandParty(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            if (gp.getParty() != null) {
                gp.msg("&cYou're already in a party!");
                return true;
            }
            PartyHandler.createParty(gp);
            gp.getParty().msg("Party created!");
            return true;
        }
        return true;
    }
}