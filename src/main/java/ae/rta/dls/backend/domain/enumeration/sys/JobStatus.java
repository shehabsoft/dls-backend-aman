package ae.rta.dls.backend.domain.enumeration.sys;

public enum JobStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. active/inactive)
     */
    public static final String DOMAIN_CODE = "AUJC_STATUS";
    
    private String value;

    JobStatus(final String value) {
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
