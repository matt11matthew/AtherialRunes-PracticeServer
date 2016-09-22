package net.atherialrunes.practiceserver.api.handler.handlers.vendor.vendors.foodvendor;

import net.atherialrunes.practiceserver.api.handler.handlers.vendor.Vendor;
import net.atherialrunes.practiceserver.api.menu.Menu;
import net.atherialrunes.practiceserver.api.player.GamePlayer;

public class FoodVendor extends Vendor {

    private int slots = 9;

    public FoodVendor(String npcName, int slots) {
        super(npcName);
        this.slots = slots;
    }

    @Override
    public Menu getMenu() {
        return new FoodVendorMenu(getNPCName(), slots);
    }

    @Override
    public void click(GamePlayer gamePlayer, int slot) {

    }

    @Override
    public void onNPCRightClick(GamePlayer gamePlayer) {
        getMenu().open(gamePlayer.getPlayer());
    }
}
