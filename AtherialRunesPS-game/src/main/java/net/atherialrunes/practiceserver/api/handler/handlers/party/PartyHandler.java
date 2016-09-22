package net.atherialrunes.practiceserver.api.handler.handlers.party;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.party.commands.CommandP;
import net.atherialrunes.practiceserver.api.handler.handlers.party.commands.CommandPQuit;
import net.atherialrunes.practiceserver.api.handler.handlers.party.commands.CommandParty;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PartyHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandParty("party"));
        cm.registerCommand(new CommandPQuit("pquit"));
        cm.registerCommand(new CommandP("p"));
    }

    @Override
    public void onUnload() {

    }

    public static void createParty(GamePlayer leader) {
        Party.create(leader);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        GamePlayer gp = GameAPI.getGamePlayer(e.getPlayer());
        if (gp.getParty() != null) {
            if (gp.getChatChannel().equals("party")) {
                gp.getPlayer().performCommand("p " + e.getMessage());
                e.setCancelled(true);
            }
        }
    }
}
