package framework.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Working with Dates and date formats
 */
public class DateUtils {

    public static final String SHORT_YEAR_AM_PM_PATTERN = "MM/dd/yy hh:mm a";
    public static final String TIME_ZONE = "UTC-6";
    public static final String TIMESTAMP_PATTERN_FOR_FILE = "yyyyMMdd HHmmss.SSS";
    public static final String TIMESTAMP_PATTERN_UNIQUE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FULL_DAY_PATTERN = "MM/dd/yyyy";
    public static final String MESSAGE_UI_TIME_FORMAT = "M/d/yyyy h:mm a";
    public static final String DB_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String ALTERNATIVE_DB_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSSSSS";
    public static final String AUDIT_LOG_DATE_PATTERN = "MM/dd/yyyy hh:mm:ss a";
    public static final String SHORT_DAY_SHORT_MONTH_WITH_TIME_PATTERN = "M/d/yyyy h:mm:ss a";
    public static final String SHORT_DAY_WITH_TIME_PATTERN = "MM/d/yyyy h:mm:ss a";
    public static final String LONG_DAY_WITH_TIME_PATTERN = "MM/dd/yyyy hh:mm a";
    public static final String SHORT_DAY_PATTERN = "M/d/yyyy";
    public static final String LONG_DAY_PATTERN = "MM-dd-yyyy";
    public static final String HYPHEN_SHORT_DAY_PATTERN = "M-d-yyyy";
    public static final String REJECT_DATE_PATTERN = "MM/dd/yy";
    public static final String FULL_DAY_WITH_HOUR_PATTERN = "MM/d/yyyy hh a";
    public static final String MONTH_YEAR_PATTERN = "MMM yyyy";
    public static final String DAY_PATTERN = "d";
    public static Pattern FULL_DAY_REGEX_PATTERN = Pattern.compile(
        "^\\d{2}/\\d{2}/\\d{4}$");
    public static Pattern SHORT_DAY_REGEX_PATTERN = Pattern.compile(
        "^\\d{1,2}/\\d{1,2}/\\d{4}$");

    /**
     * @param pattern desired date format pattern
     * @return returns local date time from the system clock in the default time-zone in the expected format.
     */
    public static String getCurrentDateTime(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        return formatter.format(now);
    }

    /**
     * @return returns local date time from the system clock in the default time-zone in the yyyy/MM/dd HH:mm:ss format
     */
    public static String getCurrentDateTime() {
        return getCurrentDateTime(TIMESTAMP_PATTERN_FOR_FILE);
    }

    public static String getRandomDate(String pattern) {
        Random random = new Random();
        LocalDateTime ldt = LocalDateTime.now()
            .minusDays(random.nextInt(10))
            .minusHours(random.nextInt(24))
            .minusMinutes(random.nextInt(60))
            .minusSeconds(random.nextInt(60));
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getYesterdayDate(String pattern) {
        LocalDateTime ldt = LocalDateTime.now()
            .minusDays(1);
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getPreYesturdayDate(String pattern) {
        LocalDateTime ldt = LocalDateTime.now()
            .minusDays(2);
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static synchronized String getCurrentTime(String dateTimeStamp) {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeStamp);
        return localDateTime.format(formatter);
    }

    public static synchronized String getUniqueCurrentTime(String dateTimeStamp) throws InterruptedException {
        String result = getCurrentTime(dateTimeStamp);
        return result;
    }

    public static synchronized String getCurrentTimeWithDayShift(String dateTimeStamp, long dayShift) {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        localDateTime = localDateTime.plusDays(dayShift);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeStamp);
        return localDateTime.format(formatter);
    }


    public static String getCurrentDate() {

        return getCurrentDate(FULL_DAY_PATTERN);
    }

    public static String getDayBeforeYesterday() {
        return getDateFromPast(2);
    }

    /**
     * @param days number of days to go in the past
     * @return date that is in the past (minus days from today)
     */
    public static String getDateFromPast(long days) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(FULL_DAY_PATTERN);
        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.minusDays(days);
        return dtf.format(localDateTime);

    }

    /**
     * @param date date without time to change the format of
     * @param actualPattern actual date pattern
     * @param expectedPattern expected date pattern
     * @return date converted from one format to another as String
     */
    public static String changeDateFormat(String date, String actualPattern, String expectedPattern) {
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern(expectedPattern);
        DateTimeFormatter actualFormatter = DateTimeFormatter.ofPattern(actualPattern);
        LocalDate dateTime = LocalDate.parse(date, actualFormatter);
        return expectedFormatter.format(dateTime);
    }

    /**
     * @param dateFormat expected date format
     * @return current date
     */
    public static String getCurrentDate(String dateFormat) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * @param date date with time to change the format of
     * @param actualPattern actual date pattern
     * @param expectedPattern expected date pattern
     * @return date-time converted from one format to another as String
     */
    public static String changeDateTimeFormat(String date, String actualPattern, String expectedPattern) {
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern(expectedPattern);
        DateTimeFormatter actualFormatter = DateTimeFormatter.ofPattern(actualPattern);
        LocalDateTime dateTime = LocalDateTime.parse(date, actualFormatter);
        return expectedFormatter.format(dateTime);
    }

    public static LocalDateTime getDateTimeFromString(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, formatter);
    }


    public static String getStringFromDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getStringFromDate(LocalDateTime date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate getDateFromString(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }

    public static boolean isDateMatchPattern(String date, Pattern pattern) {
        return pattern.matcher(date).matches();
    }

    public static String getTomorrowDate(String pattern) {
        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        return ldt.format(DateTimeFormatter.ofPattern(pattern));
    }
}