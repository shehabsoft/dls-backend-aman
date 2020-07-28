package ae.rta.dls.backend.domain.enumeration.prd;

/**
 * The LearningFileStatus enumeration.
 */
public enum DrivingLicenseStatus {
    ACTIVE("ACTIVE"),
    EXPIRED("EXPIRED"),
    SUSPENDED_BY_POLICE("SUSPENDED_BY_POLICE"),
    SUSPENDED_BY_TRAFFIC_COURT("SUSPENDED_BY_TRAFFIC_COURT"),
    REVOKED_PASSED_AWAY("REVOKED_PASSED_AWAY"),
    REVOKED_REVERSE_TRN("REVOKED_REVERSE_TRN"),
    REVOKED_TRANSFERED_TO_ABU_DHABI("REVOKED_TRANSFERED_TO_ABU_DHABI"),
    REVOKED_TRANSFERED_TO_SHARJAH("REVOKED_TRANSFERED_TO_SHARJAH"),
    REVOKED_TRANSFERED_TO_AJMAN("REVOKED_TRANSFERED_TO_AJMAN"),
    REVOKED_TRANSFERED_TO_UMM_AL_QUWAIN("REVOKED_TRANSFERED_TO_UMM_AL_QUWAIN"),
    REVOKED_TRANSFERED_TO_RAS_AL_KHAIMAH("REVOKED_TRANSFERED_TO_RAS_AL_KHAIMAH"),
    REVOKED_TRANSFERED_TO_FUJAIRAH("REVOKED_TRANSFERED_TO_FUJAIRAH"),
    REVOKED_REPLACED_IN_OTHER_COUNTRY("REVOKED_REPLACED_IN_OTHER_COUNTRY");

    /**
     * Define related domain code to be used for full domain object retrieval (ex. active/inactive)
     */
    public static final String DOMAIN_CODE = "DRLI_STATUS";

    private String value;

    DrivingLicenseStatus(final String value) {
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
