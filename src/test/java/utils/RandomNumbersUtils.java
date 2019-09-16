package utils;

import java.util.Random;

public class RandomNumbersUtils {

    public static int getRandomNumber(int minValue, int maxValue) {
        int randNumber = new Random().ints(minValue, maxValue + 1).findFirst().getAsInt();
        System.out.println(randNumber);
        return randNumber;
    }

}
