package net.atherialrunes.practiceserver.api.handler.handlers.nocheat;

import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.nocheat.checks.FlyCheck;

/**
 * Created by Matthew E on 9/26/2016 at 5:10 PM.
 */
public class NoCheatHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        registerChecks();
    }

    @Override
    public void onUnload() {

    }

    private void registerChecks() {
        CheckManager.registerCheck(new FlyCheck());
        CheckManager.loadChecks();
    }
}

