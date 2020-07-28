package ae.rta.dls.backend.domain.enumeration.um;

/**
 * The UserType enumeration.
 */
public enum UserType {
    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    SYSTEM("SYSTEM"),
    IAM("IAM"),
    ANONYMOUS("ANONYMOUS"),
    EMPLOYEE("EMPLOYEE"),
    CORPORATE("CORPORATE"),
    VERIFIED("VERIFIED"),
    BPM("BPM");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "USPR_USER_TYPE";

    private String value;

    UserType(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value();
    }
}
