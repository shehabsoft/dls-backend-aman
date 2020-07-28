package ae.rta.dls.backend.common.util;


import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Configuration provides a convenience methods for Dates parsing,formatting and manipulation
 * by using Apache DateUtils API and adding some extra functionality
 *
 * @see  org.apache.commons.lang3.time.DateUtils
 */
public abstract class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    /** Date Time Formatter */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm:ss.S")
            .withZone(ZoneId.systemDefault());

    /** Time Formatter */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
            .ofPattern("HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    /** Date Formatter */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .withZone(ZoneId.systemDefault());

    /**
     * Format Instant value and return string representation of date
     *
     * @param instant date to be formatted
     * @return String representation of date
     */
    public static String formatDate(java.time.Instant instant) {
        return DATE_FORMATTER.format(instant);
    }

    /**
     * Format Instant value and return string representation of date and time
     *
     * @param instant date and time to be formatted
     * @return String representation of date and time
     */
    public static String formatDateTime(java.time.Instant instant) {
        return DATE_TIME_FORMATTER.format(instant);
    }

    /**
     * Format Instant value and return string representation of time
     *
     * @param instant date and time to be formatted
     * @return String representation of time
     */
    public static String formatTime(java.time.Instant instant) {
        return TIME_FORMATTER.format(instant);
    }

    /**
     * Get Local Date Time from milliseconds
     *
     * @param milliseconds date and time to be formatted
     * @return  Local Date Time
     */
    public static LocalDateTime getLocalDateTime(Long milliseconds) {

        if(milliseconds == null) {
            return null;
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }

    /**
     * Get milliseconds Local Date Time
     *
     * @param localDateTime
     * @return String representation of time
     */
    public static Long getMilliSeconds(LocalDateTime localDateTime) {

        if(localDateTime == null) {
            return null;
        }

        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * Get milliseconds Local Date Time
     *
     * @param localDateTime
     * @return String representation of time
     */
    public static Long getSeconds(LocalDateTime localDateTime) {

        if(localDateTime == null) {
            return null;
        }

        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }
}
