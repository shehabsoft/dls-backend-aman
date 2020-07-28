package ae.rta.dls.backend.domain.enumeration.trn;

public enum ServiceCode {
    OPEN_DRIVING_LEARNING_FILE("001"),
    ISSUE_DRIVING_TEST_APPOINTMENT("002"),
    UPDATE_DRIVING_TEST_APPOINTMENT("003"),
    CANCEL_DRIVING_TEST_APPOINTMENT("004"),
    DEFINE_FOREIGN_DRIVING_LICENSE("005"),
    BUY_HAND_BOOK("007");

    private String value;

    ServiceCode(final String value) {
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
