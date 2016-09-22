package net.atherialrunes.practiceserver.api.command;

public class AtherialCommandManager {

    public void registerCommand(AtherialCommand command) {
        command.register();
    }
}