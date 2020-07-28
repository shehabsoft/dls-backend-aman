package ae.rta.dls.backend.domain.enumeration.trn;

public enum ApplicationTypeStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    UNDER_DEVELOPMENT("UNDER_DEVELOPMENT");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APTY_STATUS";

    private String value;

    ApplicationTypeStatus(final String value) {
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
