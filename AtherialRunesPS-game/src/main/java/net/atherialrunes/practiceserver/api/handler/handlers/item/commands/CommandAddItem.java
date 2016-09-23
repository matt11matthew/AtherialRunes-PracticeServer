package net.atherialrunes.practiceserver.api.handler.handlers.item.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.item.ItemGenerator;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddItem extends AtherialCommand {
    public CommandAddItem(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gamePlayer = GameAPI.getGamePlayer(player);
            if (Rank.isGM(gamePlayer.getName())) {
                try {
                    if (args.length == 2) {
                        int tier = Integer.parseInt(args[1]);
                        GearType type = GearType.valueOf(args[2].toUpperCase());
                        player.getInventory().addItem(ItemGenerator.generateGear(tier, type));
                        return true;
                    }
                } catch (Exception e) {
                    return true;
                }
            }
        }
        return true;
    }
}
