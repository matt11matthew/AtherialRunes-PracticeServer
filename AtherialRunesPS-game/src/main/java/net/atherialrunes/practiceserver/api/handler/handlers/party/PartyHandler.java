package net.atherialrunes.practiceserver.api.handler.handlers.party;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;

public class PartyHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        AtherialCommandManager cm = new AtherialCommandManager();
    }

    @Override
    public void onUnload() {

    }
}
