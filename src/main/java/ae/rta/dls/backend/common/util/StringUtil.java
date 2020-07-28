package ae.rta.dls.backend.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Configuration provides a convenience methods for String formatting and manipulation
 * by using Common lang StringUtils API and adding some extra functionality
 *
 * @see org.apache.commons.lang3.StringUtils
 */
public abstract class StringUtil extends StringUtils {

    /**
     * Get string representation of this object
     *
     * @param obj Object to be parsed,
     * @return string representation of this object
     */
    public static String getString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     *
     * @param cs Char Sequence
     * @return true if CharSequence is empty (""), null or whitespace only.
     */
    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * @param cs Char Sequence
     * @return true if a CharSequence is not empty (""), not null and not whitespace only.
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * @param cs Char Sequence
     * @return if a CharSequence is empty ("") or null.
     */
    public static boolean isEmpty(final CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    /**
     * Format String to Capitalize the first letter and lower rest of the word.
     * @param value  String to be formatted.
     * @return Formatted String.
     */
    public static String formatString(String value) {
        if (value == null) {
            return null;
        }
        if (StringUtil.isBlank(value)) {
            return "";
        }
        value = value.toLowerCase().trim();
        StringBuilder result = new StringBuilder(value.length());
        String[] words = value.split("\\ ");
        for (String word : words) {
            if (isBlank(word)) {
                continue;
            }
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return result.toString();
    }

    /**
     * Converts a String to upper case
     * @param str
     * @return the String to upper case
     */
    public static String upperCase(final String str) {
        return StringUtils.upperCase(str);
    }

}
