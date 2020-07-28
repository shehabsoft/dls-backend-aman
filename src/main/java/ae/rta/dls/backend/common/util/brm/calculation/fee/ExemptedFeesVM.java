package ae.rta.dls.backend.common.util.brm.calculation.fee;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Exempted Fees VM
 *
 * @author Tariq Abu Amireh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExemptedFeesVM implements Serializable {

    /*
     * Bean Fields.
     */

    /** Fee Codes List. */
    private List<Integer> list;

    /*
     * Class Constructors.
     */

    /**
     * Fee Codes Class Default Constructor.
     */
    public ExemptedFeesVM() {
        list = new ArrayList<>();
    }

    /*
     * Bean Fields Accessors.
     */

    /**
     * Get Fee Codes List.
     *
     * @return Fee Codes List.
     */
    public List<Integer> getList() {
        return list;
    }

    /**
     * Set Fee Codes List.
     *
     * @param list Fee Codes List.
     */
    public void setList(List<Integer> list) {
        this.list = list;
    }

}

