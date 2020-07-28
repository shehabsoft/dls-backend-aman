package ae.rta.dls.backend.service.trf.impl;

import ae.rta.dls.backend.service.dto.trf.ExamTrainingViewDTO;
import ae.rta.dls.backend.service.trf.ExamTrainingService;
import ae.rta.dls.backend.repository.trf.ExamTrainingRepository;
import ae.rta.dls.backend.service.mapper.trf.ExamTrainingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing ExamTraining.
 */
@Service
@Transactional
public class ExamTrainingServiceImpl implements ExamTrainingService {

    private final Logger log = LoggerFactory.getLogger(ExamTrainingServiceImpl.class);

    private final ExamTrainingRepository examTrainingRepository;

    private final ExamTrainingMapper examTrainingMapper;

    public ExamTrainingServiceImpl(ExamTrainingRepository examTrainingRepository, ExamTrainingMapper examTrainingMapper) {
        this.examTrainingRepository = examTrainingRepository;
        this.examTrainingMapper = examTrainingMapper;
    }

    @Override
    public Optional<ExamTrainingViewDTO> findByTryCode(String tryCode, String licenseCategoryCode) {
        log.debug("Request to get exam training by code: {} ", tryCode);

        return examTrainingRepository.findByTryCodeAndClassCode(tryCode, licenseCategoryCode).map(examTrainingMapper::toDto);
    }

    @Override
    public Optional<ExamTrainingViewDTO> findByForeignLicenseInfo(String tryCode, String licenseCategoryCode,String driverNationalityCode,
                                                           String countryCode, Long vehicleClassId, String foreignLicenseIssueDate) {

        return examTrainingRepository.findByForeignLicenseInfo(tryCode, licenseCategoryCode,
            driverNationalityCode,countryCode, vehicleClassId, foreignLicenseIssueDate).map(examTrainingMapper::toDto);
    }
}
