package ae.rta.dls.backend.service.dto.prd;

import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Product Details DTO
 *
 * @author Mohammad Abulawi
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDetailsDTO extends AbstractAuditingDTO {

    @JsonProperty("hasLearningFile")
    private Boolean hasLearningFile;

    @JsonProperty("hasDubaiLicense")
    private Boolean hasDubaiLicense;

    @JsonProperty("hasHandbook")
    private Boolean hasHandbook;

    public Boolean getHasLearningFile() {
        return hasLearningFile;
    }

    public void setHasLearningFile(Boolean hasLearningFile) {
        this.hasLearningFile = hasLearningFile;
    }

    public Boolean getHasDubaiLicense() {
        return hasDubaiLicense;
    }

    public void setHasDubaiLicense(Boolean hasDubaiLicense) {
        this.hasDubaiLicense = hasDubaiLicense;
    }

    public Boolean getHasHandbook() {
        return hasHandbook;
    }

    public void setHasHandbook(Boolean hasHandbook) {
        this.hasHandbook = hasHandbook;
    }


    @Override
    public String toString() {
        return "ProductDetailsDTO{" +
            "hasLearningFile=" + hasLearningFile +
            ", hasDubaiLicense=" + hasDubaiLicense +
            ", hasHandbook=" + hasHandbook +
            '}';
    }
}
