package net.atherialrunes.practiceserver.api.handler.handlers.rank;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.bank.BankHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.SpawnerHandler;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class RankHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (SpawnerHandler.placing.containsKey(gp)) {
            return;
        }
        if (BankHandler.withdraw.contains(gp)) {
            return;
        }
        Bukkit.getServer().broadcastMessage(Utils.colorCodes(gp.getRank().getChatPrefix(e.getPlayer()) + ": &f" + e.getMessage()));
    }
}
