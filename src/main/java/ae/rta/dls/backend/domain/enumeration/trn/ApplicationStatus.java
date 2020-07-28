package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * The ApplicationStatus enumeration.
 */
public enum ApplicationStatus {
    DRAFT("DRAFT"),
    UNDER_PROCESSING("UNDER_PROCESSING"),
    COMPLETED("COMPLETED"),
    REJECTED("REJECTED");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_STATUS";

    private String value;

    ApplicationStatus(final String value) {
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
