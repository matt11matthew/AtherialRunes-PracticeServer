package net.atherialrunes.practiceserver.api.handler.handlers.bank.commands;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommand;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Matthew E on 9/25/2016 at 10:57 AM.
 */
public class CommandBank extends AtherialCommand {
    public CommandBank(String command) {
        super(command);
    }

    public static HashMap<GamePlayer, Integer> bankOpening = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            GamePlayer gp = GameAPI.getGamePlayer(player);
            if (Rank.isSub(gp.getName())) {
                if (bankOpening.containsKey(gp)) {
                    return true;
                }
                switch (gp.getAlignment()) {
                    case LAWFUL:
                        break;
                    case NEUTRAL:
                        gp.msg("&cYou can't open your bank while neutral");
                        return true;
                    case CHAOTIC:
                        gp.msg("&cYou can't open your bank while chaotic");
                        return true;
                    default:
                        break;
                }
                if (gp.isInCombat()) {
                    gp.msg("&cYou cannot open your bank while in combat");
                    return true;
                }
                openTask(gp);
            } else {
                gp.msg("&aYou don't have /bank buy @ http://atherialrunes.buycraft.net/");
                return true;
            }
        }
        return true;
    }

    private void openTask(GamePlayer gp) {
        bankOpening.put(gp, 5);
    }
}
