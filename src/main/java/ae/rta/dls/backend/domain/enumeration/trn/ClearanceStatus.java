package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * Clearance Status enumeration.
 */
public enum ClearanceStatus {
    PENDING("PENDING"),
    ADDED("ADDED");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_CLEARANCE_STATUS";

    private String value;

    ClearanceStatus(final String value) {
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
