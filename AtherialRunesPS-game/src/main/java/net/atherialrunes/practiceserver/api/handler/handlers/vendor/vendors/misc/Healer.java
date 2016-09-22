package net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.misc;

import net.atherialrunes.practiceserver.api.handler.handlers.vendor.Vendor;
import net.atherialrunes.practiceserver.api.menu.Menu;
import net.atherialrunes.practiceserver.api.player.GamePlayer;

public class Healer extends Vendor {

    public Healer(String npcName) {
        super(npcName);
    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public void click(GamePlayer gamePlayer, int slot) {
        return;
    }

    @Override
    public void onNPCRightClick(GamePlayer gamePlayer) {
        gamePlayer.getPlayer().setHealth(gamePlayer.getPlayer().getMaxHealth());
    }
}
