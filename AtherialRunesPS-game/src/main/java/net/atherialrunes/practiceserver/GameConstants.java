package net.atherialrunes.practiceserver;

import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Matthew E on 9/24/2016 at 11:55 AM.
 */
public class GameConstants {

    public static final int CHAOTIC_TIME = 600;
    public static final int CHAOTIC_TIME_LOST = 300;

//    public static final int T1_DROP_RATES = 50;
//    public static final int T2_DROP_RATES = 35;
//    public static final int T3_DROP_RATES = 15;
//    public static final int T4_DROP_RATES = 9;
//    public static final int T5_DROP_RATES = 5;

    public static final double T1_DROP_RATES = 50;
    public static final double T2_DROP_RATES = 20;
    public static final double T3_DROP_RATES = 12;
    public static final double T4_DROP_RATES = 7;
    public static final double T5_DROP_RATES = 3;
    public static final double T6_DROP_RATES = 0.5;

    public static final String T1_GEM_DROP = "2-5";
    public static final String T2_GEM_DROP = "15-20";
    public static final String T3_GEM_DROP = "25-35";
    public static final String T4_GEM_DROP = "40-74";
    public static final String T5_GEM_DROP = "60-120";
    public static final String T6_GEM_DROP = "200-300";

    public static final int MOB_CPS = 5;
    public static final int PLAYER_CPS = 8;

    public static final double ELITE_DROP_MULTIPLIER = 2;

    public static final int MAX_ENTITIES_IN_CHUNK = 5;

    public static World WORLD = Bukkit.getWorld("ARPS");

    public static final int MAX_PLAYERS = 500;
    public static final String MOTD = Utils.colorCodes(YamlConfiguration.loadConfiguration(new File(PracticeServer.getInstance().getDataFolder() + "", "motd.yml")).getString("motd")).replaceAll("%nl%", "\n");
}
