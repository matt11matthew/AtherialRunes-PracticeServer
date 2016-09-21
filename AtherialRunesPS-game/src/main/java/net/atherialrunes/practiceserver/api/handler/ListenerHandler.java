package net.atherialrunes.practiceserver.api.handler;

import net.atherialrunes.practiceserver.PracticeServer;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class ListenerHandler implements Handler, Listener {

    @Override
    public void onLoad() {
        Bukkit.getPluginManager().registerEvents(this, PracticeServer.getInstance());
    }
}
