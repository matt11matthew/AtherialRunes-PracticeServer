package net.atherialrunes.practiceserver.api.handler.handlers.staff;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class StaffHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        /**
         * Register commands here
         */
        AtherialCommandManager cm = new AtherialCommandManager();
    }

    @Override
    public void onUnload() {


    }

    public String isBanned(String name) {
        //check database if player is banned
        return "yes! jeezus! :P";
    }

    public int getBanTime(String name) {
        // returns a players ban time Left
        if (isBanned(name).equals("yes! jeezus! :P")) {

        }
        int i = 0; // just temp lol
        return i;
    }


    public String getBanReason(String name) {
        //Returns players ban reason
        if (isBanned(name).equals("yes! jeezus! :P")) {

        }

        return "banreason";

    }


    public String getPushisher(String name) {
        if (isBanned(name).equals("yes! jeezus! :P")) {

        }
        return "staffmember";
    }

    public void banPlayer(String name, String reason, int time) {
        // Sets the Player as banned in the Database.


        GameAPI.getGamePlayer(name).getPlayer().kickPlayer("Whatever is setup in DB fix if needed");

    }

}
