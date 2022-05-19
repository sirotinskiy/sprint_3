package utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.Random;

public class Utils {

    public static String getRandomString(int length){
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static int getRandomNumber(int start, int end){
        return start + (int) (Math.random() * end);
    }

    public static String getRandomDate() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2022, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2023, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return randomDate.toString();
    }
}
