package net.atherialrunes.practiceserver.api.handler.handlers.player.commands.toggles;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.player.PlayerToggles;
import net.atherialrunes.practiceserver.api.menu.Menu;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Matthew E on 9/24/2016 at 1:07 PM.
 */
public class CommandToggle extends AtherialCommand {

    public CommandToggle(String command, List<String> aliases) {
        super(command, aliases);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            openToggleMenu(gp);
            return true;
        }
        return true;
    }

    public void openToggleMenu(GamePlayer gp) {
        new ToggleMenu(gp).open(gp.getPlayer());
    }

    public class ToggleMenu extends Menu {

        public ToggleMenu(GamePlayer gp) {
            super("Toggles", 9);

            int i = 0;

            for (PlayerToggles playerToggles : PlayerToggles.values()) {
                boolean isToggled = (boolean) DatabaseAPI.getInstance().getData(playerToggles.getDbField(), gp.getUniqueId());
                AtherialItem toggle = new AtherialItem(Material.INK_SACK);
                toggle.setDurability((isToggled) ? 10 : 8);
                String color = (isToggled) ? "&a" : "&c";
                toggle.setName(color + playerToggles.getName());
                toggle.addLore(playerToggles.getCommandName());
                setItem(i, toggle.build());
                i++;
            }
        }
    }
}
