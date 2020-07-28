package ae.rta.dls.backend.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "DLS_SYSTEM";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String SYSTEM_TRACE_ID = "systemTraceId";
    public static final String PREFIX_SYSTEM_TRACE_ID = "DLSBE/";

    public static final String DEFAULT_CRON_EXPRESSION = "0 0 1 * * *";
    public static final Long DEFAULT_FIXED_DELAY_VALUE = 86400000L;
    public static final Long DEFAULT_FIXED_RATE_VALUE = 86400000L;
    public static final Long DEFAULT_INITIAL_DELAY_VALUE = 0L;

    private Constants() {
    }
}
