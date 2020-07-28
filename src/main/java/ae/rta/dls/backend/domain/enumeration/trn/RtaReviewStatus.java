package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * Rta Review Status enumeration.
 */
public enum RtaReviewStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    NOT_REQUIRED("NOT_REQUIRED");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "APPL_RTA_REVIEW_STATUS";

    private String value;

    RtaReviewStatus(final String value) {
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
