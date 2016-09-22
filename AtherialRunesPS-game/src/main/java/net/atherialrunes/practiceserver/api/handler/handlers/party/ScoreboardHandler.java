package net.atherialrunes.practiceserver.api.handler.handlers.party;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class ScoreboardHandler extends ListenerHandler {

    private static HashMap<GamePlayer, Scoreboard> boards = new HashMap<>();
    private static Scoreboard main;

    @Override
    public void onLoad() {
        super.onLoad();
        main = Bukkit.getScoreboardManager().getNewScoreboard();
        headHPTask();
    }

    @Override
    public void onUnload() {

    }

    public void headHPTask() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    setOverheadHP(GameAPI.getGamePlayer(player), (int) player.getHealth());
                });
            }
        }, 5L, 5L);
    }

    public void showPartyBoard(Party party) {
        party.getMembers().forEach(gp -> {
            Scoreboard board = getBoard(gp);
            Objective partyBoard = board.registerNewObjective(party.getLeader().getName() + ":party", "dummy");
            partyBoard.setDisplayName(Utils.colorCodes(" &d&lParty "));
            partyBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
            String name = gp.getName();
            if(name.length() > 14) {
                name = name.substring(0, 14);
            }
            if (party.getLeader().getName().equals(gp.getName())) {
                name = "&l" + gp.getName();
            }
            int hp = gp.getHp();
            partyBoard.getScore(name).setScore(hp);
            gp.getPlayer().setScoreboard(board);
        });
    }

    public void updateScoreboard(GamePlayer gp) {
        if (gp.getParty() == null) {
            setOverheadHP(gp, gp.getHp());
        }
    }

    public void updatePartyBoard(Party party) {
        party.getMembers().forEach(gp -> {
            Scoreboard board = getBoard(gp);
            board.getObjective(party.getLeader().getName() + ":party").unregister();
            showPartyBoard(party);
        });
    }

    public static Scoreboard initiateScoreobard(Scoreboard s){
        Objective objective = s.registerNewObjective("hpdisplay", "dummy");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(Utils.colorCodes("&c❤"));
        return s;
    }

    public static Scoreboard getBoard(final GamePlayer plr) {
        if(!boards.containsKey(plr)) {
            Scoreboard b = initiateScoreobard(Bukkit.getScoreboardManager().getNewScoreboard());
            boards.put(plr, b);
            plr.getPlayer().setScoreboard(b);
        }
        return boards.get(plr);
    }

    private void removeBoard(GamePlayer p) {
        if(boards.containsKey(p)) boards.remove(p);
    }

    public static void setOverheadHP(GamePlayer pl, int hp) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            Score ha = getBoard(GameAPI.getGamePlayer(p)).getObjective(DisplaySlot.BELOW_NAME).getScore(pl.getPlayer());
            ha.setScore(hp);
        }
        main.getObjective(DisplaySlot.BELOW_NAME).getScore(pl.getPlayer());
    }
}
