package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * Driving School Review Status enumeration.
 */
public enum DrivingSchoolReviewStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    MISSING_FOREIGN_LICENSE("MISSING_FOREIGN_LICENSE");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_DRIVING_SCHOOL_REVIEW_STATUS";

    private String value;

    DrivingSchoolReviewStatus(final String value) {
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
