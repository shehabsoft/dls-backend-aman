package ae.rta.dls.backend.domain.enumeration.prd;

/**
 * The Test type enumeration.
 */
public enum TestType {
    THEORY("THEORY"),
    ROAD("ROAD"),
    YARD("YARD");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "LEFI_TEST_TYPE";

    private String value;

    TestType(final String value) {
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
