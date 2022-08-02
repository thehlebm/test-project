
package framework.helpers;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomUtils extends org.apache.commons.lang3.RandomUtils {

    public static String getRandomPhoneNumber() {
        return RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(4);
    }

    /**
     * @return a random string that doesn't have duplicate characters
     */
    public static String getRandomString() {
        int min = 5;
        int max = 10;
        return RandomStringUtils.randomAlphabetic(min, max);
    }

    public static String getRandomString(int minLength, int maxLength) {
        return RandomStringUtils.randomAlphabetic(minLength, maxLength);
    }

    public static String getRandomString(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

    public static Integer getRandomNumber(int min, int max) {
        return nextInt(min, max);
    }

    /**
     * Generate random digits of specified length
     */
    public static int generateRandomDigits(int count) {
        int minValue = (int) Math.pow(10, count - 1);
        return minValue + new Random().nextInt(9 * minValue);
    }
}