package ae.rta.dls.backend.service.mapper.ws.rest;

import ae.rta.dls.backend.domain.ws.rest.BrmRestLog;
import ae.rta.dls.backend.service.dto.ws.rest.BrmRestLogDTO;

import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity BrmRestLog and its DTO BrmRestLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BrmRestLogMapper extends EntityMapper<BrmRestLogDTO, BrmRestLog> {



    default BrmRestLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        BrmRestLog brmRestLog = new BrmRestLog();
        brmRestLog.setId(id);
        return brmRestLog;
    }
}
