package ae.rta.dls.backend.security.util;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String ANONYMOUS_ROLE = "ROLE_ANONYMOUS";

    public static final String VERIFIED_ROLE = "ROLE_VERIFIED";

    public static final String PUBLIC_LOGGED_IN_ROLE = "ROLE_PUBLIC_LOGGED_IN";

    public static final String BPM_ROLE = "ROLE_BPM";

    public static final String DRIVING_SCHOOL_USER_ROLE = "ROLE_DRIVING_SCHOOL_USER";

    public static final String DRIVING_SCHOOL_ADMIN_ROLE = "ROLE_DRIVING_SCHOOL_ADMIN";

    private AuthoritiesConstants() {
    }
}
