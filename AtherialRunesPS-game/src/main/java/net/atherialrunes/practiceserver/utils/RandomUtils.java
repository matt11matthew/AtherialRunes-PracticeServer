package net.atherialrunes.practiceserver.utils;

import java.util.Random;

public class RandomUtils {

    public static int random(int low, int high) {
        Random random = new Random();
        if (low > high) {
            return high - 1;
        }
        int number = random.nextInt((high - low + 1)) + low;
        if (number < 1) {
            number = 1;
        }
        return number;
    }
}
