package net.atherialrunes.practiceserver.api.handler.handlers.party;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Matthew E on 9/21/2016.
 */
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
        member.setParty(this);
        msg(member.getName() + " has &d&njoined&7 your party.");
    }

    public void removeMember(GamePlayer member) {
        members.remove(member);
        member.setParty(null);
        msg(member.getName() + " has &d&nleft&7 your party.");
        if (leader.getName() == member.getName()) {
            pickNewLeader();
        }
        if (members.size() == 0) {
            disband();
        }
    }

    private void pickNewLeader() {
        try {
            Random random = new Random();
            int i = random.nextInt(members.size());
            GamePlayer newLeader = members.get(i);
            this.leader = newLeader;
            msg(leader.getName() + " has been &d&npromoted&7 to party leader");
        } catch (Exception e) {
            pickNewLeader();
        }
    }

    public void disband() {
        AtherialRunnable.getInstance().cancelTask(taskId);
        members.forEach(gp -> {
            gp.setParty(null);
        });
    }

    public void msg(String msg) {
        members.forEach(gp -> {
            gp.msg("&d<&lP&d> &7" + msg);
        });
    }

    public void displayBoard() {
        new ScoreboardHandler().showPartyBoard(this);
    }

    public static void create(GamePlayer leader) {
        Party party = new Party(leader);
    }
}
