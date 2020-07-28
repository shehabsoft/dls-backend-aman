package ae.rta.dls.backend.service.mapper.prd;

import ae.rta.dls.backend.domain.prd.Handbook;
import ae.rta.dls.backend.service.dto.prd.HandbookDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Handbook} and its DTO {@link HandbookDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HandbookMapper extends EntityMapper<HandbookDTO, Handbook> {

    default Handbook fromId(Long id) {
        if (id == null) {
            return null;
        }
        Handbook handbook = new Handbook();
        handbook.setId(id);
        return handbook;
    }
}
