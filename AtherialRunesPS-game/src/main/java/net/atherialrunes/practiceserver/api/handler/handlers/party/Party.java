package net.atherialrunes.practiceserver.api.handler.handlers.party;

import lombok.Data;
import net.atherialrunes.practiceserver.api.player.GamePlayer;

import java.util.ArrayList;
import java.util.List;

@Data
public class Party {

    private GamePlayer leader;
    private List<GamePlayer> members = new ArrayList<>();

    public Party(GamePlayer leader) {
        this.leader = leader;
        members.add(leader);
        displayBoard();
    }

    public void addMember(GamePlayer member) {
        members.add(member);
        displayBoard();
        msg(member.getName() + " has &d&njoined&7 your party.");
    }

    public void msg(String msg) {
        members.forEach(gp -> {
            gp.msg("&d<&lP&d> &7" + msg);
        });
    }

    public void displayBoard() {
        new ScoreboardHandler().showPartyBoard(this);
    }
}
