package ae.rta.dls.backend.service.prd.impl;

import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;
import ae.rta.dls.backend.service.prd.LearningFileService;
import ae.rta.dls.backend.service.prd.PrdServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PrdServiceFacadeImpl implements PrdServiceFacade {

    @Autowired
    private LearningFileService learningFileService;

    @Override
    public Optional<LearningFileDTO> findLearningFileById(Long id) {
        return learningFileService.findOne(id);
    }

    @Override
    public Page<LearningFileDTO> findAllLearningFile(Pageable pageable) {
        return learningFileService.findAll(pageable);
    }

    @Override
    public Optional<LearningFileDTO> findLearningFileByApplicationId(Long applicationId) {
        return learningFileService.findByApplicationId(applicationId);
    }

    @Override
    public Optional<LearningFileDTO> findActiveLearningFileByApplicationId(Long applicationId) {
        return learningFileService.findActiveLearningFileByApplicationId(applicationId);
    }

    @Override
    public Optional<LearningFileDTO> findActiveLearningFileByEmiratesId(String emiratesId) {
        return learningFileService.findActiveLearningFileByEmiratesId(emiratesId);
    }

}
