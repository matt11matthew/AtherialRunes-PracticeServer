package net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.misc;

import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.vendor.Vendor;
import net.atherialrunes.practiceserver.api.menu.Menu;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class Banker extends Vendor {

    public Banker(String npcName) {
        super(npcName);
    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public void onMenuClick(GamePlayer gamePlayer, int slot, AtherialItem item) {
        return;
    }

    @Override
    public void onNPCRightClick(GamePlayer gamePlayer) {
        gamePlayer.getPlayer().openInventory(gamePlayer.getPlayer().getEnderChest());
        return;
    }
}
