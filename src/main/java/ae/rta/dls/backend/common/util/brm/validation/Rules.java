package ae.rta.dls.backend.common.util.brm.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Rules implements Serializable {

    /*
     * Instance Variables
     */
    private List<Rule> list;

    /*
     * Constructor
     */
    public Rules() {
        list = new ArrayList<>();
    }

    /*
     * Setters & Getters
     */
    public List<Rule> getList() {
        return list;
    }

    public void setList(List<Rule> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Rules{" +
            "list=" + list +
            '}';
    }
}
