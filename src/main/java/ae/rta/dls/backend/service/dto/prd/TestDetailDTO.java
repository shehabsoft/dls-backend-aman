package ae.rta.dls.backend.service.dto.prd;

import ae.rta.dls.backend.domain.enumeration.prd.TestResult;
import ae.rta.dls.backend.domain.enumeration.prd.TestStatus;
import ae.rta.dls.backend.service.dto.AbstractAuditingDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * Test Detail DTO
 *
 * @author Mohammad Qasim
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TestDetailDTO extends AbstractAuditingDTO {

    @JsonProperty("status")
    private TestStatus status;

    @JsonProperty("statusDate")
    private LocalDateTime statusDate;

    @JsonProperty("result")
    private TestResult result;

    @JsonProperty("resultDate")
    private LocalDateTime resultDate;

    @JsonProperty("currentTest")
    private Boolean currentTest;

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }

    public LocalDateTime getResultDate() {
        return resultDate;
    }

    public void setResultDate(LocalDateTime resultDate) {
        this.resultDate = resultDate;
    }

    public Boolean getCurrentTest() {
        return currentTest;
    }

    public void setCurrentTest(Boolean currentTest) {
        this.currentTest = currentTest;
    }

    @Override
    public String toString() {
        return "TestDetailDTO{" +
            "status=" + status +
            ", statusDate=" + statusDate +
            ", result=" + result +
            ", resultDate=" + resultDate +
            ", currentTest=" + currentTest +
            '}';
    }
}
