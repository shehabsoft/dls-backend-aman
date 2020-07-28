package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * The Theory Test enumeration.
 */
public enum NocType {
    REQUIRED("REQUIRED"),
    NOT_REQUIRED("NOT_REQUIRED"),
    FULFILLED_ELECTRONIC("FULFILLED_ELECTRONIC"),
    FULFILLED_MANUAL("FULFILLED_MANUAL"),
    FULFILLED_MANUAL_REVIEWED("FULFILLED_MANUAL_REVIEWED");


    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_NOC_TYPE";

    private String value;

    NocType(final String value) {
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
