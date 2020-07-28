package ae.rta.dls.backend.service.mapper.ws.rest;

import ae.rta.dls.backend.domain.ws.rest.RestLog;
import ae.rta.dls.backend.service.dto.ws.rest.RestLogDTO;
import ae.rta.dls.backend.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity RestLog and its DTO RestLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RestLogMapper extends EntityMapper<RestLogDTO, RestLog> {



    default RestLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        RestLog restLog = new RestLog();
        restLog.setId(id);
        return restLog;
    }
}
