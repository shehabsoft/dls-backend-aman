package ae.rta.dls.backend.domain.enumeration.prd;

/**
 * The GearType enumeration.
 */
public enum GearType {
    AUTO("AUTO"),
    MANUAL("MANUAL");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. active/inactive)
     */
    public static final String DOMAIN_CODE = "LEFI_GEAR_TYPE";

    private String value;

    GearType(final String value) {
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
