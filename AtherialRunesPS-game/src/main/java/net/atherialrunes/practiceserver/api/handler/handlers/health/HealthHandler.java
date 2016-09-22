package net.atherialrunes.practiceserver.api.handler.handlers.health;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import org.bukkit.Bukkit;
/**
 * Created by Matthew E on 9/21/2016.
 */
public class HealthHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        levelBarTask();
    }

    @Override
    public void onUnload() {

    }


    public void levelBarTask() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    GamePlayer gp = GameAPI.getGamePlayer(player);
                    player.setLevel(gp.getHp());
                });
            }
        }, 5L, 5L);
    }
}
