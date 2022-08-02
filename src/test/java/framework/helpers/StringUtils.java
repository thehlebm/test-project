package framework.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static framework.constants.BaseConstants.EMPTY_STRING;
import static framework.constants.BaseConstants.SPACE;


public class StringUtils {

    private static final String TRAILING_ZEROS_PATTERN = "0*$";
    private static final String TRAILING_CHARS_PATTERN = "[%s]*$";
    public static final String STARTING_CHARACTER_PATTERN = "^[%s]*";

    /**
     * @param fullString any string that has enclosing characters to be removed like [],"",{}
     * @return returns String without this characters
     */
    public static String extractFromEnclosingCharacters(String fullString) {
        return fullString.substring(1, fullString.length() - 1);
    }

    /**
     * @param stringToTrim any string with trailing zeroes that need to be remove
     * @return string without trailing zeroes
     */
    public static String trimZeros(String stringToTrim) {
        return stringToTrim.replaceAll(TRAILING_ZEROS_PATTERN, EMPTY_STRING);
    }

    /**
     * @param stringToTrim any string that has enclosing characters to be removed
     * @param character    to be removed
     * @return the string without specified character
     */
    public static String trimChars(String stringToTrim, char character) {
        return stringToTrim.replaceAll(String.format(TRAILING_CHARS_PATTERN, character), EMPTY_STRING);
    }

    /**
     * @param actual   string contains actual data
     * @param expected string contains expected data
     * @param mismatch - a StringBuilder to accumulate errors
     * @return
     */
    public static boolean areStringsIdentical(String actual, String expected, StringBuilder mismatch) {
        boolean result = actual.contains(expected);
        if (!result) mismatch.append(actual).append(" - actual is not the expected: ").append(expected).append("\n");
        return result;
    }

    /**
     * @param actual   string actual data
     * @param expected string expected data
     * @param mismatch - a StringBuilder to accumulate errors
     * @return
     */
    public static boolean areStringsEqualIgnoreCase(String actual, String expected, StringBuilder mismatch) {
        boolean result = actual.equalsIgnoreCase(expected);
        if (!result) mismatch.append(actual).append(" - actual is not the expected: ").append(expected).append("\n");
        return result;
    }

    /**
     * @param initialString string to delete the starting character from
     * @param character     character to delete
     * @return
     */
    public static String trimStartingCharacter(String initialString, char character) {

        return initialString.replaceAll(String.format(STARTING_CHARACTER_PATTERN, character), EMPTY_STRING);

    }

    /**
     * @param initialString string to delete the last character from
     * @return trimmed string
     */
    public static String removeLastChar(String initialString) {
        return removeLastChars(initialString, 1);
    }

    /**
     * @param initialString string to delete the last character from
     * @param chars         number of characters to delete
     * @return trimmed string
     */
    public static String removeLastChars(String initialString, int chars) {
        return initialString.substring(0, initialString.length() - chars);
    }

    /**
     * Method that capitalizes first letter in each word of provided string
     *
     * @param value
     * @return string where each word starts by capitalized letter
     */
    public static String capitalizeEachWord(final String value) {
        if (value.isEmpty()) {
            return value;
        }
        String[] split = value.split(SPACE);
        List<String> collect = Arrays.stream(split)
                .map(element -> org.apache.commons.lang3.StringUtils.capitalize(element.toLowerCase()))
                .collect(Collectors.toList());
        return String.join(" ", collect);
    }
}