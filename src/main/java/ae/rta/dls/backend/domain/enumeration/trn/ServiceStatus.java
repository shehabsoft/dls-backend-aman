package ae.rta.dls.backend.domain.enumeration.trn;

public enum ServiceStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "SERV_STATUS";

    private String value;

    ServiceStatus(final String value) {
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
