package net.atherialrunes.practiceserver.api.handler.handlers.player.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.menu.Menu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matthew E on 9/25/2016 at 11:26 AM.
 */
public class CommandRules extends AtherialCommand {
    public CommandRules(String command) {
        super(command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            new RuleMenu("Rules", 9).open(gp.getPlayer());
            return true;
        }
        return true;
    }

    public class RuleMenu extends Menu {

        public RuleMenu(String title, int slots) {
            super(title, slots);

            AtherialItem rule1 = new AtherialItem(Material.BOOK);
            rule1.setName("&a1. No Hacking");
            rule1.addLore("&7No hacked clients allowed");
            setItem(0, rule1.build());
        }
    }
}
