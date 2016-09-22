package net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.itemvendor;

import net.atherialrunes.practiceserver.api.handler.handlers.vendor.Vendor;
import net.atherialrunes.practiceserver.api.menu.Menu;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class ItemVendor extends Vendor {

    private int slots = 9;

    public ItemVendor(String npcName, int slots) {
        super(npcName);
        this.slots = slots;
    }

    @Override
    public Menu getMenu() {
        return new ItemVendorMenu(getNPCName(), slots);
    }

    @Override
    public void onMenuClick(GamePlayer gamePlayer, int slot) {

    }

    @Override
    public void onNPCRightClick(GamePlayer gamePlayer) {
        getMenu().open(gamePlayer.getPlayer());
    }
}
