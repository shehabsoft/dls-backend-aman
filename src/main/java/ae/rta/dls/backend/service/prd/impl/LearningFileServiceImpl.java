package ae.rta.dls.backend.service.prd.impl;

import ae.rta.dls.backend.common.util.StringUtil;
import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;
import ae.rta.dls.backend.service.prd.LearningFileService;
import ae.rta.dls.backend.domain.prd.LearningFile;
import ae.rta.dls.backend.repository.prd.LearningFileRepository;
import ae.rta.dls.backend.service.dto.prd.LearningFileDTO;
import ae.rta.dls.backend.service.mapper.prd.LearningFileMapper;
import ae.rta.dls.backend.service.trn.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing LearningFile.
 */
@Service
@Transactional
public class LearningFileServiceImpl implements LearningFileService {

    private final Logger log = LoggerFactory.getLogger(LearningFileServiceImpl.class);

    private final LearningFileRepository learningFileRepository;

    private final LearningFileMapper learningFileMapper;

    private final ApplicationService applicationService;

    public LearningFileServiceImpl(LearningFileRepository learningFileRepository, LearningFileMapper learningFileMapper ,ApplicationService applicationService) {
        this.learningFileRepository = learningFileRepository;
        this.learningFileMapper = learningFileMapper;
        this.applicationService = applicationService;
    }

    /**
     * Save a learningFile.
     *
     * @param learningFileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LearningFileDTO save(LearningFileDTO learningFileDTO) {
        log.debug("Request to save LearningFile : {}", learningFileDTO);
        LearningFile learningFile = learningFileMapper.toEntity(learningFileDTO);
        learningFile = learningFileRepository.save(learningFile);
        return learningFileMapper.toDto(learningFile);
    }

    /**
     * Get all the learningFiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LearningFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LearningFiles");
        return learningFileRepository.findAll(pageable)
            .map(learningFileMapper::toDto);
    }


    /**
     * Get one learningFile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LearningFileDTO> findOne(Long id) {
        log.debug("Request to get LearningFile : {}", id);
        return learningFileRepository.findById(id)
            .map(learningFileMapper::toDto);
    }

    /**
     * Get learning file by application id.
     *
     * @param applicationId the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LearningFileDTO> findByApplicationId(Long applicationId) {
        log.debug("Request to get LearningFile by applicationId: {}", applicationId);
        Optional<LearningFileDTO> learningFileDTO = learningFileRepository.findByApplicationId(applicationId)
                                                                          .map(learningFileMapper::toDto);
        if (learningFileDTO.isPresent()) {
            Optional<ApplicationDTO> application = applicationService.findOne(applicationId);
            if(application.isPresent()) {
                learningFileDTO.get().setApplicationDetails(application.get());
            }
        }

        return learningFileDTO;
    }

    /**
     * Get active learning file by application id.
     *
     * @param applicationId the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LearningFileDTO> findActiveLearningFileByApplicationId(Long applicationId) {
        log.debug("Request to get LearningFile by applicationId: {}", applicationId);
        return learningFileRepository.findActiveLearningFileByApplicationId(StringUtil.getString(applicationId))
                                                                          .map(learningFileMapper::toDto);
    }

    /**
     * Get active learning file by .
     *
     * @param emiratesId Emirates Id
     * @return the entity
     */
    public Optional<LearningFileDTO> findActiveLearningFileByEmiratesId(String emiratesId) {
        log.debug("Request to get LearningFile by emiratesId : {}", emiratesId);
        return learningFileRepository.findActiveLearningFileByEmiratesId(emiratesId)
            .map(learningFileMapper::toDto);
    }

    /**
     * Find active learning file by Traffic Code No
     *
     * @param trafficCodeNo Traffic Code No
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LearningFileDTO> findActiveLearningFileByTrafficCodeNo(Long trafficCodeNo) {
        log.debug("Request to get LearningFile by trafficCodeNo : {}", trafficCodeNo);
        Optional<LearningFileDTO> learningFileDTO = learningFileRepository.findActiveLearningFileByTrafficCodeNo(trafficCodeNo)
            .map(learningFileMapper::toDto);
        if (learningFileDTO.isPresent()) {
            Optional<ApplicationDTO> application = applicationService.findOne(learningFileDTO.get().getApplicationId());
            if(application.isPresent()) {
                learningFileDTO.get().setApplicationDetails(application.get());
            }
        }

        return learningFileDTO;
    }

    /**
     * Delete the learningFile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LearningFile : {}", id);
        learningFileRepository.deleteById(id);
    }
}
