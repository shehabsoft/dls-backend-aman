package ae.rta.dls.backend.domain.enumeration.trn;

/**
 * The Handbook Type enumeration.
 */
public enum HandbookType {

    HB_LMV("LIGHT_VEHICLES_AUTO_AND_MANUAL"),
    HB_MC("MOTORCYCLES"),
    HB_HMV_BUS("TRUCK_AND_BUS"),
    HB_LME("LIGHT_EQUIPMENT_MECHANIC"),
    HB_HME("HEAVY_EQUIPMENT_MECHANIC");

    /**
     * Define related domain code to be used for full domain object retrieval
     */
    public static final String DOMAIN_CODE = "HABO_HANDBOOK_TYPE";

    private String value;

    HandbookType(final String value) {
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
