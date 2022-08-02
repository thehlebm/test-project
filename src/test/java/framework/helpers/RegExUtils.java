package framework.helpers;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static framework.constants.BaseConstants.EMPTY_STRING;

public class RegExUtils {

    private final static int ATOMIC_TIME_FOR_TIMESTAMP = 1;

    /**
     * Get value via regex
     *
     * @param regex regex
     * @param text  value
     * @return Pair ( 'regex value' , 'key value' )
     */
    public static Pair<String, String> getValueViaRegexGroup(String regex, String text) {
        List<Pair<String, String>> result = getValuesViaRegexGroup(regex, text);
        if (result.isEmpty())
            return null;
        return result.get(0);
    }

    /**
     * Get values via regex
     *
     * @param regex regex
     * @param text  value
     * @return list of Pair ( 'regex value' , 'key value' )
     */
    public static List<Pair<String, String>> getValuesViaRegexGroup(String regex, String text) {
        List<Pair<String, String>> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            Pair<String, String> pair = new Pair<>(matcher.group(0), matcher.group(1));
            result.add(pair);
        }
        return result;
    }

    public static List<String> getValuesViaRegexGroup(String regex, String group, String text) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            result.add(matcher.group(group));
        }
        return result;
    }

    public static String getSingleValue(String regex, String text) {
        String resultString = EMPTY_STRING;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            resultString = matcher.group(1);
        }
        return resultString;
    }
}