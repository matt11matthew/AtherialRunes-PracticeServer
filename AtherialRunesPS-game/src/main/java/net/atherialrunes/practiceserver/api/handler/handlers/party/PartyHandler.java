package net.atherialrunes.practiceserver.api.handler.handlers.party;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.party.commands.CommandPQuit;
import net.atherialrunes.practiceserver.api.handler.handlers.party.commands.CommandParty;
import net.atherialrunes.practiceserver.api.player.GamePlayer;

public class PartyHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandParty("party"));
        cm.registerCommand(new CommandPQuit("pquit"));
    }

    @Override
    public void onUnload() {

    }

    public static void createParty(GamePlayer leader) {
        Party.create(leader);
    }
}
