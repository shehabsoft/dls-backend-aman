package ae.rta.dls.backend.service.trf;

import ae.rta.dls.backend.service.dto.trf.ExamTrainingViewDTO;
import java.util.Optional;

/**
 * Service Interface for managing ExamTraining.
 */
public interface ExamTrainingService {

    Optional<ExamTrainingViewDTO> findByTryCode(String tryCode, String licenseCategoryCode);

    Optional<ExamTrainingViewDTO> findByForeignLicenseInfo(String tryCode, String licenseCategoryCode,String driverNationalityCode,
                                                String countryCode, Long vehicleClassId, String foreignLicenseIssueDate);
}
