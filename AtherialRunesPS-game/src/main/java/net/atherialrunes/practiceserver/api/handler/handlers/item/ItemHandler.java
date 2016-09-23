package net.atherialrunes.practiceserver.api.handler.handlers.item;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.item.commands.CommandAddItem;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class ItemHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandAddItem("additem"));
    }

    @Override
    public void onUnload() {

    }
}
