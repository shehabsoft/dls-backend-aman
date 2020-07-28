package ae.rta.dls.backend.common.util.brm.calculation.fee;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.*;

/**
 * Fees List VM
 *
 * @author Tariq Abu Amireh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FeesListVM implements Serializable {

    /*
     * Bean Fields.
     */

    @JsonProperty("list")
    private Set<FeeVM> list;

    /*
     * Class Constructors.
     */

    /**
     * Fees Class Default Constructor.
     */
    public FeesListVM() {
        this.list = new HashSet<>();
    }

    /*
     * Bean Fields Accessors.
     */

    /**
     * Get Fees List.
     *
     * @return Fees List.
     */
    public Set<FeeVM> getList() {
        return this.list;
    }

    /**
     * Set Fees List.
     *
     * @param list Fees List.
     */
    public void setList(Set<FeeVM> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "FeesListVM{" +
            "list=" + list +
            '}';
    }
}

