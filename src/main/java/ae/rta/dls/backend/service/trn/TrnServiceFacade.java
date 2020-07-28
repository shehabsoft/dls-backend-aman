package ae.rta.dls.backend.service.trn;

import ae.rta.dls.backend.service.dto.trn.ApplicationDTO;

/**
 * Trn Facade Service Interface for managing trn module.
 */
public interface TrnServiceFacade {


    /**
     * Update application
     * @param applicationDTO
     * @return application dto
     */
    ApplicationDTO updateApplication(ApplicationDTO applicationDTO);
}
