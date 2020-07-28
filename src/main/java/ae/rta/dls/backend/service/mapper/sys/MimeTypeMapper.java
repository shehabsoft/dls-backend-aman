package ae.rta.dls.backend.service.mapper.sys;

import ae.rta.dls.backend.domain.sys.MimeType;
import ae.rta.dls.backend.service.dto.sys.MimeTypeDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity MimeType and its DTO MimeTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MimeTypeMapper extends EntityMapper<MimeTypeDTO, MimeType> {



    default MimeType fromId(Long id) {
        if (id == null) {
            return null;
        }
        MimeType mimeType = new MimeType();
        mimeType.setId(id);
        return mimeType;
    }
}
