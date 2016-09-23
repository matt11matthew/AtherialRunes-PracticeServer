package net.atherialrunes.practiceserver.api.handler.handlers.vendor;

import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.menu.Menu;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;

/**
 * Created by Matthew E on 9/22/2016.
 */
public abstract class Vendor {

    private String npcName;

    public Vendor(String npcName) {
        this.npcName = npcName;
    }

    public abstract Menu getMenu();

    public abstract void onMenuClick(GamePlayer gamePlayer, int slot, AtherialItem item);

    public abstract void onNPCRightClick(GamePlayer gamePlayer);

    public String getNPCName() {
        return npcName;
    }

    public void buy(GamePlayer gamePlayer, AtherialItem item) {
        int price = item.getPrice();
        if (price > gamePlayer.getGems()) {
            gamePlayer.msg("&cYou don't have enough GEM(s) for 1x of this item.");
            gamePlayer.msg("&cCOST: " + price + "g");
            gamePlayer.getPlayer().closeInventory();
            return;
        }
        if (gamePlayer.getPlayer().getInventory().firstEmpty() == -1) {
            gamePlayer.msg("&c&lWarning &cYour inventory is full!");
            gamePlayer.getPlayer().closeInventory();
            return;
        } else {
            gamePlayer.msg("&c-" + price + "&lG");
            gamePlayer.getPlayer().getInventory().setItem(gamePlayer.getPlayer().getInventory().firstEmpty(), item.removePrice().build());
            return;
        }
    }
}
