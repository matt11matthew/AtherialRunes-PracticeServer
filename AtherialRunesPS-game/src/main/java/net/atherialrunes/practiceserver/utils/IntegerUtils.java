package net.atherialrunes.practiceserver.utils;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class IntegerUtils {

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
