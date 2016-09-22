package net.atherialrunes.practiceserver.api.handler.health;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HealthHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {

            displayHealth(GameAPI.getGamePlayer(p));
        }
        super.onLoad();
    }


    @Override
    public void onUnload() {

    }


    public static void displayHealth(GamePlayer p) {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                int Health = p.getHP();
                p.getPlayer().setLevel(Health);

            }
        }, 0, 10);
    }
}
