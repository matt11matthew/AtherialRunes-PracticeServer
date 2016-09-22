package net.atherialrunes.practiceserver.api.handler.handlers.party.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.party.Party;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandP extends AtherialCommand {
    public CommandP(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            if (gp.getParty() != null) {
                Party party = gp.getParty();
                if (args.length > 1) {
                    String msg = "";

                    for (String s : args) msg += s + " ";
                    msg = ChatColor.WHITE + msg;
                    if (msg.endsWith(" ")) msg = msg.substring(0, msg.length() -1);

                    party.msg(gp.getRank().getPrefix() + ": " + msg);
                    return true;
                } else {
                    
                }
            }
        }
        return true;
    }
}