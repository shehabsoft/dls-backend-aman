package ae.rta.dls.backend.domain.enumeration.trn;

public enum ServiceGroupStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "SEGR_STATUS";

    private String value;

    ServiceGroupStatus(final String value) {
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
