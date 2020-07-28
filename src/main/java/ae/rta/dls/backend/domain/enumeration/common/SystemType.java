package ae.rta.dls.backend.domain.enumeration.common;

/**
 * The SystemType enumeration.
 */
public enum SystemType {
    ALL("ALL"),
    DLS("DLS"),
    VLS("VLS"),
    CTLS("CTLS");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "GEN_SYSTEM_TYPE";

    private String value;

    SystemType(final String value) {
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
