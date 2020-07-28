package ae.rta.dls.backend.domain.enumeration.common;

public enum Language {
    ar("ar"),
    en("en");

    /**
     * Define related domain code to be used for full object retrieval (ex. ar/en)
     */
    public static final String DOMAIN_CODE = "GEN_LANGUAGE";

    private String value;

    Language(final String value) {
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
