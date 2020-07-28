package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * The ServiceRequestStatus enumeration.
 */
public enum ServiceRequestStatus {
    UNDER_PROCESSING("UNDER_PROCESSING"),
    VERIFIED_AND_LOCKED("VERIFIED_AND_LOCKED"),
    CONFIRMED("CONFIRMED"),
    REJECTED("REJECTED");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "SERE_STATUS";

    private String value;

    ServiceRequestStatus(final String value) {
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
