package ae.rta.dls.backend.web.rest.trf;
import ae.rta.dls.backend.common.errors.SystemException;
import ae.rta.dls.backend.common.util.CommonUtil;
import ae.rta.dls.backend.common.util.NumberUtil;
import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.domain.enumeration.prd.DrivingLicenseExperience;
import ae.rta.dls.backend.domain.enumeration.prd.TestType;
import ae.rta.dls.backend.domain.enumeration.trn.ServiceCode;
import ae.rta.dls.backend.service.dto.trf.ExamTrainingViewDTO;
import ae.rta.dls.backend.service.dto.trn.ForeignLicenseDetailDTO;
import ae.rta.dls.backend.service.dto.trn.ServiceRequestDTO;
import ae.rta.dls.backend.service.trf.ExamTrainingService;
import ae.rta.dls.backend.service.trn.ServiceRequestService;
import ae.rta.dls.backend.web.rest.errors.NotImplementedException;
import ae.rta.dls.backend.web.rest.vm.trf.ExamTrainingVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * REST controller for managing ExamTraining.
 */
@RestController
@RequestMapping("/api/trf")
public class ExamTrainingResource {

    private final Logger log = LoggerFactory.getLogger(ExamTrainingResource.class);

    private final ExamTrainingService examTrainingService;

    private final ServiceRequestService serviceRequestService;

    public ExamTrainingResource(ExamTrainingService examTrainingService, ServiceRequestService serviceRequestService) {
        this.examTrainingService = examTrainingService;
        this.serviceRequestService = serviceRequestService;
    }

    /**
     * GET  /exam-trainings : get all the examTrainings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of examTrainings in body
     */
    @GetMapping("/exam-trainings/licenseCategoryCode/{licenseCategoryCode}/applicationReferenceNo/{applicationId}")
    public ResponseEntity<ExamTrainingVM> getAllExamTrainings(@PathVariable String licenseCategoryCode, @PathVariable Long applicationId) {
        log.debug("REST request to get all ExamTrainings");

        Optional<ExamTrainingViewDTO> theoryExamTrainingDTO = null;
        Optional<ExamTrainingViewDTO> practicalExamTrainingDTO = null;

        Optional<ServiceRequestDTO> foreignLicenseRequest = serviceRequestService.findByApplicationIdAndServiceCode(
                                                    applicationId, ServiceCode.DEFINE_FOREIGN_DRIVING_LICENSE.value());

        if (foreignLicenseRequest.isPresent()) {

            ForeignLicenseDetailDTO foreignLicenseDetailDTO = CommonUtil.getMapper().convertValue(
                foreignLicenseRequest.get().getServiceDocument().getParameters(), ForeignLicenseDetailDTO.class);

            if (foreignLicenseDetailDTO.getIssuedFromCountryDetails() == null || foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode() == null) {
                throw new SystemException("missing country details in foreign license info");
            }
            if (foreignLicenseDetailDTO.getCategoryDetails() == null
                || foreignLicenseDetailDTO.getCategoryDetails().isEmpty()
                || foreignLicenseDetailDTO.getCategoryDetails().get(0).getLocalCategory() == null
                || foreignLicenseDetailDTO.getCategoryDetails().get(0).getLocalCategory().getCode() == null) {
                throw new SystemException("missing locale category details in foreign license info");
            }

            if (foreignLicenseDetailDTO.getCategoryDetails().size() > 1) {
                throw new NotImplementedException("foreign license with more than 1 category");
            }

            if (foreignLicenseDetailDTO.getExperience() == null) {
                throw new SystemException("missing driving experience in foreign license info");
            }

            if (foreignLicenseDetailDTO.getDriverNationality() == null || StringUtil.isBlank(foreignLicenseDetailDTO.getDriverNationality().getCode())) {
                throw new SystemException("missing driver nationality in foreign license info");
            }

            String countryCode = foreignLicenseDetailDTO.getIssuedFromCountryDetails().getCode();
            String localeCategoryCode = foreignLicenseDetailDTO.getCategoryDetails().get(0).getLocalCategory().getCode();
            Long vehicleClassId = NumberUtil.toLong(localeCategoryCode.substring(7, localeCategoryCode.length()));

            String foreignLicenseIssueDate = null;
            if (DrivingLicenseExperience.LESS_THAN_TWO.value().equals(foreignLicenseDetailDTO.getExperience().getValue())) {
                foreignLicenseIssueDate = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else if (DrivingLicenseExperience.BETWEEN_TWO_AND_FIVE.value().equals(foreignLicenseDetailDTO.getExperience().getValue())) {
                foreignLicenseIssueDate = LocalDate.now().minusYears(3).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else {
                foreignLicenseIssueDate = LocalDate.now().minusYears(6).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            }

            String driverNationalityCode = foreignLicenseDetailDTO.getDriverNationality().getCode();
            theoryExamTrainingDTO = examTrainingService.findByForeignLicenseInfo(TestType.THEORY.value(),
                                                    licenseCategoryCode, driverNationalityCode,countryCode, vehicleClassId, foreignLicenseIssueDate);
            practicalExamTrainingDTO = examTrainingService.findByForeignLicenseInfo(TestType.ROAD.value(),
                                                    licenseCategoryCode, driverNationalityCode,countryCode, vehicleClassId, foreignLicenseIssueDate);
        } else {
            theoryExamTrainingDTO = examTrainingService.findByTryCode(TestType.THEORY.value(), licenseCategoryCode);
            practicalExamTrainingDTO = examTrainingService.findByTryCode(TestType.ROAD.value(), licenseCategoryCode);
        }

        if (!theoryExamTrainingDTO.isPresent()) {
            throw new SystemException("Theory Exam Required Training not found");
        }

        if (!practicalExamTrainingDTO.isPresent()) {
            throw new SystemException("practical Exam Required Training not found");
        }

        ExamTrainingVM examTrainingVM = new ExamTrainingVM();

        if (theoryExamTrainingDTO.get().getMinRequiredLessonsNo() > 0) {
            examTrainingVM.setMinimumTheoryLessonsRequired(8);
        } else {
            examTrainingVM.setMinimumTheoryLessonsRequired(0);
        }

        if (practicalExamTrainingDTO.get().getMinRequiredLessonsNo() >= 1 && practicalExamTrainingDTO.get().getMinRequiredLessonsNo() <= 16) {
            examTrainingVM.setMinimumDrivingLessonsRequired(10);
        } else if (practicalExamTrainingDTO.get().getMinRequiredLessonsNo() >= 17 && practicalExamTrainingDTO.get().getMinRequiredLessonsNo() <= 26) {
            examTrainingVM.setMinimumDrivingLessonsRequired(15);
        } else if (practicalExamTrainingDTO.get().getMinRequiredLessonsNo() >= 27) {
            examTrainingVM.setMinimumDrivingLessonsRequired(20);
        } else {
            examTrainingVM.setMinimumDrivingLessonsRequired(0);
        }

        Optional response = Optional.of(examTrainingVM);

        return ResponseUtil.wrapOrNotFound(response);
    }
}
