package ae.rta.dls.backend.service.trf.impl;

import ae.rta.dls.backend.repository.trf.EyeTestResultRepository;
import ae.rta.dls.backend.service.dto.trf.EyeTestResultViewDTO;
import ae.rta.dls.backend.service.mapper.trf.EyeTestResultMapper;
import ae.rta.dls.backend.service.trf.EyeTestResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Service Implementation for managing EyeTestResult.
 */
@Service
@Transactional
public class EyeTestResultServiceImpl implements EyeTestResultService {

    private final Logger log = LoggerFactory.getLogger(EyeTestResultServiceImpl.class);

    private final EyeTestResultRepository eyeTestResultRepository;

    private final EyeTestResultMapper eyeTestResultMapper;

    public EyeTestResultServiceImpl(EyeTestResultRepository eyeTestResultRepository, EyeTestResultMapper eyeTestResultMapper) {
        this.eyeTestResultRepository = eyeTestResultRepository;
        this.eyeTestResultMapper = eyeTestResultMapper;
    }

    /**
     * Find eye test result by passed emirates Id
     * @param emiratesId
     * @return Optional<EyeTestResultDTO>
     */
    @Override
    public Optional<EyeTestResultViewDTO> findByEmiratesId(String emiratesId) {
        log.debug("Request to get eye test result by emiratesId: {} ", emiratesId);

        return eyeTestResultRepository.findByEmiratesId(emiratesId).map(eyeTestResultMapper::toDto);
    }
}
