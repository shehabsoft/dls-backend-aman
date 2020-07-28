package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * The PhaseType enumeration.
 */
public enum PhaseType {
    CUSTOMER_ELIGIBILITY("CUSTOMER_ELIGIBILITY"),
    DRIVING_LEARNING_FILE_PROCESSING("DRIVING_LEARNING_FILE_PROCESSING"),
    DRIVING_LEARNING_FILE_RTA_AUDIT("DRIVING_LEARNING_FILE_RTA_AUDIT"),
    DRIVING_LEARNING_FILE_DS_AUDIT("DRIVING_LEARNING_FILE_DS_AUDIT"),
    WAITING_FOR_PAYMENT_CLEARANCE("WAITING_FOR_PAYMENT_CLEARANCE"),
    READY_FOR_CONTRACT_SIGN("READY_FOR_CONTRACT_SIGN"),
    EYE_TEST("EYE_TEST"),
    THEORY_LECTURE("THEORY_LECTURE"),
    THEORY_TEST("THEORY_TEST"),
    DRIVING_LESSONS("DRIVING_LESSONS"),
    YARD_TEST("YARD_TEST"),
    ADVANCED_PRACTICAL_TRAINING("ADVANCED_PRACTICAL_TRAINING"),
    ROAD_TEST("ROAD_TEST"),
    PRINT_LICENSE("PRINT_LICENSE"),
    APPLICATION_REJECTION("APPLICATION_REJECTION"),
    EXEMPTION_AUDITING("EXEMPTION_AUDITING"),
    MISSING_DOCUMENT("MISSING_DOCUMENT");


    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "SERE_PHASE_TYPE";

    private String value;

    PhaseType(final String value) {
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
