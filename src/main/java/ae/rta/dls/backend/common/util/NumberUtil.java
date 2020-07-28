package ae.rta.dls.backend.common.util;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Configuration provides a convenience methods for Number parsing,formatting and manipulation
 * by using Apache NumberUtils API and adding some extra functionality
 *
 * @see org.apache.commons.lang3.math.NumberUtils
 */
public abstract class NumberUtil extends NumberUtils {
    /**
     * Get Long object or null if the value is null or empty string.
     *
     * @param value String value to be parsed.
     * @return Long object or null if the value is null or empty string.
     */
    public static Long toLong(Object value) {
        if (value == null) {
            return null;
        }

        String str = StringUtil.getString(value);
        if (StringUtil.isBlank(str)) {
            return null;
        }

        return toLong(str.trim());
    }

    /**
     * <p>Convert a <code>String</code> to a <code>long</code>, returning
     * <code>zero</code> if the conversion fails.</p>
     *
     * @param str  the string to convert, may be null
     * @return the long represented by the string, or <code>0</code> if conversion fails
     */
    public static long toLong(final String str) {
        return NumberUtils.toLong(str);
    }

    public static Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }

        String str = StringUtil.getString(value);
        if (StringUtil.isBlank(str)) {
            return null;
        }

        return toInt(str.trim());
    }

    /**
     * <p>Checks whether the <code>String</code> contains only
     * digit characters.</p>
     *
     * <p><code>Null</code> and empty String will return
     * <code>false</code>.</p>
     *
     * @param str  the <code>String</code> to check
     * @return <code>true</code> if str contains only Unicode numeric
     */
    public static boolean isDigits(final String str) {
        return NumberUtils.isDigits(str);
    }

    public static Double createDouble(final String str) {
        return NumberUtils.createDouble(str);
    }
}
