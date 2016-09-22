package net.atherialrunes.practiceserver.api.command;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class AtherialCommandManager {

    public void registerCommand(AtherialCommand command) {
        command.register();
    }
}