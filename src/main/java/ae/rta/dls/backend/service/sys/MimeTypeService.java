package ae.rta.dls.backend.service.sys;

import ae.rta.dls.backend.service.dto.sys.MimeTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MimeType.
 */
public interface MimeTypeService {

    /**
     * Save a mimeType.
     *
     * @param mimeTypeDTO the entity to save
     * @return the persisted entity
     */
    MimeTypeDTO save(MimeTypeDTO mimeTypeDTO);

    /**
     * Get all the mimeTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MimeTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" mimeType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MimeTypeDTO> findOne(Long id);

    /**
     * Delete the "id" mimeType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the "extension" Mime Type.
     *
     * @param extension the code of the entity
     * @return the entity
     */
    Optional<MimeTypeDTO> getByExtension(String extension);
}
