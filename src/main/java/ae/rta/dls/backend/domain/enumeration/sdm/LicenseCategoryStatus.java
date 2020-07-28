package ae.rta.dls.backend.domain.enumeration.sdm;

public enum LicenseCategoryStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. active/inactive)
     */
    public static final String DOMAIN_CODE = "LICA_STATUS";

    private String value;

    LicenseCategoryStatus(final String value) {
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
