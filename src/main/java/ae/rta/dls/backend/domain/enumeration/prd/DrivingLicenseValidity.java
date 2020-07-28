package ae.rta.dls.backend.domain.enumeration.prd;

/**
 * The driving license validity enumeration.
 */
public enum DrivingLicenseValidity {
    VALID("VALID"),
    EXPIRED("EXPIRED");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. valid/expired)
     */
    public static final String DOMAIN_CODE = "DRLI_VALIDITY";

    private String value;

    DrivingLicenseValidity(final String value) {
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
