package ae.rta.dls.backend.domain.enumeration.prd;

/**
 * The LearningFileStatus enumeration.
 */
public enum LearningFileStatus {
    PENDING_FOR_EYE_TEST("PENDING_FOR_EYE_TEST"),
    PENDING_FOR_MEDICAL_ASSESSMENT("PENDING_FOR_MEDICAL_ASSESSMENT"),
    UNDER_PROCESSING("UNDER_PROCESSING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    DRAFT("DRAFT"),
    EXPIRED("EXPIRED"),
    TRANSFERRED("TRANSFERRED");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. active/inactive)
     */
    public static final String DOMAIN_CODE = "LEFI_STATUS";

    private String value;

    LearningFileStatus(final String value) {
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
