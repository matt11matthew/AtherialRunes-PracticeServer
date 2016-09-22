package net.atherialrunes.practiceserver.api.handler.handlers.vendor;

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

    public abstract void click(GamePlayer gamePlayer, int slot);

    public abstract void onNPCRightClick(GamePlayer gamePlayer);

    public String getNPCName() {
        return npcName;
    }
}
