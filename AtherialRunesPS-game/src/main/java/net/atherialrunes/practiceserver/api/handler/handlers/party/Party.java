package net.atherialrunes.practiceserver.api.handler.handlers.party;

import lombok.Data;
import net.atherialrunes.practiceserver.api.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;

import java.util.ArrayList;
import java.util.List;

@Data
public class Party {

    private GamePlayer leader;
    private List<GamePlayer> members = new ArrayList<>();
    private int taskId = 0;

    public Party(GamePlayer leader) {
        this.leader = leader;
        members.add(leader);
        task();
    }

    public void task() {
        taskId = AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                displayBoard();
            }
        }, 5L, 5L);
    }

    public void addMember(GamePlayer member) {
        members.add(member);
        msg(member.getName() + " has &d&njoined&7 your party.");
    }

    public void disband() {
        AtherialRunnable.getInstance().cancelTask(taskId);
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
