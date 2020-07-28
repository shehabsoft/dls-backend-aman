package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.service.dto.sys.ApplicationConfigurationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.util.Optional;

/**
 * Service Interface for managing ApplicationConfiguration.
 */
public interface ApplicationConfigurationService {

    /**
     * Save a applicationConfiguration.
     *
     * @param applicationConfigurationDTO the entity to save
     * @return the persisted entity
     */
    ApplicationConfigurationDTO save(ApplicationConfigurationDTO applicationConfigurationDTO) ;

    /**
     * Get all the applicationConfigurations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplicationConfigurationDTO> findAll(Pageable pageable) throws BadPaddingException, IllegalBlockSizeException;


    /**
     * Get the "id" applicationConfiguration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ApplicationConfigurationDTO> findOne(Long id);

    /**
     * Delete the "id" applicationConfiguration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the "key" applicationConfiguration.
     *
     * @param key the key of the entity
     * @return the entity
     */
    Optional<ApplicationConfigurationDTO> getConfiguration(String key) ;
}
