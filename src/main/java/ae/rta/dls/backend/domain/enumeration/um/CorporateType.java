package ae.rta.dls.backend.domain.enumeration.um;

/**
 * The GroupType enumeration.
 */
public enum CorporateType {
    MAIN("MAIN"),
    BRANCH("BRANCH");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "CORP_CORPORATE_TYPE";

    private String value;

    CorporateType(final String value) {
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
