package net.atherialrunes.practiceserver.api.handler.handlers.donor;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.donor.commands.CommandAddSub;

/**
 * Created by Matthew E on 9/25/2016 at 12:17 PM.
 */
public class DonorHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();

        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandAddSub("addsub"));
    }

    @Override
    public void onUnload() {

    }
}
