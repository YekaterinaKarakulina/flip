package utils;

import java.util.Random;

public class RandomNumbersUtils {

    public static int getUserNumber(int minValue, int maxValue) {
        return new Random().ints(minValue, maxValue + 1).findFirst().getAsInt();
    }

}
