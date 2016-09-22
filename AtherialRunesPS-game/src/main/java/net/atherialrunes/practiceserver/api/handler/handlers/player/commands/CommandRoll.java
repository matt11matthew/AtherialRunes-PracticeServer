package net.atherialrunes.practiceserver.api.handler.handlers.player.commands;

import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class CommandRoll extends AtherialCommand {
    public CommandRoll(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                int max = 0;
                try {
                    max = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage(Utils.colorCodes("&c&lNon-Numeric Max Number. /roll <1 - 10000>"));
                    return true;
                }
                if ((max < 1) || (max > 10000)) {
                    player.sendMessage(Utils.colorCodes("&c&lIncorrect Syntax.&7 /roll <1 - 10000>"));
                    return true;
                } else {
                    Random r = new Random();
                    int roll = r.nextInt(max + 1);
                    String name = Rank.getChatPrefix(player);
                    String msg = name + " &7has rolled a &7&l&n" + roll + "&7 out of &7&l&n" + max + ".";
                    Bukkit.getServer().broadcastMessage(Utils.colorCodes(msg));
                    return true;
                }
            }
        }
        return true;
    }
}