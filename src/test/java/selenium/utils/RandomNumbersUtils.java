package selenium.utils;

import java.util.Random;

public class RandomNumbersUtils {

    public static int getRandomNumber(int minValue, int maxValue) {
        return new Random().ints(minValue, maxValue + 1).findFirst().getAsInt();
    }

}
