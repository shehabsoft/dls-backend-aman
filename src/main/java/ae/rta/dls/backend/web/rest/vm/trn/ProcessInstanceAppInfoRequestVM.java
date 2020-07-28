package ae.rta.dls.backend.web.rest.vm.trn;

import ae.rta.dls.backend.domain.enumeration.trn.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;

/**
 * A VM of Process Instance Application Search Request API. ProcessInstanceApplicationByInfo
 */
@ApiModel(description = "Process Instance Application Info Request VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProcessInstanceAppInfoRequestVM {

    private Integer pageSize;

    private Integer offset;

    private Long processInstanceId;

    private String emiratesId;

    private String mobileNo;

    private ApplicationStatus status;

    private String licenseCategoryCode;

    private LocalDate applicationDateFrom;

    private LocalDate applicationDateTo;

    private Long applicationReferenceNo;

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getEmiratesId() {
        return emiratesId;
    }

    public void setEmiratesId(String emiratesId) {
        this.emiratesId = emiratesId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getLicenseCategoryCode() {
        return licenseCategoryCode;
    }

    public void setLicenseCategoryCode(String licenseCategoryCode) {
        this.licenseCategoryCode = licenseCategoryCode;
    }

    public LocalDate getApplicationDateFrom() {
        return applicationDateFrom;
    }

    public void setApplicationDateFrom(LocalDate applicationDateFrom) {
        this.applicationDateFrom = applicationDateFrom;
    }

    public LocalDate getApplicationDateTo() {
        return applicationDateTo;
    }

    public void setApplicationDateTo(LocalDate applicationDateTo) {
        this.applicationDateTo = applicationDateTo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Long getApplicationReferenceNo() {
        return applicationReferenceNo;
    }

    public void setApplicationReferenceNo(Long applicationReferenceNo) {
        this.applicationReferenceNo = applicationReferenceNo;
    }

    @Override
    public String toString() {
        return "ProcessInstanceAppInfoRequestVM{" +
                "pageSize=" + pageSize +
                ", offset=" + offset +
                ", processInstanceId=" + processInstanceId +
                ", emiratesId='" + emiratesId + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", status=" + status +
                ", licenseCategoryCode='" + licenseCategoryCode + '\'' +
                ", applicationDateFrom=" + applicationDateFrom +
                ", applicationDateTo=" + applicationDateTo +
                ", applicationReferenceNo=" + applicationReferenceNo +
                '}';
    }
}
