package net.atherialrunes.practiceserver.api.handler.handlers.player;

import lombok.Data;

/**
 * Created by Matthew E on 9/24/2016 at 2:06 PM.
 */

@Data
public class PlayerStatistics {

    private int t1MobKills, t2MobKills, t3MobKills, t4MobKills, t5MobKills;
    private int lawfulPlayerKills, neutralPlayerKills, chaoticPlayerKills;
    private int orbsFailed, orbsWorked;
    private int gemsSpent, gemsGained;
    private final GamePlayer gamePlayer;

    public PlayerStatistics(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        setT1MobKills((Integer) gamePlayer.getStatistic("statistics.t1mobkills"));
        setT2MobKills((Integer) gamePlayer.getStatistic("statistics.t2mobkills"));
        setT3MobKills((Integer) gamePlayer.getStatistic("statistics.t3mobkills"));
        setT4MobKills((Integer) gamePlayer.getStatistic("statistics.t4mobkills"));
        setT5MobKills((Integer) gamePlayer.getStatistic("statistics.t5mobkills"));
        setLawfulPlayerKills((Integer) gamePlayer.getStatistic("statistics.lawfulkills"));
        setNeutralPlayerKills((Integer) gamePlayer.getStatistic("statistics.neutralkills"));
        setChaoticPlayerKills((Integer) gamePlayer.getStatistic("statistics.chaotickills"));
        setOrbsFailed((Integer) gamePlayer.getStatistic("statistics.orbsfailed"));
        setOrbsWorked((Integer) gamePlayer.getStatistic("statistics.orbsworked"));
        setGemsGained((Integer) gamePlayer.getStatistic("statistics.gemsgained"));
        setGemsSpent((Integer) gamePlayer.getStatistic("statistics.gemsspent"));
    }

    public void upload() {
        gamePlayer.setStatistic("t1mobkills", getT1MobKills());
        gamePlayer.setStatistic("t2mobkills", getT2MobKills());
        gamePlayer.setStatistic("t3mobkills", getT3MobKills());
        gamePlayer.setStatistic("t4mobkills", getT4MobKills());
        gamePlayer.setStatistic("t5mobkills", getT5MobKills());
        gamePlayer.setStatistic("lawfulkills", getLawfulPlayerKills());
        gamePlayer.setStatistic("neutralkills", getNeutralPlayerKills());
        gamePlayer.setStatistic("chaotickills", getChaoticPlayerKills());
        gamePlayer.setStatistic("orbsfailed", getOrbsFailed());
        gamePlayer.setStatistic("orbsworked", getOrbsWorked());
        gamePlayer.setStatistic("gemsgained", getGemsGained());
        gamePlayer.setStatistic("gemsspent", getGemsSpent());
    }

    public int getTotalMobKills() {
        int kills = 0;
        kills += t1MobKills;
        kills += t2MobKills;
        kills += t3MobKills;
        kills += t4MobKills;
        kills += t5MobKills;
        return kills;
    }
}
