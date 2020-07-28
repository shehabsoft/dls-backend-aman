package ae.rta.dls.backend.domain.enumeration.sys;

public enum ConfigurationKey {
    ENABLE_REST_LOGGING("sys.restlog.enable"),
    ENABLE_BRM_REST_LOGGING("brm.restlog.enable"),
    REMOVE_DRAFT_APPLICATION("job.trn.applicationremovaljob"),
    MAXIMUM_LICENSE_EXPIRATION_PERIOD("service.trn.maximumlicenseexpirationperiod");

    private String value;

    ConfigurationKey(final String value) {
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
