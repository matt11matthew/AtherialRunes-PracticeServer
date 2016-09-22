package net.atherialrunes.practiceserver.api.handler.handlers.staff;

import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;

public class StaffHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        /**
         * Register commands here
         */
        AtherialCommandManager cm = new AtherialCommandManager();
    }

    @Override
    public void onUnload() {
        
    }
}
