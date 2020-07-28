package ae.rta.dls.backend.domain.enumeration.prd;

/**
 * The driving license Source enumeration.
 */
public enum DrivingLicenseSource {
    LEARNING_FILE("LEARNING_FILE"),
    PREVIOUS_LICENSE("PREVIOUS_LICENSE");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. PREVIOUS_LICENSE/PREVIOUS_LICENSE)
     */
    public static final String DOMAIN_CODE = "DRLI_SOURCE";

    private String value;

    DrivingLicenseSource(final String value) {
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
