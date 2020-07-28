package ae.rta.dls.backend.domain.enumeration.um;

/**
 * The Gender enumeration.
 */
public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "USPR_GENDER";

    private String value;

    Gender(final String value) {
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
