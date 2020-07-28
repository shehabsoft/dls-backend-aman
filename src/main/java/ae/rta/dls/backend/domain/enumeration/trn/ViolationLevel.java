package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * The ViolationLevel enumeration.
 */
public enum ViolationLevel {
    BLOCKER("BLOCKER"),
    WARNING("WARNING"),
    INFO("INFO");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APVI_VIOLATION_LEVEL";

    private String value;

    ViolationLevel(final String value) {
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
