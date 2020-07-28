package ae.rta.dls.backend.web.rest.errors;


public enum ValidationType {
    ERROR("ERROR"),
    WARNING("WARNING");

    private String value;

    ValidationType(final String value) {
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
