package net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.itemvendor;

import net.atherialrunes.practiceserver.api.handler.handlers.vendor.Vendor;
import net.atherialrunes.practiceserver.api.menu.Menu;
import net.atherialrunes.practiceserver.api.player.GamePlayer;

public class ItemVendor extends Vendor {

    public ItemVendor(String npcName) {
        super(npcName);
    }

    @Override
    public Menu getMenu() {
        return new ItemVendorMenu();
    }

    @Override
    public void click(GamePlayer gamePlayer, int slot) {
        
    }

    @Override
    public void onNPCRightClick(GamePlayer gamePlayer) {
        getMenu().open(gamePlayer.getPlayer());
    }
}
