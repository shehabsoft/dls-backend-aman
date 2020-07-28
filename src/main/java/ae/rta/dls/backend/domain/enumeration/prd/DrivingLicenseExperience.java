package ae.rta.dls.backend.domain.enumeration.prd;

/**
 * The driving license Experience enumeration.
 */
public enum DrivingLicenseExperience {
    LESS_THAN_TWO("LESS_THAN_TWO"),
    BETWEEN_TWO_AND_FIVE("BETWEEN_TWO_AND_FIVE"),
    MORE_THAN_FIVE("MORE_THAN_FIVE");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. MORE_THAN_TWO/LESS_THAN_TWO)
     */
    public static final String DOMAIN_CODE = "APPL_EXPERIENCE";

    private String value;

    DrivingLicenseExperience(final String value) {
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
