package utils;

import java.util.Random;

public class RandomNumbersUtils {
    public static int getUserNumber() {
        return new Random().ints(0, 1 + 1).findFirst().getAsInt();
    }
}
