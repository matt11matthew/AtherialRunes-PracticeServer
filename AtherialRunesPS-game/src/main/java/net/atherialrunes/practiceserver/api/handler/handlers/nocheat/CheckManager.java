package net.atherialrunes.practiceserver.api.handler.handlers.nocheat;

import java.util.HashMap;

/**
 * Created by Matthew E on 9/26/2016 at 5:25 PM.
 */
public class CheckManager {

    public static HashMap<String, Check> checks = new HashMap<>();

    public static void loadChecks() {
        checks.values().forEach(check -> System.out.println("[" + check.getClass().getSimpleName() + "] has been loaded"));
    }

    public static void registerCheck(Check check) {
        checks.put(check.getClass().getSimpleName(), check);
    }
}
