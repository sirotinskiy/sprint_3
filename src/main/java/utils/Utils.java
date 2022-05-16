package utils;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static String getRandomString(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                case 0:
                    result=Math.round(Math.random()*25+65);
                    sb.append(((char)result));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+97);
                    sb.append(((char)result));
                    break;
                case 2:
                    sb.append((new Random().nextInt(10)));
                    break;
            }


        }
        return sb.toString();
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
